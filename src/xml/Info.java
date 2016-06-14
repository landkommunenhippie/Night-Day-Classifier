package xml;

import java.util.Random;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Info {

	private final String idChars = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";

	@XmlElement
	private File file;

	@XmlElement
	private Hash hash;

	@XmlElement
	private String id;

	@XmlElement
	private String name;

	@XmlElement
	private String description = "Nacht- und Tageserkennung des Bildes - HTWK Leipzig, Sonja Mauersberger, Tobias Hahn";

	@XmlElement
	private String frameCount = "1";

	public Info() {
		Random rnd = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(idChars.charAt(rnd.nextInt(idChars.length())));
		}
		this.id = builder.toString();

	}

	public void setHash(String relPath) {
		Hash hash = new Hash();
		hash.setHash(relPath);
		this.hash = hash;

	}

	public void setFile(String relPath) {
		File file = new File();
		file.setFile(relPath);
		this.file = file;
		this.name = relPath;
	}

}
