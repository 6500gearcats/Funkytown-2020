package frc;

import org.usfirst.frc.team6500.trc.util.TRCTypes;
import com.revrobotics.*;

/**
 *	Control a single flywheel mechanism
 */
public class SingleFlywheel
{
	private CANSparkMax drive;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel
	 *	@param port port number of the SparkMax driving the Neo
	 *	@param optimalSpeed the speed at which the motor should run in rotations per second
	 */
	public SingleFlywheel(int port, double optimalSpeed)
	{
		drive = new CANSparkMax(port, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.optimalSpeed = optimalSpeed;
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
		drive.getPIDController().setReference(optimalSpeed, ControlType.kVelocity);
	}

	/**
	 *	Stop motor power and allow flywheel to spin down
	 */
	public void spinDown()
	{
		drive.stopMotor();
	}

	/**
	 *	@return if flywheel is spinning at optimal speed
	 */
	public boolean atSpeed() { return drive.getPIDController().getIAccum() >= drive.getPIDController().getIZone(); }
}
