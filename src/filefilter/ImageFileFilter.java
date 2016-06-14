package filefilter;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class ImageFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		String[] fileNameArr = pathname.getName().split(Pattern.quote("."));
		if(pathname.isFile() && fileNameArr.length > 1){ 
			String fileDataType = fileNameArr[fileNameArr.length - 1 ];
			
			if(fileDataType.equals("png") || fileDataType.equals("jpg")){
				return true;
			}
		}
			
		return false;
	}
}
