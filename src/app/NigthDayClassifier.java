package app;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import classifier.ClassificationResult;
import classifier.Main_Classifier;

public class NigthDayClassifier {

	public static void main(String[] args) {
		Map<String, String> argMap = new HashMap<>();
		// set AppConfig
		for (String arg : args) {
			System.out.println(arg);
			String[] keyVal = arg.split("=");
			if (keyVal.length == 2) {
				argMap.put(keyVal[0], keyVal[1]);
			}
		}

		new AppConfig(argMap);

		run();

	}

	private static void run() {
		Main_Classifier classifier = new Main_Classifier();
		classifier.train(AppConfig.getConfig().PATH_TO_LEARN());

		ImagePlus fileToClassify = IJ.openImage(AppConfig.getConfig()
				.INPUTFILE());
		ClassificationResult result = classifier.classify(fileToClassify);

		showResult(fileToClassify, result);

		ResultFileSaver.saveImageFileToPng(fileToClassify);
		try {
			ResultFileSaver.saveXMLFile(result);
		} catch (FileNotFoundException | XMLStreamException
				| URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private static void showResult(ImagePlus fileToClassify,
			ClassificationResult result) {

		// Ergebnis im Bild einblenden
		ImageProcessor ip = fileToClassify.getChannelProcessor();
		Font fonti = new Font("Arial", Font.PLAIN, 100); // 100 Schriftgröße
		ip.setFont(fonti);
		ip.setColor(Color.red);
		ip.drawString(result.getEnvironment(), 1350, 150); // (1400,150)
															// Pixel-Position im
															// Bild
		Font fontii = new Font("Arial", Font.PLAIN, 50); // 50 Schriftgröße
		ip.setFont(fontii);
		DecimalFormat df = new DecimalFormat("0.00");
		String str1 = "Nacht: " + df.format(result.getProbIsNight());
		String str2 = "Tag: " + df.format(result.getProbIsDay());
		String str3 = "Innenraum: " + df.format(result.getProbIsInside());
		String str4 = "unbestimmt: "
				+ df.format(result.getProbIsNotClassifiable());
		ip.drawString(str1, 1400, 250);
		ip.drawString(str2, 1400, 300);
		ip.drawString(str3, 1400, 350);
		ip.drawString(str4, 1400, 400);
		fileToClassify.show();

	}
}
