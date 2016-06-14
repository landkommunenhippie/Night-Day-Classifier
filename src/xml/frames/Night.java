package xml.frames;

import javax.xml.bind.annotation.XmlAttribute;

public class Night {

	@XmlAttribute(name="confidence")
	private double conf = 0.5;

	public void setCofidence(double confidence){
		this.conf = confidence;
	}
}
