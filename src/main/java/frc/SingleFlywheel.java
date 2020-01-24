package frc;

import org.usfirst.frc.team6500.trc.util.TRCTypes;
import java.util.concurrent.atomic.AtomicBoolean;

import com.revrobotics.*;

/**
 *	Control a single flywheel mechanism
 */
public class SingleFlywheel
{
	private CANSparkMax drive;
	private FlywheelDriver driver;
	private AtomicBoolean atSpeed;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel
	 *	@param port port number of the SparkMax driving the Neo
	 *	@param controller the type of SpeedController
	 *	@param optimalSpeed the speed at which the motor should run in rotations per second
	 */
	public SingleFlywheel(int port, double optimalSpeed)
	{
		drive = new CANSparkMax(port, CANSparkMaxLowLevel.MotorType.kBrushless);
		driver = new FlywheelDriver();
		this.optimalSpeed = optimalSpeed;
		this.atSpeed = new AtomicBoolean(false);
		/* // code here to tune PID controller
		CANPIDController pidc = drive.getPIDController();
		pidc.setP(0.2);
		pidc.setI(0.2);
		pidc.setD(0.5);
		*/
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
			CANPIDController pidc = drive.getPIDController();
			pidc.setReference(optimalSpeed, ControlType.kVelocity);
			atSpeed.set(pidc.getIAccum() >= pidc.getIZone()); // TODO: problably not right
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
