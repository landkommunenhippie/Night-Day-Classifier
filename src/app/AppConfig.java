package app;


import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

public class AppConfig {

	private static AppConfig INSTANCE;
	
	// Program stuff
	private String PATH_TO_LEARN = "files/learn/";
	private String INPUTFILE = "files/901";
	private String IMAGE_TYPE = ".png";
	private String XML_TYPE = ".xml";
	private String OUTPUTFILE_FOLDER = "result/";
	private String RESULT_SUFFIX  = "_img_info";
	private boolean isDebug = false; 
	
	
	AppConfig(Map<String, String> argumentMap) {
		if (checkArgExistence(argumentMap, "learnPath")) {
			this.PATH_TO_LEARN = argumentMap.get("learnPath");
		}
		if (checkArgExistence(argumentMap, "-i")) {
			String string = argumentMap.get("-i");
			String[] imageFile = string.split(Pattern.quote("."));
			this.INPUTFILE = imageFile[0];
			if (imageFile.length > 0) {
				this.IMAGE_TYPE = String.format(".%s", imageFile[1]);
			}
		}
		if (checkArgExistence(argumentMap, "-o")) {
			this.OUTPUTFILE_FOLDER = String.format("%s",
					argumentMap.get("-o"));
		}
		if (checkArgExistence(argumentMap, "-d")) {
			this.isDebug = Boolean.valueOf(argumentMap.get("-d"));
		}
		
		AppConfig.INSTANCE = this;
	}


	private boolean checkArgExistence(Map<String, String> argumentMap,
			String argName) {
		return argumentMap.get(argName) != null
				&& !argumentMap.get(argName).isEmpty();
	}

	/**
	 * get the one and only instance of Config
	 * 
	 * @return
	 */
	public static AppConfig getConfig() {
		if (AppConfig.INSTANCE == null) {
			AppConfig.INSTANCE = new AppConfig(Collections.EMPTY_MAP);
		}

		return AppConfig.INSTANCE;
	}
	
	public String PATH_TO_LEARN() {
		return PATH_TO_LEARN;
	}


	public String INPUTFILE() {
		return String.format("%s%s",INPUTFILE, IMAGE_TYPE);
	}
	
	void setINPUTFILE(String inputFileName) {
		String[] nameArr = inputFileName.split(Pattern.quote("."));
		String name = nameArr[0];
		if(nameArr.length > 1){
			IMAGE_TYPE = String.format(".%s",nameArr[1]);
		}
		INPUTFILE = name;
	}



	public String IMAGE_TYPE() {
		return IMAGE_TYPE;
	}


	public String XML_TYPE() {
		return XML_TYPE;
	}


	public String OUTPUTFILE_PICTURE() {
		String[] split = INPUTFILE.split("/");
		String imageName = split[split.length - 1];
		return String.format("%s%s%s%s", OUTPUTFILE_FOLDER, imageName,RESULT_SUFFIX, IMAGE_TYPE);
	}


	public String OUTPUTFILE_XML() {
		String[] split = INPUTFILE.split("/");
		String imageName = split[split.length - 1];
		return String.format("%s%s%s%s", OUTPUTFILE_FOLDER, imageName,RESULT_SUFFIX,XML_TYPE);
	}
	
	public boolean IS_DEBUG(){
		return isDebug;
	}


}
