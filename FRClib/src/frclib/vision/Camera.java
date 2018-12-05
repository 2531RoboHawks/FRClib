package frclib.vision;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

	private CvSink sink;
	private CvSource source;

	private UsbCamera cam;

	public Camera(String name, int id, int w, int h) {
		cam = new UsbCamera(name, id);
		cam.setResolution(w, h);
		sink = CameraServer.getInstance().getVideo(cam);
		source = CameraServer.getInstance().putVideo(name, w, h);
	}

	public Mat getImage() {
		Mat mat = new Mat();
		sink.grabFrame(mat);
		return mat;
	}

	public void putImage(Mat mat) {
		source.putFrame(mat);
	}

	public void showLive() {
		putImage(getImage());
	}
}