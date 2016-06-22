package classifier;

import app.AppConfig;

import com.dkriesel.snipe.core.*;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import ij.*;

public class Main_Classifier {

	private final NeuralNetwork netz;

	// Groesse des Netzes einstellen

	// Anzahl Klassen (mit Rueckweisungsklasse)
	private int outputClasses = 5;
	// Anzahl der Neuronen in der Hidden-Schicht
	private int nhide = 30;
	// Groesse des Eigenschaftsvektors
	private int nin = 28;

	// Parameter für Lernalgorithmus
	private int durchlauf = 5000;
	private double epsilon = 0.2;

	public Main_Classifier() {

		// KNN (kuenstliches neuronales Netz)
		NeuralNetworkDescriptor knnDescriptor = new NeuralNetworkDescriptor(
				nin, nhide, outputClasses); // Größe

		// Art des Netzes
		// (totale,zyklenfreie Vernetzung)
		knnDescriptor.setSettingsTopologyFeedForward();
		this.netz = new NeuralNetwork(knnDescriptor);

	}

	public void train(String pathToLearn) {
		System.out.println("Start Training");
		// Trainingsbeispiele (koennen auch mehr sein)
		ImagePlus imp1 = IJ.openImage(String
				.format("%s/night.jpg", pathToLearn));
		ImagePlus imp2 = IJ.openImage(String.format("%s/day.png", pathToLearn));
		ImagePlus imp3 = IJ.openImage(String.format("%s/inside.jpg",
				pathToLearn));
		ImagePlus imp4 = IJ.openImage(String.format("%s/waether.png",
				pathToLearn));
		ImagePlus imp5=IJ.openImage(String.format("%s/inside3.png",	pathToLearn));

		double[] a = createVector(imp1);
		double[] b = createVector(imp2);
		double[] c = createVector(imp3);
		double[] d = createVector(imp4);
		double[] e = createVector(imp5);

		double[][] input = new double[][] { a, b, c, d, e }; // Eingabe der
		// Trainingsbeispiele
		double[][] output = new double[][] { {1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{0,0,0,0,1}};// gewuenschte Ausgabe (Einheitsmatrix)

		TrainingSampleLesson lektion = new TrainingSampleLesson(input, output);// Beginn
		// Trainingsphase
		// Backpropagationzur Fehlermininierung (=Vorgang des Lernens)
		netz.trainBackpropagationOfError(lektion, durchlauf, epsilon);
		System.out.println("Finished Training");
	}

	public ClassificationResult classify(ImagePlus inputFile) {
		System.out.println(String.format("Start classification of %s",
				AppConfig.getConfig().INPUTFILE()));
		double[] pixelCharacteristic = createVector(inputFile);

		double[] out = netz.propagate(pixelCharacteristic); // Anwendungsphase
															// (Simulation)

		int maxi = 0; // Suche nach dem Maximum (=beste Erkennung)
		for (int i = 0; i < this.outputClasses; i++) {
			if (out[maxi] < out[i]) {
				maxi = i;
			}
			if (AppConfig.getConfig().IS_DEBUG()) {
				System.out.println(out[i] + ", ");
			}
		}
		System.out.println("Finished Classification");
		return new ClassificationResult(AppConfig.getConfig().INPUTFILE(),
				maxi, out[1], out[0], out[2], out[3], out[4]);

	}

	// zerlegt RGB Farben aller Pixel im Bild in 3 Klassen (niedrig , mittel ,
	// hoch)
	// Eigenschaftsvektor des Bildes
	public double[] zaehl(ImagePlus imp) {
		double[] zahl = new double[nin];
		double sum=0;
		for (int i = 0; i < imp.getHeight(); i++) {
			for (int j = 0; j < imp.getWidth(); j++) {
				// 4, weil Einteilung der Farben in 3 Klassen
				//for (int t = 0; t < 3; t++) {
				int rot=imp.getPixel(i, j)[0];
				int gruen=imp.getPixel(i, j)[1];
				int blau=imp.getPixel(i, j)[2];
				sum=sum+rot+gruen+blau;
					if (rot < 85) { // 85=255/3
						if (gruen < 85) {
							if (blau < 85) {
								zahl[0]++;
							}
							if (blau > 170) {
								zahl[2]++;
							}
							else {
								zahl[1]++;
							}
						}
						if (gruen >170) {
							if (blau < 85) {
								zahl[6]++;
							}
							if (blau > 170) {
								zahl[8]++;
							}
							else {
								zahl[7]++;
							}
						}
						else {
							if (blau < 85) {
								zahl[3]++;
							}
							if (blau > 170) {
								zahl[5]++;
							}
							else {
								zahl[4]++;
							}
						}
					} 
					
					if (rot > 170) {
						if (gruen < 85) {
							if (blau < 85) {
								zahl[9]++;
							}
							if (blau > 170) {
								zahl[11]++;
							}
							else {
								zahl[10]++;
							}
						}
						if (gruen >170) {
							if (blau < 85) {
								zahl[15]++;
							}
							if (blau > 170) {
								zahl[17]++;
							}
							else {
								zahl[16]++;
							}
						}
						else {
							if (blau < 85) {
								zahl[12]++;
							}
							if (blau > 170) {
								zahl[14]++;
							}
							else {
								zahl[13]++;
							}
						}
					} else {
						if (gruen < 85) {
							if (blau < 85) {
								zahl[18]++;
							}
							if (blau > 170) {
								zahl[20]++;
							}
							else {
								zahl[19]++;
							}
						}
						if (gruen >170) {
							if (blau < 85) {
								zahl[24]++;
							}
							if (blau > 170) {
								zahl[26]++;
							}
							else {
								zahl[25]++;
							}
						}
						else {
							if (blau < 85) {
								zahl[21]++;
							}
							if (blau > 170) {
								zahl[23]++;
							}
							else {
								zahl[22]++;
							}
						}
					}

				//}
			}
		}
		zahl[27]=sum/255;
		// mit 3 multiplizieren, weil 3 Farbkanaele
		int total = imp.getHeight() * imp.getWidth() * 3;
		for (int t = 0; t < zahl.length; t++) {
			// relativer Anteil
			zahl[t] = zahl[t] / total;
		}

		return zahl;
	}

	private double[] createVector(ImagePlus imp1) {
		double[] featureVector = zaehl(imp1);
		if (AppConfig.getConfig().IS_DEBUG()) {
			showVector(featureVector);
		}

		return featureVector;
	}

	private void showVector(double[] featureVector) {
		for (int i = 0; i < featureVector.length; i++) {
			System.out.println(featureVector[i]);
		}
	}

}
