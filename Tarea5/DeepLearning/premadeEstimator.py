#!/usr/bin/env
import tensorflow as tf
import studentsData
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

maxNumber = 10

def main(argv):
    print("\nCollecting arguments")
    global args
    args = parser.parse_args(argv[1:])
    print("\nFetch data")
    (trainFeatures, trainLabel),(testFeatures, testLabel) = studentsData.loadData()
    featureColumns = []
    print("\nAdding features keys")
    for key in trainFeatures.keys():
        featureColumns.append(tf.feature_column.numeric_column(key = key))
    # To select the model type of deep learning
    global classifier
    classifier = tf.estimator.DNNClassifier(feature_columns = featureColumns,
                                            hidden_units = [6, 8, 8, 10, 8, 8, 6],
                                            n_classes = maxNumber + 1)
    print("\nTraining model")
    # print(trainLabel)
    classifier.train(input_fn = lambda:studentsData.trainInputFn(
                                                            trainFeatures,
                                                            trainLabel,
                                                            args.batch_size),
                    steps = args.train_steps)
    print("\nEvaluating model")
    evalResult = classifier.evaluate(
        input_fn = lambda:studentsData.evalInputFn(testFeatures, testLabel,
                                                        args.batch_size))

    print("\nTest set accuracy: {accuracy:0.3f}\n".format(**evalResult))
    print(prediction([
        1,16,0,0,0,1,1,4,4,0,0,2,2,0,0,1,1,0,1,1,1,0,3,4,2,1,1,5,18,4.5,3.5
    ]))


def prediction(data):
    print("\nGenerating predictions from inserted data")
    predictFeatures = {
        "sex": [data[0]],
        "age": [data[1]],
        "address": [data[2]],
        "famsize": [data[3]],
        "Pstatus": [data[4]],
        "Medu": [data[5]],
        "Fedu": [data[6]],
        "Mjob": [data[7]],
        "Fjob": [data[8]],
        "reason": [data[9]],
        "guardian": [data[10]],
        "traveltime": [data[11]],
        "studytime": [data[12]],
        "failures": [data[13]],
        "famsup": [data[14]],
        "paid": [data[15]],
        "activities": [data[16]],
        "internet": [data[17]],
        "romantic": [data[18]],
        "famrel": [data[19]],
        "freetime": [data[20]],
        "goout": [data[21]],
        "Dalc": [data[22]],
        "Walc": [data[23]],
        "health": [data[24]],
        "absences": [data[25]],
        "G1": [data[26]],
        "G2": [data[27]]
    }
    global classifier
    global args
    predictions = classifier.predict(
        input_fn = lambda:studentsData.evalInputFn(predictFeatures,
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
