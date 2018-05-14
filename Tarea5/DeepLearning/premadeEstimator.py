#!/usr/bin/env
import tensorflow as tf
import airlineTicketData
import argparse

print("\nInitializing Premade Estimator")

parser = argparse.ArgumentParser()
parser.add_argument('--batch_size',
                    default = 100,
                    type = int,
                    help = 'batch size')
parser.add_argument('--train_steps',
                    default = 2000,
                    type = int,
                    help = 'number of training steps')

maxNumber = 250

def main(argv):
    print("\nCollecting arguments")
    args = parser.parse_args(argv[1:])
    print("\nFetch data")
    (trainFeatures, trainLabel),(testFeatures, testLabel) = airlineTicketData.loadData()
    featureColumns = []
    print("\nAdding features keys")
    for key in trainFeatures.keys():
        featureColumns.append(tf.feature_column.numeric_column(key = key))
    # To select the model type of deep learning
    classifier = tf.estimator.DNNClassifier(feature_columns = featureColumns,
                                            hidden_units = [10,10],
                                            n_classes = maxNumber + 1)
    print("\nTraining model")
    # print(trainLabel)
    classifier.train(input_fn = lambda:airlineTicketData.trainInputFn(
                                                            trainFeatures,
                                                            trainLabel,
                                                            args.batch_size),
                    steps = args.train_steps)
    print("\nEvaluating model")
    evalResult = classifier.evaluate(
        input_fn = lambda:airlineTicketData.evalInputFn(testFeatures, testLabel,
                                                        args.batch_size))

    print("\nTest set accuracy: {accuracy:0.3f}\n".format(**evalResult))


def prediction(data):
    print("\nGenerating predictions from inserted data")
    predictFeatures = {
        "ORIGIN_AIRPORT_ID": [data[0]],
        "ROUNDTRIP": [data[1]],
        "ONLINE": [data[2]],
        "PASSENGERS": [data[3]],
        "MILES_FLOWN": [data[4]]
    }
    predictions = classifier.predict(
        input_fn = lambda:airlineTicketData.evalInputFn(predictFeatures,
                                                        labels = None,
                                                        batchSize = args.batch_size))
    template = "\nPrediction is {:.1f} ({:.1f}%)"
    for predDict in predictions:
        classId = predDict['class_ids'][0]
        probability = predDict['probabilities'][classId]
        return template.format(classId, probability * 100)

tf.logging.set_verbosity(tf.logging.INFO)
tf.app.run(main)


if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run(main)
