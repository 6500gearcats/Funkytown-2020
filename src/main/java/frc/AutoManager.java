package frc;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;


import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.team6500.trc.auto.TRCAutoPath;
import frc.team6500.trc.auto.TRCDriveSync;
import frc.team6500.trc.util.TRCTypes.DriveSyncState;
import frc.team6500.trc.wrappers.sensors.TRCEncoder;
import frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import frc.team6500.trc.wrappers.sensors.TRCGyroBase;


public class AutoManager
{
    private static final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(Constants.AUTO_WHEELDISTANCE);
    private static final DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.AUTO_KS, Constants.AUTO_KV, Constants.AUTO_KA), driveKinematics, 10);
    private static final TrajectoryConfig config = new TrajectoryConfig(Constants.AUTO_MAX_SPEED, Constants.AUTO_MAX_ACCEL).setKinematics(driveKinematics).addConstraint(autoVoltageConstraint);
    private static DifferentialDriveOdometry m_odometry;
    private static TRCGyroBase m_gyro;
    private static TRCEncoderSet m_leftEncoder, m_rightEncoder;
    private static SpeedController[] m_motors;
    private static CANSparkMax[] m_sparks;
    private static CANPIDController[] m_PIDs;
    private static Drive m_drive;
    private static Shooter m_shooter;
    private static DifferentialDrive m_internalDrive;
    private static NetworkTableInstance tableServer;
    private static NetworkTable table;
    public static AtomicBoolean coprocessorExecutionActive;

    public static void initialize()
    {
        m_gyro = Robot.gyro;
        m_leftEncoder = Robot.leftEncoder;
        m_rightEncoder = Robot.rightEncoder;
        m_shooter = Robot.shooter;
        m_drive = Robot.drive;
        m_internalDrive = m_drive.getInternalDrive();
        m_motors = m_drive.outputMotors.clone();
        m_sparks = new CANSparkMax[m_motors.length];
        for (int i = 0; i < m_motors.length; i++)
        {
            m_sparks[i] = (CANSparkMax) m_motors[i];
        }
        m_PIDs = new CANPIDController[m_sparks.length];
        for (int i = 0; i < m_sparks.length; i++)
        {
            m_PIDs[i] = new CANPIDController(m_sparks[i]);
            m_PIDs[i].setP(Constants.AUTO_PID_P);
            m_PIDs[i].setI(Constants.AUTO_PID_I);
            m_PIDs[i].setD(Constants.AUTO_PID_D);
        }
        m_odometry = new DifferentialDriveOdometry(getRotation());


        tableServer = NetworkTableInstance.getDefault();
		tableServer.startServer();
		
        table = tableServer.getTable("/coprocessor");
        coprocessorExecutionActive = new AtomicBoolean(false);
    }

    public static void enableCoprocessorExecution()
    {
        System.out.println("Active");
        //coprocessorExecutionActive.set(true);
    }

    public static void disableCoprocessorExecution()
    {
        coprocessorExecutionActive.set(false);
    }

    public static DifferentialDriveWheelSpeeds getWheelSpeeds()
    {
        return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
    }

    public static double getAverageEncoderDistance()
    {
        return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2;
    }

    public static Rotation2d getRotation() { return Rotation2d.fromDegrees(getHeading()); }
    public static Pose2d getPose() { return m_odometry.getPoseMeters(); }
    public static double getHeading() { return m_gyro.getAngle() - 180.0; }
    public static double getTurnRate() { return m_gyro.getRate(); }
    public static TRCEncoderSet getLeftEncoder() { return m_leftEncoder; }
    public static TRCEncoderSet getRightEncoder() { return m_rightEncoder; }

    public static void resetEncoders()
    {
        m_leftEncoder.reset();
        m_rightEncoder.reset();
    }

    public static void resetOdometry(Pose2d pose)
    {
        resetEncoders();
        m_odometry.resetPosition(pose, getRotation());
    }

    public static void resetHeading()
    {
        m_gyro.reset();
    }

    public static void update()
    {
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
    }

    public static void setMaxOutput(double maxOutput)
    {
        m_internalDrive.setMaxOutput(maxOutput);
    }

    public static void tankDriveVolts(double leftVolts, double rightVolts)
    {
        m_drive.setLeftVolts(leftVolts);
        m_drive.setRightVolts(rightVolts);
        m_internalDrive.feed();
    }

    public static void enablePID()
    {
        for (CANPIDController PID : m_PIDs)
        {
            PID.setReference(0.0, ControlType.kVelocity);
        }
    }

    public static void disablePID()
    {
        for (CANPIDController PID : m_PIDs)
        {
            PID.setReference(0.0, ControlType.kDutyCycle);
        }
    }

    public static void setLeftPIDVelocity(double velocity)
    {
        m_PIDs[0].setReference(velocity, ControlType.kVelocity);
        m_PIDs[1].setReference(velocity, ControlType.kVelocity);
    }

    public static void setRightPIDVelocity(double velocity)
    {
        m_PIDs[2].setReference(velocity, ControlType.kVelocity);
        m_PIDs[3].setReference(velocity, ControlType.kVelocity);
    }

    public static void followRamsetePath(TRCAutoPath path)
    {
        Trajectory mainTrajectory = TrajectoryGenerator.generateTrajectory(path.getStartPose(), path.getTranslationList(), path.getEndPose(), config);
        RamseteController controller = new RamseteController(Constants.AUTO_RAMSETE_B, Constants.AUTO_RAMSETE_ZETA);

        Instant start = Instant.now();
        Instant now = Instant.now();
        double timeSeconds = Duration.between(start, now).toMillis() / 1000.0;

        TRCDriveSync.requestChangeState(DriveSyncState.DrivePID);
        try
        {
            TRCDriveSync.assertDrivePID();
        }
        catch(AssertionError e)
        {
            e.printStackTrace();
            return;
        }

        enablePID();

        while (timeSeconds <= mainTrajectory.getTotalTimeSeconds() && coprocessorExecutionActive.get())
        {
            try
            {
                TRCDriveSync.assertDrivePID();
            }
            catch(AssertionError e)
            {
                e.printStackTrace();
                disablePID();
                return;
            }

            now = Instant.now();
            timeSeconds = Duration.between(start, now).toMillis() / 1000.0;

            Trajectory.State goalPose = mainTrajectory.sample(timeSeconds);
            update();
            ChassisSpeeds adjustedSpeeds = controller.calculate(getPose(), goalPose);
            DifferentialDriveWheelSpeeds wheelSpeeds = driveKinematics.toWheelSpeeds(adjustedSpeeds);
            setLeftPIDVelocity(wheelSpeeds.leftMetersPerSecond);
            setRightPIDVelocity(wheelSpeeds.rightMetersPerSecond);

            if (Thread.currentThread().isInterrupted())
            {
                Thread.currentThread().interrupt();
                break;
            }
        }

        disablePID();

        TRCDriveSync.requestChangeState(DriveSyncState.Teleop);
    }

    public static void followRamsetePath(Pose2d startPose, List<Translation2d> translationList, Pose2d endPose)
    {
        followRamsetePath(new TRCAutoPath(startPose, translationList, endPose));
    }

    public static void executeFromCoprocessor()
    {
        NetworkTableEntry commandEntry = table.getEntry("Command");
        NetworkTableEntry statusEntry = table.getEntry("Status");
        NetworkTableEntry currentPoseEntry = table.getEntry("Current Pose");
        NetworkTableEntry targetPoseEntry = table.getEntry("Target Pose");
        NetworkTableEntry shooterAngleEntry = table.getEntry("Shooter Angle");
        NetworkTableEntry shooterRPMEntry = table.getEntry("Shooter RPM");
        NetworkTableEntry firedEntry = table.getEntry("Fired");

        while (true)
        {
            if (!coprocessorExecutionActive.get())
            {
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }

            String command = commandEntry.getString("");
            if (command.equals("")) { continue; }

            else if (command.equals("MOVE"))
            {
                double fallbackPose[] = {0.0, 0.0, 0.0};
                double currentPoseDoubles[] = currentPoseEntry.getDoubleArray(fallbackPose);
                double targetPoseDoubles[] = targetPoseEntry.getDoubleArray(fallbackPose);
                if (currentPoseDoubles == fallbackPose || targetPoseDoubles == fallbackPose) { continue; }

                Pose2d currentPose = new Pose2d(currentPoseDoubles[0], currentPoseDoubles[1], Rotation2d.fromDegrees(currentPoseDoubles[2]));
                Pose2d targetPose = new Pose2d(targetPoseDoubles[0], targetPoseDoubles[1], Rotation2d.fromDegrees(targetPoseDoubles[2]));
                double diffX = currentPose.getTranslation().getX() - targetPose.getTranslation().getX();
                double diffY = currentPose.getTranslation().getY() - targetPose.getTranslation().getY();
                followRamsetePath(currentPose, List.of(new Translation2d(diffX, diffY)), targetPose);
                statusEntry.forceSetString("MOVE");
            }
            else if (command.equals("AIM"))
            {
                double shooterAngle = shooterAngleEntry.getDouble(0.0);
                if (shooterAngle == 0.0) { continue; }

                if (Math.abs(m_shooter.getAngle() - shooterAngle) > Constants.SHOOTER_TOLERANCE_ANGLE)
                {
                    if (m_shooter.getAngle() - shooterAngle > 0.0)
                    {
                        m_shooter.rotateDown();
                    }
                    else if (m_shooter.getAngle() - shooterAngle < 0.0)
                    {
                        m_shooter.rotateUp();
                    }
                }
                else
                {
                    statusEntry.forceSetString("AIM");
                }
            }
            else if (command.equals("REV"))
            {
                double shooterRPM = shooterRPMEntry.getDouble(0.0);
                if (shooterRPM == 0.0) { continue; }

                if (Math.abs(m_shooter.getRPM() - shooterRPM) > Constants.SHOOTER_TOLERANCE_RPM)
                {
                    m_shooter.driveForward();
                }
                else
                {
                    m_shooter.fullStop();
                    statusEntry.forceSetString("REV");
                }
            }
            else if (command.equals("FIRE"))
            {
                if (!firedEntry.getBoolean(true))
                {
                    //m_conveyor.shiftOut();
                    firedEntry.forceSetBoolean(true);
                }
                statusEntry.forceSetString("FIRE");
            }

            if (Thread.currentThread().isInterrupted())
            {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}