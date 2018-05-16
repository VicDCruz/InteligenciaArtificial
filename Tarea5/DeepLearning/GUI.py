#!/usr/bin/env
# encoding: utf-8
from Tkinter import *
import tkMessageBox
import tensorflow as tf
import studentsData
import argparse

top = Tk()
top.title("La calificación final")
# top.geometry('500x400')

gradeStr = StringVar()
porcentageStr = StringVar()
sex = IntVar()
age = StringVar()
age.set("15")
address = IntVar()
famsize = IntVar()
Pstatus = IntVar()
Medu = IntVar()
Fedu = IntVar()
Mjob = IntVar()
Fjob = IntVar()
reason = IntVar()
guardian = IntVar()
traveltime = IntVar()
studytime = IntVar()
failures = StringVar()
failures.set("1")
famsup = IntVar()
paid = IntVar()
activities = IntVar()
internet = IntVar()
romantic = IntVar()
famrel = StringVar()
famrel.set("5")
freetime = StringVar()
freetime.set("1")
goout = StringVar()
goout.set("1")
Dalc = StringVar()
Dalc.set("1")
Walc = StringVar()
Walc.set("1")
health = StringVar()
health.set("5")
absences = StringVar()
absences.set("0")
g1 = StringVar()
g1.set("10")
g2 = StringVar()
g2.set("10")

def getGrade():
    data = [
        sex.get(),
        int(age.get()),
        address.get(),
        famsize.get(),
        Pstatus.get(),
        Medu.get(),
        Fedu.get(),
        Mjob.get(),
        Fjob.get(),
        reason.get(),
        guardian.get(),
        traveltime.get(),
        studytime.get(),
        int(failures.get()),
        famsup.get(),
        paid.get(),
        activities.get(),
        internet.get(),
        romantic.get(),
        int(famrel.get()),
        int(freetime.get()),
        int(goout.get()),
        int(Dalc.get()),
        int(Walc.get()),
        int(health.get()),
        int(absences.get()),
        float(g1.get()),
        float(g2.get()),
    ]
    print("\nCreating data to make prediction")
    print(data)
    (grade, probability) = prediction(data)
    gradeStr.set(str(grade))
    porcentageStr.set("Probabilidad: {:.1f}%".format(probability))


print("\nInitializing Premade Estimator")

parser = argparse.ArgumentParser()
parser.add_argument('--batch_size',
                    default = 100,
                    type = int,
                    help = 'batch size')
