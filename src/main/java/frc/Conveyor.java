package frc;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.SpeedControllerType;

public class Conveyor extends TRCDirectionalSystem
{
    private static WPI_TalonSRX m_talons[];
    private static WPI_TalonSRX m_upper;
    private static WPI_TalonSRX m_lower;
    private static DigitalInput m_entered, m_stored, m_full;
    private static boolean m_shooting;

    public Conveyor(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        m_talons = new WPI_TalonSRX[this.outputMotors.size()];
        for (int i = 0; i < this.outputMotors.size(); i++)
        {
            m_talons[i] = (WPI_TalonSRX) this.outputMotors.get(motorPorts[i]);
        }
        m_upper = m_talons[1];
        m_lower = m_talons[0];
        m_upper.setInverted(false);
        m_lower.setInverted(false);
        m_entered = new DigitalInput(Constants.CONVEYOR_DIO_ENTERED);
        m_stored = new DigitalInput(Constants.CONVEYOR_DIO_STORED);
        m_full = new DigitalInput(Constants.CONVEYOR_DIO_FULL);
        m_shooting = false;
    }

    public void setShooting(boolean shooting)
    {
        m_shooting = shooting;
    }

    @Override
    public void driveForward()
    {
        m_upper.set(Constants.CONVEYOR_UPPER_SPEED);
        m_lower.set(Constants.CONVEYOR_LOWER_SPEED);
    }

    @Override
    public void driveReverse()
    {
        m_upper.set(-Constants.CONVEYOR_UPPER_SPEED);
        m_lower.set(-Constants.CONVEYOR_LOWER_SPEED);
    }

    public void update()
    {
        //if (!m_full.get())
        //{
            if (!m_entered.get() || !m_stored.get())
            {
                this.driveForward();
            }
            else
            {
                if (m_entered.get() && m_stored.get())
                {
                    this.fullStop();
                }
            }
        /*}
        else
        {
            if (m_shooting)
            {
                this.driveForward();
            }
            else
            {
                this.fullStop();
            }
        }*/

        TRCNetworkData.updateDataPoint("Ball Entered", m_entered.get());
        TRCNetworkData.updateDataPoint("Ball Stored", m_stored.get());
    }
}