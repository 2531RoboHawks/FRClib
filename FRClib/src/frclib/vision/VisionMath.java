package frclib.vision;

import com.ni.vision.NIVision;

public class VisionMath {

	public static double computeDistance(NIVision.Rect rect, double fov, int screenwidth, int targetWidthFt) {
		if (rect != null) {
			double d = targetWidthFt * screenwidth / (2 * rect.width * Math.tan(fov));
			return d;
		}
		return 0;
	}
}