package GUI;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.bayes.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;
import weka.gui.Main;

public class ClassifierModel {

	public static String getUserInterest() throws Exception {
		Classifier classifier = new NaiveBayesMultinomialText();
		Instances train = new Instances(new BufferedReader(new FileReader("./ThesisData1/trainingdata.arff")));

		int lastIndex = train.numAttributes() - 1;

		train.setClassIndex(lastIndex);

		Instances test = new Instances(
				new BufferedReader(new FileReader("C:/Users/Sampath/Desktop/New folder/THESIS/ThesisData1/test.arff")));
		test.setClassIndex(lastIndex);

		classifier.buildClassifier(train);

		double index = classifier.classifyInstance(test.instance(0));
		String userType = train.attribute(lastIndex).value((int) index);
		return userType;

	}
}
