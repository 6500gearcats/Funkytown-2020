package frc;
import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

public class doubleflywheel extends TRCDirectionalSystem {
    private boolean isShooting;
    private int num_balls_loaded;

    public doubleflywheel(int[] motorPorts, SpeedControllerType[] motorTypes, boolean balanceInvert, double dFSpeed, double dRSpeed) {
        super(motorPorts, motorTypes, balanceInvert, dFSpeed, dRSpeed);
        // TODO Auto-generated constructor stub
    }

    /**
     * The shooter shoots all the balls out at once
     */
    public void shoot()
    {
        this.isShooting = true;

    }

    /**
     * As each ball shoots out, the speed of the wheel slows, so this takes the rpm of the neo motor before shooting
     * We delay the sparkmax before shooting and it will automatically bring the motor back up to speed
     */
    public void adjust_speed()
    {

    }

    
    
}