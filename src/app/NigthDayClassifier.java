package app;

import filefilter.ImageFileFilter;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
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

		String inputfile = AppConfig.getConfig()
				.INPUTFILE();
		
		if(inputfile.contains(".")){
			ImagePlus fileToClassify = IJ.openImage(inputfile);
			ClassificationResult result = classifier.classify(fileToClassify);
			showResult(fileToClassify, result, true);
			createResultFiles(fileToClassify, result);
			
		}else{
			File dir = new File(inputfile);
			
			for(File imageFile : dir.listFiles(new ImageFileFilter())){
				
				AppConfig.getConfig().setINPUTFILE(imageFile.getName());
				ImagePlus fileToClassify = IJ.openImage(imageFile.getAbsolutePath());
				ClassificationResult result = classifier.classify(fileToClassify);
				showResult(fileToClassify, result, false);
				createResultFiles(fileToClassify, result);
			}

		}
		
		
	}

	private static void createResultFiles(ImagePlus fileToClassify, ClassificationResult result) {
		
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
			ClassificationResult result, boolean popup) {

		// Ergebnis im Bild einblenden
		ImageProcessor ip = fileToClassify.getChannelProcessor();
		// fontsize for result
		int fontSize = ip.getWidth() / 20; 
		//fontsize for confidences
		int smallFontSize = fontSize/2;
		// Postion in 2 quarter of the image
		int horizontalPosition = ip.getWidth()/16*10;
		int verticalPosition = ip.getHeight() / 16 * 2;
		
		Font fonti = new Font("Arial", Font.PLAIN, fontSize); 
		ip.setFont(fonti);
		ip.setColor(Color.red);
		ip.drawString(result.getEnvironment(), horizontalPosition, verticalPosition); 
								
		Font fontii = new Font("Arial", Font.PLAIN, smallFontSize); 
		ip.setFont(fontii);
		DecimalFormat df = new DecimalFormat("0.00");
		String str1 = "Nacht: " + df.format(result.getProbIsNight());
		String str2 = "Tag: " + df.format(result.getProbIsDay());
		String str3 = "Innenraum: " + df.format(result.getProbIsInside());
		String str4 = "unbestimmt: "
				+ df.format(result.getProbIsNotClassifiable());
		ip.drawString(str1,  horizontalPosition, verticalPosition + smallFontSize);
		ip.drawString(str2,  horizontalPosition, verticalPosition + 2 * smallFontSize);
		ip.drawString(str3,  horizontalPosition, verticalPosition + 3 * smallFontSize);
		ip.drawString(str4,  horizontalPosition, verticalPosition + 4 * smallFontSize);
		if(popup){
			fileToClassify.show();
		}
	}
}
