package frc;


import frc.team6500.trc.auto.TRCAutoManager;
import frc.team6500.trc.auto.TRCAutoPath;
import frc.team6500.trc.auto.TRCDriveSync;
import frc.team6500.trc.systems.TRCPneumaticSystem;
import frc.team6500.trc.util.TRCDriveParams;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCRobotManager;
import frc.team6500.trc.util.TRCTypes.*;
import frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import frc.team6500.trc.util.TRCConfigParser;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SpeedController;


public class Robot extends TimedRobot
{
    // Robot member definitions
    public static Drive drive;
    public static Intake intake;
    public static Shooter shooter;
    public static Lift lift;
    public static Conveyor conveyor;
    public static TRCGyroBase gyro;
    public static TRCEncoderSet leftEncoder, rightEncoder;
    //public static DigitalInput dio0, dio1;

    /**
     * Code here will run once as soon as the robot starts
     */
    @Override
    public void robotInit() {
        // Setup: Communications
        TRCNetworkData.initialize(DataInterfaceType.Board);
        TRCNetworkData.createDataPoint("Left Encoder");
        TRCNetworkData.createDataPoint("Right Encoder");
        TRCNetworkData.createDataPoint("Gyro");
        TRCNetworkData.createDataPoint("Ball Entered");
        TRCNetworkData.createDataPoint("Ball Stored");
        TRCNetworkData.createDataPoint("Roll");
        TRCNetworkData.createDataPoint("Pitch");
        
        // Setup: Systems: Drivetrain
        //drive = new Drive(Constants.DRIVE_WHEEL_PORTS, Constants.DRIVE_WHEEL_TYPES, Constants.DRIVE_WHEEL_INVERTS, true);
        TRCDriveSync.initialize();
        TRCDriveSync.requestChangeState(DriveSyncState.Teleop);

        // Setup: Sensors
        gyro = new TRCGyroBase(Constants.GYRO_TYPE);
        //dio0 = new DigitalInput(0);
        //dio1 = new DigitalInput(1);
        /*SpeedController[] leftMotors = new SpeedController[2]; leftMotors[0] = drive.outputMotors[0]; leftMotors[1] = drive.outputMotors[1];
        SpeedController[] rightMotors = new SpeedController[2]; rightMotors[0] = drive.outputMotors[2]; rightMotors[1] = drive.outputMotors[3];
        for (SpeedController sm : drive.outputMotors)
        {
            ((CANSparkMax) sm).setIdleMode(IdleMode.kCoast);
        }
        leftEncoder = new TRCEncoderSet(Constants.ENCODER_LEFT_PORTS, Constants.ENCODER_LEFT_DISTANCE_MULTIPLIERS, false, 2, Constants.ENCODER_LEFT_TYPES, leftMotors);
        rightEncoder = new TRCEncoderSet(Constants.ENCODER_RIGHT_PORTS, Constants.ENCODER_RIGHT_DISTANCE_MULTIPLIERS, false, 2, Constants.ENCODER_RIGHT_TYPES, rightMotors);
        *///leftEncoder.setInverted(true);
        //rightEncoder.setInverted(true);
        shooter = new Shooter(Constants.SHOOTER_MOTOR_PORTS, Constants.SHOOTER_MOTOR_TYPES);
        conveyor = new Conveyor(Constants.CONVEYOR_MOTOR_PORTS, Constants.CONVEYOR_MOTOR_TYPES);
        lift = new Lift(Constants.LIFT_MOTOR_PORTS, Constants.LIFT_MOTOR_TYPES);
        intake = new Intake(Constants.INTAKE_MOTOR_PORTS, Constants.INTAKE_MOTOR_TYPES, Constants.INTAKE_PNEUMATICS_PORTS);
        // Setup: Systems: Mechanisms
        //TRCPneumaticSystem.setupPneumatics(Constants.PNEUMATICS_PCM_ID);
        //intake = new Intake(Constants.INTAKE_MOTOR_PORTS, Constants.INTAKE_MOTOR_TYPES, Constants.INTAKE_PNEUMATICS_PORTS);
        //lift = new Lift(Constants.WINCH_MOTOR_PORTS, Constants.WINCH_MOTOR_TYPES, Constants.WINCH_SERVO_PORT_A);
        //shooter = new Shooter(Constants.SHOOTER_MOTOR_PORTS, Constants.SHOOTER_MOTOR_TYPES);

        // Setup: Input
        TRCRobotManager.initialize();
        TRCInputManager.initialize();
        TRCAutoManager.initialize();
        TRCRobotManager.registerObject("auto", AutoManager.class);
        TRCRobotManager.registerObject("shooter", shooter);
        TRCRobotManager.registerObject("conveyor", conveyor);
        TRCRobotManager.registerObject("lift", lift);
        TRCRobotManager.registerObject("intake", intake);
        TRCConfigParser.initialize();

        //AutoManager.initialize();
    }

    @Override
    public void disabledInit()
    {
        //MotorSafety.checkLiftImbalance();
    }

    /**
     * Code here will run once at the start of autonomous
     */
    @Override
    public void autonomousInit()
    {
        Pose2d zero = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
        Pose2d target = new Pose2d(0.5, 0.5, Rotation2d.fromDegrees(135));
        AutoManager.resetOdometry(zero);
        AutoManager.enableCoprocessorExecution();
        TRCAutoPath testPath = TRCAutoManager.getPath("TestPath");
        testPath.setStartPose(AutoManager.getPose());
        AutoManager.followRamsetePath(testPath);
    }

    /**
     * Code here will run continously during autonomous
     */
    @Override
    public void autonomousPeriodic()
    {
        driveRobot();
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
    }

    public void driveRobot()
    {
        // And drive the robot
        TRCDriveParams dps = TRCInputManager.getDPS("drive");
        try
        {
            TRCDriveSync.assertTeleop();
            if (TRCDriveSync.getState() == DriveSyncState.Teleop)
            {
                //drive.arcadeDrive(dps);
                TRCInputManager.checkInputs();
            }
        }
        catch (AssertionError e) { }

        //shooter.spinUp();
        //conveyor.drive();

//        TRCNetworkData.updateDataPoint("Left Encoder", leftEncoder.getDistance());
//        TRCNetworkData.updateDataPoint("Right Encoder", rightEncoder.getDistance());
        TRCNetworkData.updateDataPoint("Gyro", gyro.getAngle());
        //TRCNetworkData.updateDataPoint("dio0", dio0.get());
        //TRCNetworkData.updateDataPoint("dio1", dio1.get());
        TRCNetworkData.updateDataPoint("Roll", Robot.gyro.getRoll());
        TRCNetworkData.updateDataPoint("Pitch", Robot.gyro.getPitch());
    }

    public static void main(String... args) throws InterruptedException
    {
        RobotBase.startRobot(Robot::new);
    }
}