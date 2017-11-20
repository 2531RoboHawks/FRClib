package frclib.sensors;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;

public class SDAR {

	Servo servo;
	Ultrasonic sensor;

	HashMap<Double, Double> data;
	boolean dir = false;

	public SDAR(int servoport, int sensorport) {
		servo = new Servo(servoport);
		sensor = new Ultrasonic(sensorport, sensorport);
	}

	public SDAR(int servoport, int sensorping, int sensorecho) {
		servo = new Servo(servoport);
		sensor = new Ultrasonic(sensorping, sensorecho);
	}

	public void init() {
		servo.set(0.5);
		sensor.setAutomaticMode(true);
	}

	public void update() {
		double angle = servo.get();
		if (angle >= 1) {
			dir = false;
		} else if (angle <= 0) {
			dir = true;
		}
		if (dir) {
			angle += 0.1;
			servo.set(angle);
		} else {
			angle -= 0.1;
			servo.set(angle);
		}
		data.put(angle, sensor.getRangeInches());

	}

	public double get(double angle) {
		return data.get(angle);
	}

}
