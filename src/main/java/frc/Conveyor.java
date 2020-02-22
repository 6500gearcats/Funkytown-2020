package frc;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCTypes.SpeedControllerType;

public class Conveyor extends TRCDirectionalSystem
{
    private static WPI_TalonSRX m_talons[];
    private static WPI_TalonSRX m_upper;
    private static WPI_TalonSRX m_lower;

    public Conveyor(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, Constants.CONVEYOR_SPEED, -Constants.CONVEYOR_SPEED);
        m_talons = new WPI_TalonSRX[this.outputMotors.size()];
        for (int i = 0; i < this.outputMotors.size(); i++)
        {
            m_talons[i] = (WPI_TalonSRX) this.outputMotors.get(motorPorts[i]);
        }
        m_upper = m_talons[0];
        m_lower = m_talons[1];
        m_upper.setInverted(false);
        m_lower.setInverted(false);
    }
}