package frclib.vision;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Camera {

	private VideoCapture cam = null;

	public Camera() {
		cam = new VideoCapture();
	}

	public Mat getRawImage() {
		Mat mat = new Mat();
		cam.read(mat);
		return mat;
	}
}
