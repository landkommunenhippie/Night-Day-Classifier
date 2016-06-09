import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * The Class Rotate_Plugin.
 * This is just a test Plugin to check if the import in imagej worked. 
 */
public class Rotate_Plugin implements PlugInFilter {

	private static final int DEFAUL_DIALOG_ANGLE = 2;

	@Override
	public void run(ImageProcessor ip) {
		
		GenericDialog gd = openDialog();
		int angle = (int) gd.getNextNumber();
		if(gd.getNextBoolean()){
			angle = 360 - angle;
		}
		ip.rotate(angle);
	}

	private GenericDialog openDialog() {
		
		GenericDialog gd = new GenericDialog("Drehwinkel");
		int DIALOG_DIGITS = 0;
		gd.addNumericField("Drehwinkel:", DEFAUL_DIALOG_ANGLE, DIALOG_DIGITS);
		gd.addCheckbox("Linksdrehen", false);
		
		gd.showDialog();
		return gd;
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_ALL;
	}
}
