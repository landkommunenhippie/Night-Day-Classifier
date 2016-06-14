package xml;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Hash {
	 
	@XmlAttribute
	 private String type = "md5";
	 
	 @XmlValue
	 private String hash;

	 public void setHash(String relPath){
		
		try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 md.update(relPath.getBytes());
			 byte[] digest = md.digest();
			 StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
				}

			 this.hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Digestcreation failed");
		}
		 
	 }
}
