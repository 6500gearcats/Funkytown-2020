package frc;

import org.usfirst.frc.team6500.trc.util.TRCTypes;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Encoder;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *	Control a single flywheel mechanism
 */
public class SingleFlywheel
{
	private PIDController pidc;
	private SpeedController drive;
	private Encoder encoder;
	private FlywheelDriver driver;
	private AtomicBoolean atSpeed;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel
	 *	@param port port number of the motor driving the device
	 *	@param controller the type of SpeedController
	 *	@param optimalSpeed the speed at which the motor should run in rotations per second
	 */
	public SingleFlywheel(int port, TRCTypes.SpeedControllerType controller, double optimalSpeed)
	{
		drive = TRCTypes.controllerTypeToObject(port, controller);
		encoder = new Encoder(port, 0);
		pidc = new PIDController(0.5, 0.5, 0.1);
		driver = new FlywheelDriver();
		this.optimalSpeed = optimalSpeed;
		this.atSpeed = new AtomicBoolean(false);
	}

	/**
	 *	Start and spin flywheel up to optimal speed
	 */
	public void spinUp()
	{
		driver.start();
	}

	/**
	 *	Stop motor power and allow flywheel to spin down
	 */
	public void spinDown()
	{
		driver.interrupt();
	}

	/**
	 *	@return if flywheel is spinning at optimal speed
	 */
	public boolean atSpeed() { return this.atSpeed.get(); }

	/*
	 *	This is a temporary class to do demonstration on.
	 *	I feel that the current auto driving needs improvement
	 *	that can't be done right now, so in the meantime, this
	 *	will work.
	 */
	private class FlywheelDriver extends java.lang.Thread
	{
		@Override
		public void run()
		{
			pidc.setSetpoint(optimalSpeed);
			while (true)
			{
				double calc = pidc.calculate(encoder.getRaw());
				drive.set(calc);
				atSpeed.set(pidc.atSetpoint());
			}
		}

		@Override
		public void interrupt()
		{
			super.interrupt();
			atSpeed.set(false);
			drive.stopMotor();
		}
	}
}
