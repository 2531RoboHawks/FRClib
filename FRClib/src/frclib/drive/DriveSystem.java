package frclib.drive;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveSystem {

	private SpeedController[] motors;

	public DriveSystem(SpeedController[] m) {
		motors = m;
	}

	/**
	 * Method for mech drive
	 * 
	 * @param x
	 *            x power
	 * @param y
	 *            y power
	 * @param r
	 *            rotation power
	 */
	public void drive(double x, double y, double r) {
		double a = Math.atan2(y, x);
		double FL = -(Math.abs(Math.sin(a)) * y) + (Math.abs(Math.cos(a)) * x) + r;
		double FR = (Math.abs(Math.sin(a)) * y) + (Math.abs(Math.cos(a)) * x) + r;
		double BL = -(Math.abs(Math.sin(a)) * y) - (Math.abs(Math.cos(a)) * x) + r;
		double BR = (Math.abs(Math.sin(a)) * y) - (Math.abs(Math.cos(a)) * x) + r;
		for (int i = 0; i < motors.length; i += 4) {
			motors[i].set(FL);
			motors[i + 1].set(FR);
			motors[i + 2].set(BL);
			motors[i + 3].set(BR);
		}
	}

	/**
	 * Method for arcade drive and tank drive
	 * 
	 * @param a0
	 *            left / power
	 * @param a1
	 *            right / steering
	 * @param arcade
	 *            true is arcade false is tank
	 */
	public void drive(double a0, double a1, boolean arcade) {
		for (int i = 0; i < motors.length - 1; i += 2) {
			if (arcade) {
				motors[i].set(a0 - (a1 / 2));
				motors[i + 1].set(a0 + (a1 / 2));
			} else {
				motors[i].set(a0);
				motors[i + 1].set(a1);
			}
		}
	}

	/**
	 * Stops all motors
	 */
	public void stop() {
		for (int i = 0; i < motors.length; i++) {
			motors[i].stopMotor();
		}
	}
}
