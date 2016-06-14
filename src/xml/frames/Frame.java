package xml.frames;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import classifier.ClassificationResult;

public class Frame {
	
	@XmlAttribute(name="index")
	private final int index=1;
	
	@XmlElement
	private FrameInfo info;
	
	
	public void setFrameInfo(ClassificationResult result){
		FrameInfo info = new FrameInfo();
		info.setConfidences(result);
		this.info = info;
	}
}
