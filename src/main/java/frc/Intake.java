package frc;


import java.time.Duration;
import java.time.Instant;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.systems.TRCPneumaticSystem;
import frc.team6500.trc.util.TRCTypes.*;


public class Intake extends TRCDirectionalSystem
{
    private TRCPneumaticSystem manipulator;
    private boolean dropped = true;
    private Instant toggle_instant;
    private WPI_TalonSRX m_talon;
    private double speed = 0.0;
    private boolean enabledd;

    public Intake(int[] motorPorts, SpeedControllerType[] motorTypes, int[] solenoidPorts)
    {
        super(motorPorts, motorTypes, false, Constants.INTAKE_SPEED_SLOW, -Constants.INTAKE_SPEED_FAST);
        m_talon = (WPI_TalonSRX) this.outputMotors.get(motorPorts[0]);
        manipulator = new TRCPneumaticSystem(solenoidPorts, true);
        m_talon.setInverted(true);
        raiseIntake();
        toggle_instant = Instant.now();
        //this.dropped = false;
        //dropIntake();
        slow();
        enable();
    }

    public void slow()
    {
        speed = Constants.INTAKE_SPEED_SLOW;
    }

    public void fast()
    {
        speed = Constants.INTAKE_SPEED_FAST;
    }

    public void enable()
    {
        enabledd = true;
    }

    public void disable()
    {
        enabledd = false;
        super.valueDrive(-0.25);
    }

    public void dropIntake()
    {
        manipulator.fullOpen();
        this.dropped = true;
    }

    public void raiseIntake()
    {
        manipulator.fullClose();
        //this.dropped = false;
    }

    public void toggle()
    {
        Instant now = Instant.now();
        if (Duration.between(toggle_instant, now).toMillis() > 750)
        {
            manipulator.toggle();
            this.dropped = !this.dropped;
            toggle_instant = now;
        }
    }

    @Override
    public void driveForward()
    {
        if (enabledd)
        {
            super.valueDrive(speed);
        }
    }

    @Override
    public void fullStop()
    {
        m_talon.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void driveReverse()
    {
        if (enabledd)
        {
            super.valueDrive(-speed);
        }
    }
}