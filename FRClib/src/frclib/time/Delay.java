package frclib.time;

import edu.wpi.first.wpilibj.command.Command;

public class Delay extends Command {

	long milliseconds;
	long endtime = 0;

	public Delay(long millis) {
		milliseconds = millis;
	}

	protected void initialize() {
		System.out.println("-> Delay");
		endtime = System.currentTimeMillis() + milliseconds;
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return System.currentTimeMillis() > endtime;
	}

	protected void end() {
		System.out.println("-! Delay");
	}

	protected void interrupted() {
		end();
	}
}
