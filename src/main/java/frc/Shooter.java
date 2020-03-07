package frc;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.*;


public class Shooter extends TRCDirectionalSystem
{
    private static CANSparkMax[] m_sparks;
    private static CANPIDController[] m_PIDs;
    private static CANEncoder[] m_encoders;

    // Two spark maxes for the flywheels, one talon srx for the pusher-upper wheel, one talon for rotating the mechanism
    public Shooter(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        m_sparks = new CANSparkMax[3];
        m_PIDs = new CANPIDController[3];
        m_encoders = new CANEncoder[3];

        for (int i = 0; i < 3; i++)
        {
            m_sparks[i] = (CANSparkMax) this.outputMotors.get(motorPorts[i]);
            m_PIDs[i] = new CANPIDController(m_sparks[i]);
            m_encoders[i] = new CANEncoder(m_sparks[i]);
        }

        m_PIDs[0].setFF(0.000175);
        m_PIDs[0].setP(0.0001);
        m_PIDs[1].setFF(0.000175);
        m_PIDs[1].setP(0.0001);
        m_PIDs[1].setFF(0.000100);
        m_PIDs[1].setP(0.00004);

        TRCNetworkData.updateDataPoint("FF 0", 170);
        TRCNetworkData.updateDataPoint("FF 1", 180);
        TRCNetworkData.updateDataPoint("FF 2", 160);
        TRCNetworkData.updateDataPoint("AB RPM Target", Constants.SHOOTER_RPM_TARGET_AB);
        TRCNetworkData.updateDataPoint("Ejector RPM Target", Constants.SHOOTER_RPM_TARGET_EJECTOR);

        m_encoders[0].setPositionConversionFactor(1.0);
        m_encoders[1].setPositionConversionFactor(1.0);
        m_encoders[2].setPositionConversionFactor(1.0);
        m_encoders[0].setVelocityConversionFactor(1.0);
        m_encoders[1].setVelocityConversionFactor(1.0);
        m_encoders[2].setVelocityConversionFactor(1.0);
    }

    public double getRPMForward()
    {
        return (m_encoders[0].getVelocity() + -m_encoders[1].getVelocity())/2;
    }

    public double getRPMEjector()
    {
        return m_encoders[2].getVelocity();
    }

    @Override
    public void driveForward()
    {
        Robot.conveyor.setShooting(true);
        m_PIDs[0].setReference(Constants.SHOOTER_RPM_TARGET_AB, ControlType.kVelocity);
        m_PIDs[1].setReference(-Constants.SHOOTER_RPM_TARGET_AB, ControlType.kVelocity);
        m_PIDs[2].setReference(Constants.SHOOTER_RPM_TARGET_EJECTOR, ControlType.kVelocity);
    }

    @Override
    public void fullStop()
    {
        Robot.conveyor.setShooting(false);
        m_PIDs[0].setReference(Constants.SHOOTER_RPM_IDLE, ControlType.kVelocity);
        m_PIDs[1].setReference(-Constants.SHOOTER_RPM_IDLE, ControlType.kVelocity);
        m_PIDs[2].setReference(Constants.SHOOTER_RPM_IDLE, ControlType.kVelocity);
    }

    public void updateDebugInfo()
    {
        TRCNetworkData.updateDataPoint("RPM 0", m_encoders[0].getVelocity());
        TRCNetworkData.updateDataPoint("RPM 1", m_encoders[1].getVelocity());
        TRCNetworkData.updateDataPoint("RPM 2", m_encoders[2].getVelocity());
        m_PIDs[0].setFF(TRCNetworkData.getDataPoint("FF 0") / 1000000.0);
        m_PIDs[1].setFF(TRCNetworkData.getDataPoint("FF 1") / 1000000.0);
        m_PIDs[2].setFF(TRCNetworkData.getDataPoint("FF 2") / 1000000.0);
        Constants.SHOOTER_RPM_TARGET_AB = TRCNetworkData.getDataPoint("AB RPM Target");
        Constants.SHOOTER_RPM_TARGET_EJECTOR = TRCNetworkData.getDataPoint("Ejector RPM Target");
    }
}