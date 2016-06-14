package xml.frames;

import javax.xml.bind.annotation.XmlElement;

import classifier.ClassificationResult;

public class Frames {

	@XmlElement
	private Frame frame;

	public void setFrame(ClassificationResult result){
		Frame frame = new Frame();
		frame.setFrameInfo(result);
		this.frame = frame;
		
	}
}
