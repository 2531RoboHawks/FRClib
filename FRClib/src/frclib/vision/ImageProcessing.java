package frclib.vision;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ImageProcessing {

	public static ArrayList<Rect> getBlobsRGB(Mat src, double r, double g, double b, double rmax, double gmax,
			double bmax) {
		Mat mat = src.clone();
		ArrayList<Rect> blobs = new ArrayList<Rect>();
		ArrayList<MatOfPoint> c = new ArrayList<MatOfPoint>();
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);
		Core.inRange(mat, new Scalar(r, g, b), new Scalar(rmax, gmax, bmax), mat);
		Imgproc.findContours(mat, c, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		for (int i = 0; i < c.size(); i++) {
			MatOfPoint mop = c.get(i);
			if (mop != null) {
				blobs.add(Imgproc.boundingRect(mop));
			}
		}
		return blobs;
	}

	public static double getDistance(Rect rect, double fov, int objectwidth, int imagewidth) {
		if (rect != null) {
			double d = objectwidth * imagewidth / (2 * rect.width * Math.tan(fov));
			return d;
		}
		return 0;
	}

	public static Mat showBlobs(Mat src, ArrayList<Rect> blobs, Scalar color) {
		Mat mat = src.clone();
		for (int i = 0; i < blobs.size(); i++) {
			Rect r = blobs.get(i);
			if (r != null) {
				Imgproc.rectangle(mat, r.tl(), r.br(), color, 1);
			}
		}
		return mat;
	}
}
