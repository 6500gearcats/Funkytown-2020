package frc;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCTypes.*;


public class Lift extends TRCDirectionalSystem
{
    private static CANSparkMax[] m_sparks;

    public Lift(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, Constants.LIFT_SPEED_UP, Constants.LIFT_SPEED_DOWN);
        m_sparks = new CANSparkMax[2];
        for (int i = 0; i < 2; i++)
        {
            m_sparks[i] = (CANSparkMax) this.outputMotors.get(motorPorts[i]);
        }
        brake();
    }

    public void brake()
    {
        m_sparks[0].setIdleMode(IdleMode.kBrake);
        m_sparks[1].setIdleMode(IdleMode.kBrake);
    }

    public void release()
    {
        m_sparks[0].setIdleMode(IdleMode.kCoast);
        m_sparks[1].setIdleMode(IdleMode.kCoast);
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