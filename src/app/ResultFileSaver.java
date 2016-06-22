package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;

import xml.Info;
import xml.Video;
import xml.frames.Frames;
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

		try {
			JAXBContext jc = JAXBContext.newInstance(Video.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "objectDetection.xsd");
			marshaller.marshal(createVidoXml(result), new FileOutputStream(AppConfig.getConfig().OUTPUTFILE_XML()));
		} catch (JAXBException | FileNotFoundException e) {
			System.out.println("Failed to create xml file");
		}
	}
	
	public static Video createVidoXml(ClassificationResult result){
		
		
		Info info = new Info();
		info.setFile(result.getName());
		info.setHash(result.getName());
		
		Frames frames = new Frames();
		frames.setFrame(result);
		
		Video video = new Video();
		video.setInfo(info);
		video.setFrames(frames);
		return video;
	}
}
