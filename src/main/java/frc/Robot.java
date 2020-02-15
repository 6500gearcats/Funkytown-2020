package frc;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import edu.wpi.first.networktables.*;

import com.revrobotics.*;

import frc.team6500.trc.drive.TRCDifferentialDrive;
import frc.team6500.trc.system.TRCDriveInput;
import frc.team6500.trc.sensor.TRCDigitalEncoder;
import frc.team6500.trc.sensor.TRCEncoderGroup;
import frc.team6500.trc.util.TRCController;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.*;
import frc.team6500.trc.sensor.TRCSparkMaxEncoder;
import frc.team6500.trc.sensor.TRCnavXGyro;

public class Robot extends TimedRobot
{
	// Robot member definitions
	private CANSparkMax flController, frController, rlController, rrController;
	private TRCSparkMaxEncoder flEncoder, frEncoder, rlEncoder, rrEncoder;
	private SpeedControllerGroup leftControllers, rightControllers;
	private TRCEncoderGroup leftEncoders, rightEncoders;
	private TRCnavXGyro gyroscope;

	private TRCController controller;
	private Drive drive;

	private NetworkTable table;

	private double fbExternalControl = 0.0;
	private double rtExternalControl = 0.0;
	// Intake intake;
	// Winch winch;

	/**
	 * Code here will run once as soon as the robot starts
	 */
	@Override
	public void robotInit()
	{
		System.out.println("Begin robotInit()");

		flController = new CANSparkMax(Constants.DRIVE_MOTOR_FRONTLEFT, CANSparkMax.MotorType.kBrushless);
		flController.setInverted(Constants.DRIVE_MOTOR_FRONTLEFT_INVERTED);

		rlController = new CANSparkMax(Constants.DRIVE_MOTOR_REARLEFT, CANSparkMax.MotorType.kBrushless);
		rlController.setInverted(Constants.DRIVE_MOTOR_REARLEFT_INVERTED);

		frController = new CANSparkMax(Constants.DRIVE_MOTOR_FRONTRIGHT, CANSparkMax.MotorType.kBrushless);
		frController.setInverted(Constants.DRIVE_MOTOR_FRONTRIGHT_INVERTED);

		rrController = new CANSparkMax(Constants.DRIVE_MOTOR_REARRIGHT, CANSparkMax.MotorType.kBrushless);
		rrController.setInverted(Constants.DRIVE_MOTOR_REARRIGHT_INVERTED);
		System.out.println("Created all SpeedControllers");

		leftControllers = new SpeedControllerGroup(flController, rlController);
		rightControllers = new SpeedControllerGroup(frController, rrController);
		System.out.println("Created SpeedControllerGroups");

		flEncoder = new TRCSparkMaxEncoder(flController);
		flEncoder.setPositionConversionFactor(2.0); // 1.96
		flEncoder.setPosition(0.0);

		rlEncoder = new TRCSparkMaxEncoder(rlController);
		rlEncoder.setPositionConversionFactor(2.0);
		rlEncoder.setPosition(0.0);

		frEncoder = new TRCSparkMaxEncoder(frController);
		frEncoder.setPositionConversionFactor(2.0);
		frEncoder.setPosition(0.0);

		rrEncoder = new TRCSparkMaxEncoder(rrController);
		rrEncoder.setPositionConversionFactor(2.0);
		rrEncoder.setPosition(0.0);
		System.out.println("Created all Encoders");

		leftEncoders = new TRCEncoderGroup(flEncoder, rlEncoder);
		leftEncoders.setInverted(true);

		rightEncoders = new TRCEncoderGroup(frEncoder, rrEncoder);
		rightEncoders.setInverted(false);
		System.out.println("Created TRCEncoderGroups");

		gyroscope = new TRCnavXGyro();
		System.out.println("Created gyro");

		// TRCNetworkData.initializeNetworkData(DataInterfaceType.Board);
		// System.out.println("TRCNetworkData initialized");

		drive = new Drive(leftControllers, rightControllers, leftEncoders, rightEncoders, gyroscope, 0.1);
		System.out.println("Created drivetrain object");

		table = NetworkTableInstance.getDefault().getTable("/SmartDashboard");
		table.addEntryListener(Constants.TABLE_KEY_FORWARDBACK, this.drive, EntryListenerFlags.kUpdate);
		table.addEntryListener(Constants.TABLE_KEY_ROTATE, this.drive, EntryListenerFlags.kUpdate);
		System.out.println("Created NetworkTable");

		TRCDriveInput.initializeDriveInput(Constants.INPUT_PORTS, Constants.INPUT_TYPES, Constants.SPEED_BASE, Constants.SPEED_BOOST);
		System.out.println("Initialized drive input");
	}

	@Override
	public void disabledInit()
	{
		//MotorSafety.checkLiftImbalance();
	}

	// CANSparkMax fl, fr, rl, rr;
	// CANEncoder fle, fre, rle, rre;

