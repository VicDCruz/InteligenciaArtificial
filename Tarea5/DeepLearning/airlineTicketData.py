#!/usr/bin/env
# encoding: utf-8
import tensorflow as tf
import pandas as pd

print("\nInitializing AlTD…")

TRAIN_PATH = "/Users/daniel/Documents/InteligenciaArtificial/Tarea5/DeepLearning/ticketData.csv"
TEST_PATH = "/Users/daniel/Documents/InteligenciaArtificial/Tarea5/DeepLearning/ticketDataTest.csv"

CSV_COLUMN_NAMES = [
                        "ORIGIN_AIRPORT_ID",
                        "ROUNDTRIP",
                        "ONLINE",
                        "PASSENGERS",
                        "MILES_FLOWN",
                        "ITIN_FARE"
                    ]

print("Collecting CSV column names")

def getFiles():
    """Local copy of train set"""
    print("\nGetting Train Path")
    trainPath = tf.keras.utils.get_file(fname = TRAIN_PATH.split('/')[-1],
                                        origin = TRAIN_PATH)
    print("\nGetting Test Path")
    testPath = tf.keras.utils.get_file(fname = TEST_PATH.split('/')[-1],
                                        origin = TEST_PATH)
    return trainPath, testPath

def loadData(labelName = "ITIN_FARE"):
    """Parse the data in train and test paths"""
    print("\nLoading data…")
    trainPath, testPath = TRAIN_PATH, TEST_PATH
    print("\nParse local csv")
    train = pd.read_csv(filepath_or_buffer = trainPath,
                        names = CSV_COLUMN_NAMES,
                        header = 0)
    print("\nGet train information")
    trainFeatures, trainLabel = train, train.pop(labelName)

    # Parse local csv
    test = pd.read_csv(filepath_or_buffer = testPath,
                        names = CSV_COLUMN_NAMES,
                        header = 0)
    # Get test information
    testFeatures, testLabel = test, test.pop(labelName)

    return (trainFeatures, trainLabel),(testFeatures, testLabel)

def trainInputFn(features, labels, batchSize):
    """Training input"""
    print("\nTraining input")
    # features = dict(features)
    print("\nConverting the inputs to dataset")
    dataset = tf.data.Dataset.from_tensor_slices((dict(features), labels))
    print("\nShuffle, repeat, and batch the examples")
    dataset = dataset.shuffle(1000).repeat().batch(batchSize)

    return dataset

def evalInputFn(features, labels, batchSize):
    """Evaluating input"""
    print("\nEvaluating input")
    features = dict(features)
    print("\nCheck labels")
    if labels is None:
        inputs = features
    else:
        inputs = (features, labels)
    print("\nCreating dataset")
    dataset = tf.data.Dataset.from_tensor_slices(inputs)

    assert batchSize is not None, "batchSize must not be None"
    dataset = dataset.batch(batchSize)

    return dataset
