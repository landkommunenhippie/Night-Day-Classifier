package app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import classifier.ClassificationResult;
import ij.ImagePlus;
import ij.io.FileSaver;

/**
 * @author lkh
 *
 */
public class ResultFileSaver {
	
	public static void saveImageFileToPng(ImagePlus fileToSave){
		// Bild speichern 
		FileSaver fsave = new FileSaver(fileToSave);
		fsave.saveAsPng(AppConfig.getConfig().OUTPUTFILE_PICTURE());
	}
	
	/**
	 * 
	 * It is important, that the outputfolder exists already
	 * 
	 * @param result
	 * @throws XMLStreamException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void saveXMLFile(ClassificationResult result) throws XMLStreamException, URISyntaxException, IOException{

		// create an XMLOutputFactory
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    // create XMLEventWriter
	    XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(AppConfig.getConfig().OUTPUTFILE_XML()));
	    
	    // create an EventFactory
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent newLine = eventFactory.createDTD("\n");
	    // create and write Start Tag
	    StartDocument startDocument = eventFactory.createStartDocument();
	    eventWriter.add(startDocument);
	    
	    // create config open tag
	    StartElement configStartElement = eventFactory.createStartElement("",
	        "", "classification-result");
	    eventWriter.add(configStartElement);
	    eventWriter.add(newLine);
	    
	    createNode(eventWriter, "environment", result.getEnvironment());
	    
	    StartElement confidencesStartElement = eventFactory.createStartElement("",
		        "", "confidences");
	    eventWriter.add(confidencesStartElement);
	    eventWriter.add(newLine);
	    
	    
	    createConfidenceNode(eventWriter, "day", result.getProbIsDay());
	    createConfidenceNode(eventWriter, "night", result.getProbIsNight());
	    createConfidenceNode(eventWriter, "Inside", result.getProbIsInside());
	    createConfidenceNode(eventWriter, "not-classifiable", result.getProbIsNotClassifiable());
	    
	    eventWriter.add(eventFactory.createEndElement("", "", "confidences"));
	    eventWriter.add(eventFactory.createEndElement("", "", "classification-result"));
	    eventWriter.add(newLine);
	    eventWriter.add(eventFactory.createEndDocument());
	    eventWriter.close();
	}
	
	private static void createConfidenceNode(XMLEventWriter eventWriter,String category, double value) throws XMLStreamException{
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		StartElement confidenceStartElement = eventFactory.createStartElement("",
		        "", "confidence");
		eventWriter.add(confidenceStartElement);
	    eventWriter.add(eventFactory.createDTD("\n"));
	    	createNode(eventWriter, "result", category);
	    	createNode(eventWriter, "value", df.format(value));
	    eventWriter.add(eventFactory.createEndElement("", "", "confidence"));
	    
	}
	
	 private static void createNode(XMLEventWriter eventWriter, String name,
		      String value) throws XMLStreamException {

		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
		    XMLEvent tab = eventFactory.createDTD("\t");
		    // create Start node
		    StartElement sElement = eventFactory.createStartElement("", "", name);
		    eventWriter.add(tab);
		    eventWriter.add(sElement);
		    // create Content
		    Characters characters = eventFactory.createCharacters(value);
		    eventWriter.add(characters);
		    // create End node
		    EndElement eElement = eventFactory.createEndElement("", "", name);
		    eventWriter.add(eElement);
		    eventWriter.add(end);

		  }
}
