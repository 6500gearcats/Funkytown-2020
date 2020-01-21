package frc;

import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

public class SingleFlywheel
 {
    private TRCDirectionalSystem drive;
    private boolean atSpeed;
    private double optimalSpeed;

    public SingleFlywheel(int port, SpeedControllerType controller, double optimalSpeed)
    {
        drive = new TRCDirectionalSystem(port, controller, false, dFSpeed, dRSpeed);
        this.optimalSpeed = optimalSpeed;
        this.atSpeed = false;
    }

     /**
      * @return if flywheel is spinning at optimal speed
      */
     public boolean atSpeed() { return atSpeed; }
 }