

import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Classifier_Plugin implements PlugInFilter {


	@Override
	public void run(ImageProcessor arg0) {
//		System.out.println("Run Plugin");
//		System.out.println(arg0);
//		Main_Classifier.classify();
		
		
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
//		System.out.println("Set Up Plugin");
//		System.out.println(arg0);
//		System.out.println(arg1);
		
		return NO_IMAGE_REQUIRED;
	}



}