	/**
	 * Code here will run once at the start of autonomous
	 */
	@Override
	public void autonomousInit()
	{
		// TRCDrivePID.run(DriveActionType.Forward, 6.0);
		// TRCDrivePID.run(DriveActionType.Rotate, 90.0);
		// TRCDrivePID.run(DriveActionType.Forward, -6.0);
		// TRCDrivePID.run(DriveActionType.Rotate, 90.0);
		// TRCDrivePID.run(DriveActionType.Forward, -6.0);
		// TRCDrivePID.run(DriveActionType.Rotate, 90.0);
		// TRCDrivePID.run(DriveActionType.Forward, -6.0);
		// TRCDrivePID.run(DriveActionType.Rotate, 90.0);

		// CANSparkMax fl, fr, rl, rr;
		// CANEncoder fle, fre, rle, rre;
		// CANPIDController flc, frc, rlc, rrc;

		// fl = new CANSparkMax(Constants.DRIVE_WHEEL_FRONTLEFT, CANSparkMax.MotorType.kBrushless);
		// fr = new CANSparkMax(Constants.DRIVE_WHEEL_FRONTRIGHT, CANSparkMax.MotorType.kBrushless);
		// rl = new CANSparkMax(Constants.DRIVE_WHEEL_REARLEFT, CANSparkMax.MotorType.kBrushless);
		// rr = new CANSparkMax(Constants.DRIVE_WHEEL_REARRIGHT, CANSparkMax.MotorType.kBrushless);

		// fle = fl.getEncoder(/*com.revrobotics.EncoderType.kHallSensor, 1000000*/);
		// fre = fr.getEncoder(/*com.revrobotics.EncoderType.kHallSensor, 1000000*/);
		// rle = rl.getEncoder(/*com.revrobotics.EncoderType.kHallSensor, 1000000*/);
		// rre = rr.getEncoder(/*com.revrobotics.EncoderType.kHallSensor, 1000000*/);

		// flc = fl.getPIDController();
		// frc = fr.getPIDController();
		// rlc = rl.getPIDController();
		// rrc = rr.getPIDController();

		// ==============================

		// get average of all motors
		// double averageDistance = (fle.getPosition() + fre.getPosition() + rle.getPosition() + rre.getPosition()) / 4.0;
		// set target

		// System.out.println("----------------------------------------------------------------");

		// flc.setP( 1.00); flc.setI( 0.00); flc.setD( 0.05); flc.setOutputRange(-0.1,  0.1);
		// frc.setP( 1.00); frc.setI( 0.00); frc.setD( 0.05); frc.setOutputRange(-0.1,  0.1);
		// rlc.setP( 1.00); rlc.setI( 0.00); rlc.setD( 0.05); rlc.setOutputRange(-0.1,  0.1);
		// rrc.setP( 1.00); rrc.setI( 0.00); rrc.setD( 0.05); rrc.setOutputRange(-0.1,  0.1);

		// flc.setReference(1.0, ControlType.kPosition);
		// frc.setReference(1.0, ControlType.kPosition);
		// rlc.setReference(1.0, ControlType.kPosition);
		// rrc.setReference(1.0, ControlType.kPosition);

		// System.out.println("================================================================");

		// ==============================

		// fl.stopMotor();
		// fr.stopMotor();
		// rl.stopMotor();
		// rr.stopMotor();
		// drive.pidDrive(6.0, 0.0);
	}

	/**
	 * Code here will run continously during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		flEncoder.setPosition(0.0);
		rlEncoder.setPosition(0.0);
		frEncoder.setPosition(0.0);
		rrEncoder.setPosition(0.0);
		// gyroscope.reset();
		// driveRobot();
		// System.out.println(leftEncoders.getDistance());
		// drive.tankDrive(0.0, 0.0, false);
		// drive.pidDrive(fbExternalControl, rtExternalControl);
		drive.externalDrive();
		// uncomment the following to enable autodrive
	}

	/**
	 * Code here will run once at the start of teleop
	 */
	@Override
	public void teleopInit()
	{
	
		// Nothing to do here ¯\_(ツ)_/¯
	}

	/**
	 * Code here will run continously during teleop
	 */
	@Override
	public void teleopPeriodic()
	{
		driveRobot();
		// flController.set(0.2); 3
		// frController.set(0.2); 4
		// rlController.set(0.2); 1
		// rrController.set(0.2); 2
	}

	@Override
	public void disabledPeriodic() {}

	@Override
	public void robotPeriodic()
	{

	}

	public void driveRobot()
	{
		// And drive the robot
		double leftSpeed = TRCDriveInput.getController(Constants.INPUT_DRIVER_PORT).getAxis(XboxAxisType.LeftY);
		double rightSpeed = TRCDriveInput.getController(Constants.INPUT_DRIVER_PORT).getAxis(XboxAxisType.RightY);
		drive.tankDrive(leftSpeed, rightSpeed, true);
	}

/*
	private static TRCEncoderGroup leftEncoders()
	{
		TRCDigitalEncoder front, rear;

		DigitalInput frontInput = new DigitalInput(Constants.DRIVE_WHEEL_FRONTLEFT);
		DigitalInput rearInput = new DigitalInput(Constants.DRIVE_WHEEL_REARLEFT);

		front = new TRCDigitalEncoder(frontInput, Constants.DEAD_INPUT, Constants.DRIVE_WHEEL_FRONTLEFT_INVERTED);
		rear = new TRCDigitalEncoder(rearInput, Constants.DEAD_INPUT, Constants.DRIVE_WHEEL_REARLEFT_INVERTED);

		return new TRCEncoderGroup(front, rear);
	}

	private static TRCEncoderGroup rightEncoders()
	{
		TRCDigitalEncoder front, rear;

		DigitalInput frontInput = new DigitalInput(Constants.DRIVE_WHEEL_FRONTRIGHT);
		DigitalInput rearInput = new DigitalInput(Constants.DRIVE_WHEEL_REARRIGHT);

		front = new TRCDigitalEncoder(frontInput, Constants.DEAD_INPUT, Constants.DRIVE_WHEEL_FRONTRIGHT_INVERTED);
		rear = new TRCDigitalEncoder(rearInput, Constants.DEAD_INPUT, Constants.DRIVE_WHEEL_REARRIGHT_INVERTED);

		return new TRCEncoderGroup(front, rear);
	}*/

	public static void main(String... args) throws InterruptedException
	{
		RobotBase.startRobot(Robot::new);
	}
}