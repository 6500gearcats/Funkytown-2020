package frc;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.*;


public class Lift extends TRCDirectionalSystem
{
    private static CANSparkMax[] m_sparks;
    private static CANPIDController[] m_PIDs;
    private static CANEncoder[] m_encoders;

    public Lift(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, true, Constants.LIFT_SPEED_UP, Constants.LIFT_SPEED_DOWN);
        m_sparks = new CANSparkMax[2];
        m_PIDs = new CANPIDController[2];
        m_encoders = new CANEncoder[2];
        for (int i = 0; i < 2; i++)
        {
            m_sparks[i] = (CANSparkMax) this.outputMotors.get(motorPorts[i]);
            m_sparks[i].setSmartCurrentLimit(Constants.LIFT_CURRENT_LIMIT);
            m_PIDs[i] = new CANPIDController(m_sparks[i]);
            m_encoders[i] = new CANEncoder(m_sparks[i]);
        }
        fullStop();
        TRCNetworkData.createDataPoint("Lift Encoder Distance");
    }

    @Override
    public void fullStop()
    {
        m_sparks[0].setIdleMode(IdleMode.kBrake);
        m_sparks[0].set(-Constants.LIFT_IDLE_RESISTANCE);
        m_sparks[1].setIdleMode(IdleMode.kBrake);
        m_sparks[1].set(Constants.LIFT_IDLE_RESISTANCE);
    }

    public void release()
    {
        m_sparks[0].setIdleMode(IdleMode.kCoast);
        m_sparks[1].setIdleMode(IdleMode.kCoast);
    }

    @Override
    public void driveForward()
    {
        TRCNetworkData.updateDataPoint("Lift Encoder Distance", (m_encoders[0].getPosition() + m_encoders[1].getPosition()) / 2);
        if ((m_encoders[0].getPosition() + m_encoders[1].getPosition()) / 2 < Constants.LIFT_MAX_HEIGHT)
        {
            super.driveForward();
        }
    }

    @Override
    public void driveReverse()
    {
        TRCNetworkData.updateDataPoint("Lift Encoder Distance", (m_encoders[0].getPosition() + m_encoders[1].getPosition()) / 2);
        /*if (m_sparks[0].getOutputCurrent() - m_sparks[1].getOutputCurrent() > Constants.LIFT_AMP_TOLERANCE)
        {
            m_sparks[0].set(Constants.LIFT_SPEED_DOWN / 2);
            m_sparks[1].set(Constants.LIFT_SPEED_DOWN);
        }
        else if (m_sparks[1].getOutputCurrent() - m_sparks[0].getOutputCurrent() > Constants.LIFT_AMP_TOLERANCE)
        {
            m_sparks[1].set(Constants.LIFT_SPEED_DOWN / 2);
            m_sparks[0].set(Constants.LIFT_SPEED_DOWN);
        }
        else
        {
            super.driveReverse();
        }*/
        super.driveReverse();
    }

    public void balance()
    {
        if (Math.abs(Robot.gyro.getRoll()) > Constants.LIFT_ANGLE_TOLERANCE)
        {
            if (Robot.gyro.getRoll() < 0)
            {
                m_sparks[0].set(Constants.LIFT_SPEED_BALANCE);
                m_sparks[1].set(-Constants.LIFT_SPEED_BALANCE);
            }
            if (Robot.gyro.getRoll() > 0)
            {
                m_sparks[0].set(-Constants.LIFT_SPEED_BALANCE);
                m_sparks[1].set(Constants.LIFT_SPEED_BALANCE);
            }
        }
    }
}