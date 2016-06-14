package xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import xml.frames.Frames;

@XmlRootElement
public class Video {
	
	@XmlElement
	private Info info;

	@XmlElement
	private Frames frames ;
	
	
	public void setInfo(Info info){
		 this.info = info; 
	 }
	public void setFrames(Frames frames){
		 this.frames = frames; 
	 }
}
