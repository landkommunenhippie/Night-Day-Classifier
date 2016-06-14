package xml.frames;

import javax.xml.bind.annotation.XmlElement;

import classifier.ClassificationResult;

public class FrameInfo {
	
	@XmlElement
	private Day day;

	@XmlElement
	private Night night;
	
	@XmlElement
	private Inside inside;

	@XmlElement(name="bad-weather")
	private BadWeather badWeather;
	
	
	public void setConfidences(ClassificationResult result){
		Day day = new Day();
		day.setCofidence(result.getProbIsDay());
		this.day = day; 
		Night night = new Night();
		night.setCofidence(result.getProbIsNight());
		this.night = night;
		Inside ins = new Inside();
		ins.setCofidence(result.getProbIsInside());
		this.inside  = ins;
		BadWeather bw = new BadWeather();
		bw.setCofidence(result.getProbIsBadWeather());
		this.badWeather = bw;
	}
}
