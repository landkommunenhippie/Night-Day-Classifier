package xml.frames;

import javax.xml.bind.annotation.XmlAttribute;

public class Day {
	
	@XmlAttribute(name="confidence")
	private double conf;
	
	public void setCofidence(double confidence){
		this.conf = confidence;
	}
}
