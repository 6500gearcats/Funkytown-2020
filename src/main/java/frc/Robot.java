package frc;


import org.usfirst.frc.team6500.trc.auto.TRCDriveSync;
import org.usfirst.frc.team6500.trc.systems.TRCDriveInput;
import org.usfirst.frc.team6500.trc.systems.TRCPneumaticSystem;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotBase;


public class Robot extends TimedRobot
{
    // Robot member definitions
    Drive drive;
    Intake intake;
    Winch winch;

    /**
     * Code here will run once as soon as the robot starts
     */
    @Override
    public void robotInit() {
        // Setup: Communications
        TRCNetworkData.initializeNetworkData(DataInterfaceType.Board);
        
        // Setup: Systems: Drivetrain
        drive = new Drive(Constants.DRIVE_WHEEL_PORTS, Constants.DRIVE_WHEEL_TYPES, Constants.DRIVE_WHEEL_INVERTS,
                true);
        TRCDriveSync.initializeTRCDriveSync();
        TRCDriveSync.requestChangeState(DriveSyncState.Teleop);
        ArcadeControlTest.initializeArcadeControlTest(drive);

        // Setup: Systems: Mechanisms
        TRCPneumaticSystem.setupPneumatics(Constants.PNEUMATICS_PCM_ID);
        intake = new Intake(Constants.INTAKE_MOTOR_PORTS, Constants.INTAKE_MOTOR_TYPES, Constants.INTAKE_PNEUMATICS_PORTS);
        winch = new Winch(Constants.WINCH_MOTOR_PORTS, Constants.WINCH_MOTOR_TYPES, Constants.WINCH_SERVO_PORT_A);

        // Setup: Input
        TRCDriveInput.initializeDriveInput(Constants.INPUT_PORTS, Constants.INPUT_TYPES, Constants.SPEED_BASE, Constants.SPEED_BOOST);

        /*
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_TURN_LEFT_SLOW, ArcadeControlTest::INPUT_DRIVE_TURN_LEFT_SLOW);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_TURN_LEFT_FAST, ArcadeControlTest::INPUT_DRIVE_TURN_LEFT_FAST);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_TURN_RIGHT_SLOW, ArcadeControlTest::INPUT_DRIVE_TURN_RIGHT_SLOW);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_TURN_RIGHT_FAST, ArcadeControlTest::INPUT_DRIVE_TURN_RIGHT_FAST);

        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_FORWARD_SLOW, ArcadeControlTest::INPUT_DRIVE_FORWARD_SLOW);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_FORWARD_FAST, ArcadeControlTest::INPUT_DRIVE_FORWARD_FAST);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_BACKWARD_SLOW, ArcadeControlTest::INPUT_DRIVE_BACKWARD_SLOW);
        TRCDriveInput.bindButtonPress(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_BACKWARD_FAST, ArcadeControlTest::INPUT_DRIVE_BACKWARD_FAST);

        TRCDriveInput.bindButtonAbsence(Constants.INPUT_DRIVER_PORT, Constants.INPUT_DRIVE_BUTTONS_MANUAL, ArcadeControlTest::STOP);
        */

        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_INTAKE_DROP, intake::dropIntake);
        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_INTAKE_RAISE, intake::raiseIntake);
        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_INTAKE_PULLIN, intake::pullIn);
        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_INTAKE_PUSHOUT, intake::pushOut);

        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_WINCH_BRAKE, winch::brake);
        TRCDriveInput.bindButtonPress(Constants.INPUT_GUNNER_PORT, Constants.INPUT_WINCH_RELEASE, winch::release);
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
        //double leftSpeed = TRCDriveInput.getController(Constants.INPUT_DRIVER_PORT).getAxis(XboxAxisType.LeftY);
        //double rightSpeed = TRCDriveInput.getController(Constants.INPUT_DRIVER_PORT).getAxis(XboxAxisType.RightY);
        try
        {
            TRCDriveSync.assertTeleop();
            if (TRCDriveSync.getState() == DriveSyncState.Teleop)
            {
                //drive.tankDrive(leftSpeed, rightSpeed, true);
                TRCDriveInput.checkButtonBindings();
            }
        }
        catch (AssertionError e)
        {
            // System.out.println(e);
        }
    }

    public static void main(String... args) throws InterruptedException
    {
        RobotBase.startRobot(Robot::new);
    }
}