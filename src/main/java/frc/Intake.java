package frc;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.systems.TRCPneumaticSystem;
import frc.team6500.trc.util.TRCTypes.*;


public class Intake extends TRCDirectionalSystem
{
    private TRCPneumaticSystem manipulator;
    private boolean dropped = true;
    private WPI_TalonSRX m_talon;

    public Intake(int[] motorPorts, SpeedControllerType[] motorTypes, int[] solenoidPorts)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        m_talon = (WPI_TalonSRX) this.outputMotors.get(motorPorts[0]);
        //this.manipulator = new TRCPneumaticSystem(solenoidPorts, true);
        m_talon.setInverted(true);
        this.raiseIntake();
    }

    public void dropIntake()
    {
        //manipulator.fullOpen();
        this.dropped = true;
    }

    public void raiseIntake()
    {
        //manipulator.fullClose();
        this.dropped = false;
    }

    public void pullIn()
    {
        //if (this.dropped)
        //{
            m_talon.set(ControlMode.PercentOutput, Constants.INTAKE_SPEED);
        //}
    }

    public void stop()
    {
        m_talon.set(ControlMode.PercentOutput, 0.0);
    }

    public void pushOut()
    {
        //if (this.dropped)
        //{
            m_talon.set(ControlMode.PercentOutput, -Constants.INTAKE_SPEED);
        //}
    }
}