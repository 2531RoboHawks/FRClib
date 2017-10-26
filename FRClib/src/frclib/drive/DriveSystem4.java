package frclib.drive;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveSystem4 {

	private SpeedController FL, FR, BL, BR;

	public DriveSystem4(SpeedController frontleftmotor, boolean ifl, SpeedController frontrightmotor, boolean ifr, SpeedController backleftmotor, boolean ibl,
			SpeedController backrightmotor, boolean ibr) {
		FL = frontleftmotor;
		FL.setInverted(ifl);
		FR = frontrightmotor;
		FR.setInverted(ifr);
		BL = backleftmotor;
		BL.setInverted(ibl);
		BR = frontleftmotor;
		BR.setInverted(ibr);
	}

	public void axisDrive(double x, double y, double z) {
		FL.set(y + x - z);
		FR.set(y - x + z);
		BL.set(y - x - z);
		BR.set(y + x + z);
	}

	public void tankDrive(double left, double right) {
		FL.set(left);
		BL.set(left);
		FR.set(right);
		BR.set(right);
	}

	public void arcadeDrive(double forward, double rotation) {
		double left = forward - rotation;
		double right = forward + rotation;
		FL.set(left);
		BL.set(left);
		FR.set(right);
		BR.set(right);
	}

	public void stop() {
		FL.stopMotor();
		BL.stopMotor();
		FR.stopMotor();
		BR.stopMotor();
	}
}
