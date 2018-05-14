#!/usr/bin/env
# encoding: utf-8
from Tkinter import *
import tkMessageBox
import tensorflow as tf
import airlineTicketData
import argparse

global classifier
global agrs
airportsId = []
airportsName = []
top = Tk()
top.title("El precio de un boleto de avión")
# top.geometry('500x400')

def getAirports():
    csv = open("/Users/daniel/Documents/InteligenciaArtificial/Tarea5/DeepLearning/airports.csv", "r")
    for line in csv:
        attributes = line.split("|")
        airportsId.append(attributes[0])
        airportsName.append(attributes[1].replace("\r\n",""))

priceStr = StringVar()
porcentageStr = StringVar()
selectAirport = StringVar()
passangersValue = StringVar()
passangersValue.set("1")
distanceValue = StringVar()
distanceValue.set("0")
getAirports()
selectAirport.set(airportsName[0])
roundValue = IntVar()
onLineValue = IntVar()

def getPrice():
    airportIndex = airportsName.index(selectAirport.get())
    airportId = int(airportsId[airportIndex])
    data = [
        airportId,
        roundValue.get(),
        onLineValue.get(),
        int(passangersValue.get()),
        int(distanceValue.get()),

    ]
    print("\nCreating data to make prediction")
    print(data)
    (price, probability) = prediction(data)
    priceStr.set("$ "+str(price))
    porcentageStr.set("Probabilidad: {:.1f}%".format(probability))


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
    global args
    args = parser.parse_args(argv[1:])
    print("\nFetch data")
    (trainFeatures, trainLabel),(testFeatures, testLabel) = airlineTicketData.loadData()
    featureColumns = []
    print("\nAdding features keys")
    for key in trainFeatures.keys():
        featureColumns.append(tf.feature_column.numeric_column(key = key))
    # To select the model type of deep learning
    global classifier
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

    frameData = LabelFrame(top, text = "Información")
    framePrice = LabelFrame(top, text = "Resultado")
    lblOrigin = Label(frameData, text = "Origen").grid(row = 0, column = 0)
    ddlOrigin = OptionMenu(frameData, selectAirport, *airportsName).grid(row = 0, column = 1)
    lblRound = Label(frameData, text = "Vuelo redondo").grid(row = 1, column = 0)
    optionRound1 = Radiobutton(frameData, text="Sí", variable = roundValue, value=1).grid(row = 1, column = 1)
    optionRound2 = Radiobutton(frameData, text="No", variable = roundValue, value=0).grid(row = 1, column = 2)
    lblOnLine = Label(frameData, text = "Compra en línea").grid(row = 2, column = 0)
    optionOnLine1 = Radiobutton(frameData, text="Sí", variable = onLineValue, value=1).grid(row = 2, column = 1)
    optionOnLine2 = Radiobutton(frameData, text="No", variable = onLineValue, value=0).grid(row = 2, column = 2)
    lblPassangers = Label(frameData, text = "# Pasajeros").grid(row = 3, column = 0)
    txtPassangers = Entry(frameData, textvariable = passangersValue, bd = 5).grid(row = 3, column = 1)
    lblDistance = Label(frameData, text = "Distancia (Millas)").grid(row = 4, column = 0)
    txtDistance = Entry(frameData, textvariable = distanceValue, bd = 5).grid(row = 4, column = 1)
    lblPrice = Label(framePrice, text = "Precio: $").grid(row = 0, column = 0)
    priceResult = Label(framePrice, textvariable = priceStr).grid(row = 0, column = 1)
    btnCalculate = Button(top, text = "Calcular", command = getPrice)
    lblPorcentage = Label(framePrice, textvariable = porcentageStr).grid(row = 1, column = 0)
    priceStr.set("$ 0.00")
    porcentageStr.set("")
    frameData.pack(fill = "both", expand = "yes")
    framePrice.pack(fill = "both", expand = "yes")
    btnCalculate.pack()

    top.mainloop()


def prediction(data):
    global args
    print("\nGenerating predictions from inserted data")
    predictFeatures = {
        "ORIGIN_AIRPORT_ID": [data[0]],
        "ROUNDTRIP": [data[1]],
        "ONLINE": [data[2]],
        "PASSENGERS": [data[3]],
        "MILES_FLOWN": [data[4]]
    }
    global classifier
    predictions = classifier.predict(
        input_fn = lambda:airlineTicketData.evalInputFn(predictFeatures,
                                                        labels = None,
                                                        batchSize = args.batch_size))
    template = "\nPrediction is {:.1f} ({:.1f}%)"
    for predDict in predictions:
        classId = predDict['class_ids'][0]
        probability = predDict['probabilities'][classId]
        print(template.format(classId, probability * 100))
        print("End of prediction")
        return (classId, probability * 100)

if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run(main)
