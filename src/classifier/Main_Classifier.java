package classifier;

import app.AppConfig;

import com.dkriesel.snipe.core.*;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import ij.*;

public class Main_Classifier {

	private final NeuralNetwork netz;

	// Groesse des Netzes einstellen

	// Anzahl Klassen (mit Zurückweisungsklasse)
	private int outputClasses = 4;
	// Anzahl der Neuronen in der Hidden-Schicht
	private int nhide = 10;
	// Groesse des Eigenschaftsvektors
	private int nin = 9;

	// Parameter für Lernalgorithmus
	private int durchlauf = 1000;
	private double epsilon = 0.2;

	public Main_Classifier() {

		// KNN (künstliches neuronales Netz)
		NeuralNetworkDescriptor knnDescriptor = new NeuralNetworkDescriptor(
				nin, nhide, outputClasses); // Größe

		// Art des Netzes
		// (totale,zyklenfreie Vernetzung)
		knnDescriptor.setSettingsTopologyFeedForward();
		this.netz = new NeuralNetwork(knnDescriptor);

	}

	public void train(String pathToLearn) {
		System.out.println("Start Training");
		// Trainingsbeispiele (können auch mehr sein)
		ImagePlus imp1 = IJ.openImage(String
				.format("%s/night.jpg", pathToLearn));
		ImagePlus imp2 = IJ.openImage(String.format("%s/day.png", pathToLearn));
		ImagePlus imp3 = IJ.openImage(String.format("%s/inside.jpg",
				pathToLearn));

		double[] a = createVector(imp1);

		double[] b = createVector(imp2);

		double[] c = createVector(imp3);

		double[][] input = new double[][] { a, b, c }; // Eingabe der
		// Trainingsbeispiele
		double[][] output = new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 },
				{ 0, 0, 1, 0 } };// gewünschte Ausgabe (Einheitsmatrix)

		TrainingSampleLesson lektion = new TrainingSampleLesson(input, output);// Beginn
		// Trainingsphase
		// Backpropagationzur Fehlermininierung (=Vorgang des Lernens)
		netz.trainBackpropagationOfError(lektion, durchlauf, epsilon);
		System.out.println("Finished Training");
	}

	public ClassificationResult classify(ImagePlus inputFile) {
		System.out.println(String.format("Start classification of %s", AppConfig.getConfig().INPUTFILE()));
		double[] pixelCharacteristic = createVector(inputFile);

		double[] out = netz.propagate(pixelCharacteristic); // Anwendungsphase
															// (Simulation)

		int maxi = 0; // Suche nach dem Maximum (=beste Erkennung)
		for (int i = 0; i < this.outputClasses; i++) {
			if (out[maxi] < out[i]) {
				maxi = i;
			}
			if(AppConfig.getConfig().IS_DEBUG()){
				System.out.println(out[i] + ", ");
			}
		}
		System.out.println("Finished Classification");
		return new ClassificationResult(maxi, out[1], out[0], out[2], out[3]);

	}

	// zerlegt RGB Farben aller Pixel im Bild in 3 Klassen (niedrig , mittel ,
	// hoch)
	// Eigenschaftsvektor des Bildes
	public double[] zaehl(ImagePlus imp) {
		double[] zahl = new double[nin];

		for (int i = 0; i < imp.getHeight(); i++) {
			for (int j = 0; j < imp.getWidth(); j++) {
				// 4, weil Einteilung der Farben in 3 Klassen
				for (int t = 0; t < 4; t++) {
					if (imp.getPixel(i, j)[t] < 85) { // 85=255/3
						zahl[t]++;

					} else if (imp.getPixel(i, j)[t] > 170) {// 170=255*2/3
						// 6, weil 3 Klassen insgesamt*2 Klassen bereits
						// abgearbeitet (dient Positionierung im Array)
						zahl[t + 6]++;
					} else {
						// 3, weil 3 Klassen insgesamt*1 Klassen bereits
						// abgearbeitet (dient Positionierung im Array)
						zahl[t + 3]++;
					}

				}
			}
		}
		// mit 3 multiplizieren, weil 3 Farbkanäle
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
