package xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class File {
	
	 @XmlAttribute
	 private String type = "file";
	 
	 @XmlValue
	 private String file;
	 
	 public void setFile(String relPath){
		 this.file = relPath;
	 }
}
