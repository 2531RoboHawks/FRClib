package frclib.vision;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionTracking {

	Camera cam;
	Image frame;
	NIVision.Range v1 = new NIVision.Range();
	NIVision.Range v2 = new NIVision.Range();
	NIVision.Range v3 = new NIVision.Range();

	float areamin = 10;

	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);

	boolean tracking = false;

	boolean captureing = false;

	int session;

	public boolean isStarted = false;

	public VisionTracking(Camera cam) {
		this.cam = cam;
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		session = NIVision.IMAQdxOpenCamera(cam.getName(),
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
	}

	public void startCam() {
		tracking = true;
		captureing = true;
		NIVision.IMAQdxStartAcquisition(session);
		isStarted = true;
	}

	public void stopCam() {
		NIVision.IMAQdxStopAcquisition(session);
		isStarted = false;
		tracking = false;
		captureing = false;
	}

	public void displayRawImage() {
		if (captureing) {
			cam.getImage(frame, session);
			CameraServer.getInstance().setImage(frame);
		}
	}

	public void setColor(int max1, int min1, int max2, int min2, int max3, int min3) {
		v1.maxValue = max1;
		v1.minValue = min1;
		v2.maxValue = max2;
		v2.minValue = min2;
		v3.maxValue = max3;
		v3.minValue = min3;
	}

	public void HSVdisplayBlobs() {
		if (tracking) {
			ArrayList<NIVision.Rect> blobs = HSVgetBlobs();
			for (int i = 0; i < blobs.size(); i++) {
				NIVision.Rect blob = blobs.get(i);
				NIVision.imaqDrawShapeOnImage(frame, frame, blob, NIVision.DrawMode.DRAW_VALUE,
						NIVision.ShapeMode.SHAPE_RECT, 255);
			}
			CameraServer.getInstance().setImage(frame);
		}
	}

	public void RGBdisplayBlobs() {
		if (tracking) {
			ArrayList<NIVision.Rect> blobs = RGBgetBlobs();
			for (int i = 0; i < blobs.size(); i++) {
				NIVision.Rect blob = blobs.get(i);
				NIVision.imaqDrawShapeOnImage(frame, frame, blob, NIVision.DrawMode.DRAW_VALUE,
						NIVision.ShapeMode.SHAPE_RECT, 255);
			}
			CameraServer.getInstance().setImage(frame);
		}
	}

	public void HSLdisplayBlobs() {
		if (tracking) {
			ArrayList<NIVision.Rect> blobs = HSLgetBlobs();
			for (int i = 0; i < blobs.size(); i++) {
				NIVision.Rect blob = blobs.get(i);
				NIVision.imaqDrawShapeOnImage(frame, frame, blob, NIVision.DrawMode.DRAW_VALUE,
						NIVision.ShapeMode.SHAPE_RECT, 255);
			}
			CameraServer.getInstance().setImage(frame);
		}
	}

	public ArrayList<NIVision.Rect> HSLgetBlobs() {
		if (tracking) {
			cam.getImage(frame, session);
			Image filteredframe = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			ArrayList<NIVision.Rect> blobs = new ArrayList<NIVision.Rect>();
			NIVision.imaqColorThreshold(filteredframe, frame, 255, NIVision.ColorMode.HSL, v1, v2, v3);
			int numblobs = NIVision.imaqCountParticles(filteredframe, 1);
			SmartDashboard.putNumber("Blobs in View", numblobs);
			for (int i = 0; i < numblobs; i++) {
				int top = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				int left = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				int width = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				int height = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				blobs.add(new NIVision.Rect(top, left, height, width));
			}
			return blobs;
		} else {
			return null;
		}
	}

	public ArrayList<NIVision.Rect> RGBgetBlobs() {
		if (tracking) {
			cam.getImage(frame, session);
			Image filteredframe = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			ArrayList<NIVision.Rect> blobs = new ArrayList<NIVision.Rect>();
			NIVision.imaqColorThreshold(filteredframe, frame, 255, NIVision.ColorMode.RGB, v1, v2, v3);
			int numblobs = NIVision.imaqCountParticles(filteredframe, 1);
			SmartDashboard.putNumber("Blobs in View", numblobs);
			for (int i = 0; i < numblobs; i++) {
				int top = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				int left = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				int width = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				int height = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				blobs.add(new NIVision.Rect(top, left, height, width));
			}
			return blobs;
		} else {
			return null;
		}
	}

	public ArrayList<NIVision.Rect> HSVgetBlobs() {
		if (tracking) {
			cam.getImage(frame, session);
			Image filteredframe = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			ArrayList<NIVision.Rect> blobs = new ArrayList<NIVision.Rect>();
			NIVision.imaqColorThreshold(filteredframe, frame, 255, NIVision.ColorMode.HSV, v1, v2, v3);
			int numblobs = NIVision.imaqCountParticles(filteredframe, 1);
			SmartDashboard.putNumber("Blobs in View", numblobs);
			for (int i = 0; i < numblobs; i++) {
				int top = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				int left = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				int width = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				int height = (int) NIVision.imaqMeasureParticle(filteredframe, i, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				blobs.add(new NIVision.Rect(top, left, height, width));
			}
			return blobs;
		} else {
			return null;
		}
	}
}
