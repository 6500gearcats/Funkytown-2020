package frc;

import org.usfirst.frc.team6500.trc.util.TRCTypes;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Encoder;

public class SingleFlywheel
{
	private SpeedController drive;
	private Encoder encoder;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel class
	 *	@param port port number of the motor driving the device
	 *	@param controller the type of SpeedController
	 *	@param optimalSpeed the speed at which the motor should run in rotations per second
	 */
	public SingleFlywheel(int port, TRCTypes.SpeedControllerType controller, double optimalSpeed)
	{
		drive = TRCTypes.controllerTypeToObject(port, controller);
		encoder = new Encoder(port, 0);
		this.optimalSpeed = optimalSpeed;
	}

	public void spinUp()
	{
		drive.set(optimalSpeed); // TODO: not correct!
	}

	public void spinDown()
	{
		drive.stopMotor();
	}

	/**
	 *	@return if flywheel is spinning at optimal speed
	 */
	public boolean atSpeed()
	{
		
	}

	/*
	 *	This is a temporary class to do demonstation on.
	 *	I feel that the current auto driving needs improvement
	 *	that can't be done right now, so in the meantime, this
	 *	will work.
	 */
	/*
	private class FlywheelDrive extends java.lang.Thread
	{
		@Override
		public void run()
		{
			drive.set()
		}

		@Override
		public void stop()
		{
			drive.stopMotor();
			super.stop();
		}
	}
	*/
}
