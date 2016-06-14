package xml.frames;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class ConfidenceValue {
	

	@XmlAttribute(name="confidence")
	protected double conf;
	
	public void setCofidence(double confidence){
		this.conf = confidence;
	}
}
