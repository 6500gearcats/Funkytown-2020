package frc;


import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.systems.TRCPneumaticSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.*;


public class Intake extends TRCDirectionalSystem
{
    private TRCPneumaticSystem manipulator;
    private boolean dropped = false;

    public Intake(int[] motorPorts, SpeedControllerType[] motorTypes, int[] solenoidPorts)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        this.manipulator = new TRCPneumaticSystem(solenoidPorts, true);
        this.raiseIntake();
    }

    public void dropIntake()
    {
        manipulator.fullOpen();
        this.dropped = true;
    }

    public void raiseIntake()
    {
        manipulator.fullClose();
        this.dropped = false;
    }

    public void pullIn()
    {
        if (this.dropped)
        {
            this.driveForward();
        }
    }

    public void pushOut()
    {
        if (this.dropped)
        {
            this.driveReverse();
        }
    }
}