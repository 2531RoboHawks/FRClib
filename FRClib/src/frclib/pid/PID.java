package frclib.pid;

public class PID {
	private double kp = 0.0;
	private double ki = 0.0;
	private double kd = 0.0;
	private double setpoint = 0.0;
	private double lastInput = 0.0;
	private double input = 0.0;
	private double output = 0.0;
	private double error = 0.0;
	private double inputChange = 0.0;
	private double outMax = 0.0;
	private double outMin = 0.0;
	private double Iterm = 0.0;
	private double offset = 0.0;
	private int ontargets = 0;
	private int ontarget = 10;

	/**
	 * 
	 * @param p        tuning value (Proportional)
	 * @param i        tuning value (Intergral)
	 * @param d        tuning value (Derivative)
	 * @param setpoint setpoint (target value)
	 */
	public PID(double p, double i, double d, double setpoint) {
		this.kp = p;
		this.ki = i;
		this.kd = d;
		this.setpoint = setpoint;
	}

	/**
	 * Sets the number of checks to see if the input is within the offset before the
	 * input is considered on target
	 * 
	 * @param count number of checks
	 */
	public void setOnTargetCount(int count) {
		ontarget = count;
	}

	/**
	 * Sets the tuning parameters
	 * 
	 * @param p tuning value (Proportional)
	 * @param i tuning value (Intergral)
	 * @param d tuning value (Derivative)
	 */
	public void setTunings(double p, double i, double d) {
		this.kp = p;
		this.ki = i;
		this.kd = d;
	}

	/**
	 * Sets the setpoint or desired input
	 * 
	 * @param value setpoints
	 */
	public void setSetpoint(double value) {
		this.setpoint = value;
	}

	/**
	 * Sets the limits of how big the outputs are alowed to be from the PID
	 * 
	 * @param min minimum output value
	 * @param max maximupt output value
	 */
	public void setOutputLimits(double min, double max) {
		this.outMax = max;
		this.outMin = min;
	}

	/**
	 * Sets how big the on target range is away from the setpoint
	 * 
	 * @param value offset
	 */
	public void setOnTargetOffset(double value) {
		this.offset = value;
	}

	/**
	 * Returns if the input of last loop was on target
	 * 
	 * @return true if on target
	 */
	public boolean onTarget() {
		if (this.setpoint + this.offset > this.input && this.setpoint - this.offset < this.input) {
			ontargets += 1;
		} else {
			ontargets = 0;
		}
		return ontargets > ontarget;
	}

	/**
	 * Runs the PID (this should be run every loop)
	 * 
	 * @param in input (current value)
	 * @return output of PID limited to the output limits defined
	 */
	public double compute(double in) {
		this.input = in;
		this.error = this.setpoint - this.input;
		this.inputChange = this.input - this.lastInput;
		this.Iterm = this.ki * this.error;
		if (this.Iterm > this.outMax) {
			this.Iterm = this.outMax;
		}
		this.output = this.kp * this.error + this.Iterm - this.kd * this.inputChange;
		this.lastInput = this.input;
		if (this.output > this.outMax) {
			this.output = this.outMax;
		} else if (this.output < this.outMin) {
			this.output = this.outMin;
		}
		return this.output;
	}
}
