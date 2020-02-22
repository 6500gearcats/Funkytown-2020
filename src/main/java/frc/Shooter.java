package frc;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCTypes.*;


public class Shooter extends TRCDirectionalSystem
{
    private static CANSparkMax[] m_sparks;
    private static CANPIDController[] m_PIDs;
    private static CANEncoder[] m_encoders;
    private static WPI_TalonSRX[] m_talons;
    private static WPI_TalonSRX m_pusher_talon;
    private static WPI_TalonSRX m_rotator_talon;

    // Two spark maxes for the flywheels, one talon srx for the pusher-upper wheel, one talon for rotating the mechanism
    public Shooter(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        m_sparks = new CANSparkMax[2];
        m_PIDs = new CANPIDController[2];
        m_encoders = new CANEncoder[2];
        m_talons = new WPI_TalonSRX[2];
        for (int i = 0; i < 2; i++)
        {
            m_sparks[i] = (CANSparkMax) this.outputMotors.get(motorPorts[i]);
            m_PIDs[i] = new CANPIDController(m_sparks[i]);
            m_encoders[i] = new CANEncoder(m_sparks[i]);
        }
        for (int i = 2; i < 4; i++)
        {
            m_talons[i-2] = (WPI_TalonSRX) this.outputMotors.get(motorPorts[i]);
        }
        m_pusher_talon = m_talons[0];
        m_rotator_talon = m_talons[1];
        m_pusher_talon.set(ControlMode.Velocity, 0.0);
        m_rotator_talon.set(ControlMode.Position, 0.0);
    }

    public double getRPM()
    {
        return (m_encoders[0].getVelocity() + m_encoders[1].getVelocity())/2;
    }

    public double getAngle()
    {
        return m_rotator_talon.getSelectedSensorPosition() / Constants.SHOOTER_ANGLE_MULTIPLIER;
    }

    public void rotateUp()
    {
        m_rotator_talon.set(ControlMode.PercentOutput, 1.0);
        //m_rotator_talon.set(ControlMode.Position, (getAngle() + Constants.SHOOTER_ANGLE_INTERVAL) * Constants.SHOOTER_ANGLE_MULTIPLIER);
    }

    public void rotateStop()
    {
        m_rotator_talon.set(ControlMode.PercentOutput, 0.0);
    }

    public void rotateDown()
    {
        //m_rotator_talon.set(ControlMode.Position, (getAngle() - Constants.SHOOTER_ANGLE_INTERVAL) * Constants.SHOOTER_ANGLE_MULTIPLIER);
    }

    @Override
    public void driveForward()
    {
        //m_sparks[0].set(TRCInputManager.getAxisInput("shooter"));
        //m_sparks[1].set(-TRCInputManager.getAxisInput("shooter"));
        //m_pusher_talon.set(-1.0*TRCInputManager.getAxisInput("shooter2"));
        m_sparks[0].set(0.9);
        m_sparks[1].set(-0.9);
        m_pusher_talon.set(0.8);
        //m_PIDs[0].setReference(Constants.SHOOTER_RPM_TARGET, ControlType.kVelocity);
        //m_PIDs[1].setReference(Constants.SHOOTER_RPM_TARGET, ControlType.kVelocity);
    }

    @Override
    public void fullStop()
    {
        m_sparks[0].set(0.0);
        m_sparks[1].set(0.0);
        m_pusher_talon.set(0.0);
        //m_PIDs[0].setReference(Constants.SHOOTER_RPM_IDLE, ControlType.kVelocity);
        //m_PIDs[1].setReference(Constants.SHOOTER_RPM_IDLE, ControlType.kVelocity);
    }
}