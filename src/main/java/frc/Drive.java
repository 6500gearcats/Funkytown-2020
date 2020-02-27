package frc;

import frc.team6500.trc.drive.TRCDifferentialDrive;
import frc.team6500.trc.sensor.TRCEncoder;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.networktables.*;

public class Drive extends TRCDifferentialDrive implements TableEntryListener
{
	private double lExtControl = 0.0;
	private double rExtControl = 0.0;

	/**
	 *	Construct a Drive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 *	@param gyroscope	the gyroscope to use for rotation (pass null if none)
	 */
	public Drive(SpeedController leftMotor, SpeedController rightMotor, 
				 TRCEncoder leftEncoder, TRCEncoder rightEncoder, Gyro gyroscope)
	{
		super(leftMotor, rightMotor, leftEncoder, rightEncoder, gyroscope);
	}

	/**
	 *	Construct a TRCDifferentialDrive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 *	@param gyroscope	the gyroscope to use for rotation (pass null if none)
	 *	@param maxAutoSpeed the maximum speed the robot will drive when driving pid style
	 */
	public Drive(SpeedController leftMotor, SpeedController rightMotor, 
				 TRCEncoder leftEncoder, TRCEncoder rightEncoder, Gyro gyroscope,
				 double maxAutoSpeed)
	{
		super(leftMotor, rightMotor, leftEncoder, rightEncoder, gyroscope, maxAutoSpeed);
	}

	/**
	 *	Construct a TRCDifferentialDrive with motors (you cannot auto drive with this setup!)
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 */
	public Drive(SpeedController leftMotor, SpeedController rightMotor)
	{
		super(leftMotor, rightMotor);
	}

	/**
	 *	Call this method in the periodic Robot class to drive the robot from the network
	 */
	public void externalDrive()
	{
		tankDrive(lExtControl, rExtControl);
	}

	/**
	 *	Reset external data points
	 */
	public void resetExternalPoints()
	{
		lExtControl = 0.0;
		rExtControl = 0.0;
	}

	public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags)
	{
		double rawValue = value.getDouble();

		if (key.equals(Constants.TABLE_KEY_LEFTPOWER))
		{
			lExtControl = rawValue;
			// System.out.println("Left: " + rawValue);
		}
		else if (key.equals(Constants.TABLE_KEY_RIGHTPOWER))
		{
			rExtControl = rawValue;
			// System.out.println("Right: " + rawValue);
		}
	}
}
