package frc;


import edu.wpi.first.wpilibj.Servo;

import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.*;


public class Winch extends TRCDirectionalSystem
{
    private Servo brake;
    private boolean brakeActive;

    public Winch(int[] motorPorts, SpeedControllerType[] motorTypes, int servoPort)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        this.brake = new Servo(servoPort);
        this.brakeActive = false;
    }

    public void brake()
    {
        this.brake.setAngle(Constants.WINCH_SERVO_ANGLE_BRAKE);
        this.brakeActive = true;
    }

    public void release()
    {
        this.brake.setAngle(Constants.WINCH_SERVO_ANGLE_RELEASE);
        this.brakeActive = false;
    }
}