parser.add_argument('--train_steps',
                    default = 2500,
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
                                            hidden_units = [8, 6, 6, 8, 8, 15, 8, 8, 6, 6, 8],
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

    frameData = LabelFrame(top, text = "Datos")
    frameResults = LabelFrame(top, text = "Resultados")
    btnCalculate = Button(top, text ="Calcular", command = getGrade)
    Label(frameData, text = "Sexo").grid(row = 1, column = 0)
    Label(frameData, text = "Edad").grid(row = 2, column = 0)
    Label(frameData, text = "Zona").grid(row = 3, column = 0)
    Label(frameData, text = "Tamaño de la familia").grid(row = 4, column = 0)
    Label(frameData, text = "Padres viven").grid(row = 5, column = 0)
    Label(frameData, text = "Eduación de la madre").grid(row = 6, column = 0)
    Label(frameData, text = "Educación del padre").grid(row = 7, column = 0)
    Label(frameData, text = "Trabajo de la madre").grid(row = 8, column = 0)
    Label(frameData, text = "Trabajo del padre").grid(row = 9, column = 0)
    Label(frameData, text = "Razón de elegir la escuela").grid(row = 10, column = 0)
    Label(frameData, text = "Guardian").grid(row = 11, column = 0)
    Label(frameData, text = "Tiempo de translado").grid(row = 12, column = 0)
    Label(frameData, text = "Tiempo de estudio").grid(row = 13, column = 0)
    Label(frameData, text = "Fallas (1-4)").grid(row = 14, column = 0)
    Label(frameData, text = "Con novi@").grid(row = 1, column = 6)
    Label(frameData, text = "Calidad de la relación familiar (1-5)").grid(row = 2, column = 6)
    Label(frameData, text = "Tiempo libre (1-5)").grid(row = 3, column = 6)
    Label(frameData, text = "Sale con amigos (1-5)").grid(row = 4, column = 6)
    Label(frameData, text = "Tomar alcohol entre semana (1-5)").grid(row = 5, column = 6)
    Label(frameData, text = "Tomar alcohol en fin de semana (1-5)").grid(row = 6, column = 6)
    Label(frameData, text = "Salud (1-5)").grid(row = 7, column = 6)
    Label(frameData, text = "Ausencias").grid(row = 8, column = 6)
    Label(frameData, text = "Ex. Parcial 1").grid(row = 9, column = 6)
    Label(frameData, text = "Ex. Parcial 2").grid(row = 10, column = 6)
    Label(frameData, text = "Soporte familiar").grid(row = 11, column = 6)
    Label(frameData, text = "Clases extras").grid(row = 12, column = 6)
    Label(frameData, text = "Actividades extracurriculares").grid(row = 13, column = 6)
    Label(frameData, text = "Tiene internet").grid(row = 14, column = 6)

    Radiobutton(frameData, text="Masculino", variable = sex, value=1).grid(row = 1, column = 1)
    Radiobutton(frameData, text="Femenino", variable = sex, value=0).grid(row = 1, column = 2)
    Entry(frameData, textvariable = age, bd = 5).grid(row = 2, column = 1)
    Radiobutton(frameData, text="Rural", variable = address, value=1).grid(row = 3, column = 1)
    Radiobutton(frameData, text="Urbana", variable = address, value=0).grid(row = 3, column = 2)
    Radiobutton(frameData, text="> 3", variable = famsize, value=1).grid(row = 4, column = 1)
    Radiobutton(frameData, text="= 3", variable = famsize, value=0).grid(row = 4, column = 2)
    Radiobutton(frameData, text="Juntos", variable = Pstatus, value=0).grid(row = 5, column = 1)
    Radiobutton(frameData, text="Separados", variable = Pstatus, value=1).grid(row = 5, column = 2)
    Radiobutton(frameData, text="Nada", variable = Medu, value=0).grid(row = 6, column = 1)
    Radiobutton(frameData, text="Primaria", variable = Medu, value=1).grid(row = 6, column = 2)
    Radiobutton(frameData, text="Secundaria", variable = Medu, value=2).grid(row = 6, column = 3)
    Radiobutton(frameData, text="Preparatoria", variable = Medu, value=3).grid(row = 6, column = 4)
    Radiobutton(frameData, text="Universidad", variable = Medu, value=4).grid(row = 6, column = 5)
    Radiobutton(frameData, text="Nada", variable = Fedu, value=0).grid(row = 7, column = 1)
    Radiobutton(frameData, text="Primaria", variable = Fedu, value=1).grid(row = 7, column = 2)
    Radiobutton(frameData, text="Secundaria", variable = Fedu, value=2).grid(row = 7, column = 3)
    Radiobutton(frameData, text="Preparatoria", variable = Fedu, value=3).grid(row = 7, column = 4)
    Radiobutton(frameData, text="Universidad", variable = Fedu, value=4).grid(row = 7, column = 5)
    Radiobutton(frameData, text="Educación", variable = Mjob, value=0).grid(row = 8, column = 1)
    Radiobutton(frameData, text="Salud", variable = Mjob, value=1).grid(row = 8, column = 2)
    Radiobutton(frameData, text="Servicios", variable = Mjob, value=2).grid(row = 8, column = 3)
    Radiobutton(frameData, text="En casa", variable = Mjob, value=3).grid(row = 8, column = 4)
    Radiobutton(frameData, text="Otros", variable = Mjob, value=4).grid(row = 8, column = 5)
    Radiobutton(frameData, text="Educación", variable = Fjob, value=0).grid(row = 9, column = 1)
    Radiobutton(frameData, text="Salud", variable = Fjob, value=1).grid(row = 9, column = 2)
    Radiobutton(frameData, text="Servicios", variable = Fjob, value=2).grid(row = 9, column = 3)
    Radiobutton(frameData, text="En casa", variable = Fjob, value=3).grid(row = 9, column = 4)
    Radiobutton(frameData, text="Otros", variable = Fjob, value=4).grid(row = 9, column = 5)
    Radiobutton(frameData, text="Cerca de casa", variable = reason, value=0).grid(row = 10, column = 1)
    Radiobutton(frameData, text="Reputación", variable = reason, value=1).grid(row = 10, column = 2)
    Radiobutton(frameData, text="Calidad de cursos", variable = reason, value=2).grid(row = 10, column = 3)
    Radiobutton(frameData, text="Otros", variable = reason, value=3).grid(row = 10, column = 4)
    Radiobutton(frameData, text="Madre", variable = guardian, value=0).grid(row = 11, column = 1)
    Radiobutton(frameData, text="Padre", variable = guardian, value=1).grid(row = 11, column = 2)
    Radiobutton(frameData, text="Otros", variable = guardian, value=2).grid(row = 11, column = 3)
    Radiobutton(frameData, text="< 15 min", variable = traveltime, value=1).grid(row = 12, column = 1)
    Radiobutton(frameData, text="15 - 30 min", variable = traveltime, value=2).grid(row = 12, column = 2)
    Radiobutton(frameData, text="30 min - 1 hr", variable = traveltime, value=3).grid(row = 12, column = 3)
    Radiobutton(frameData, text="> 1 hr", variable = traveltime, value=4).grid(row = 12, column = 4)
    Radiobutton(frameData, text="< 2 hrs", variable = studytime, value=1).grid(row = 13, column = 1)
    Radiobutton(frameData, text="2 - 5 hrs", variable = studytime, value=2).grid(row = 13, column = 2)
    Radiobutton(frameData, text="5 - 10 hrs", variable = studytime, value=3).grid(row = 13, column = 3)
    Radiobutton(frameData, text="> 10 hrs", variable = studytime, value=4).grid(row = 13, column = 4)
    Entry(frameData, textvariable = failures, bd = 5).grid(row = 14, column = 1)
    Radiobutton(frameData, text="Sí", variable = romantic, value=1).grid(row = 1, column = 7)
    Radiobutton(frameData, text="No", variable = romantic, value=0).grid(row = 1, column = 8)
    Entry(frameData, textvariable = famrel, bd = 5).grid(row = 2, column = 7)
    Entry(frameData, textvariable = freetime, bd = 5).grid(row = 3, column = 7)
    Entry(frameData, textvariable = goout, bd = 5).grid(row = 4, column = 7)
    Entry(frameData, textvariable = Dalc, bd = 5).grid(row = 5, column = 7)
    Entry(frameData, textvariable = Walc, bd = 5).grid(row = 6, column = 7)
    Entry(frameData, textvariable = health, bd = 5).grid(row = 7, column = 7)
    Entry(frameData, textvariable = absences, bd = 5).grid(row = 8, column = 7)
    Entry(frameData, textvariable = g1, bd = 5).grid(row = 9, column = 7)
    Entry(frameData, textvariable = g2, bd = 5).grid(row = 10, column = 7)
    Radiobutton(frameData, text="Sí", variable = famsup, value=1).grid(row = 11, column = 7)
    Radiobutton(frameData, text="No", variable = famsup, value=0).grid(row = 11, column = 8)
    Radiobutton(frameData, text="Sí", variable = paid, value=1).grid(row = 12, column = 7)
    Radiobutton(frameData, text="No", variable = paid, value=0).grid(row = 12, column = 8)
    Radiobutton(frameData, text="Sí", variable = activities, value=1).grid(row = 13, column = 7)
    Radiobutton(frameData, text="No", variable = activities, value=0).grid(row = 13, column = 8)
    Radiobutton(frameData, text="Sí", variable = internet, value=1).grid(row = 14, column = 7)
    Radiobutton(frameData, text="No", variable = internet, value=0).grid(row = 14, column = 8)

    Label(frameResults, text = "Calificación:").grid(row = 0, column = 0)
    Label(frameResults, textvariable = gradeStr).grid(row = 0, column = 1)
    Label(frameResults, textvariable = porcentageStr).grid(row = 1, column = 0)

    frameData.pack(fill = "both", expand = "yes")
    frameResults.pack(fill = "both", expand = "yes")
    btnCalculate.pack()

    top.mainloop()



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
        gradeId = predDict['class_ids'][0]
        probability = predDict['probabilities'][gradeId]
        return (gradeId, probability * 100)

if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run(main)
