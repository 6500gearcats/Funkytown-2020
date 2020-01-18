package frc;
import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class doubleflywheel extends TRCDirectionalSystem {
    private boolean isShooting;
    private int num_balls_loaded;
    private int target_velocity;
    private double curren_velocity; 
    private CANSparkMax m_motor;
    private CANEncoder m_encoder;

    // Not sure what this is used for saw it in documentation
    private static final int deviceID = 1;


    public doubleflywheel(int[] motorPorts, SpeedControllerType[] motorTypes, boolean balanceInvert, double dFSpeed, double dRSpeed) {
        super(motorPorts, motorTypes, balanceInvert, dFSpeed, dRSpeed);
        // TODO Auto-generated constructor stub

        this.m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);
        this.m_motor.restoreFactoryDefaults();
        this.m_encoder = m_motor.getEncoder();
    }

    /**
     * The shooter shoots all the balls out at once
     */
    public void shoot()
    {
        while(true)
        {
            adjust_speed();
            if(isShooting)
            {
                // run conveyer
            }else{
                this.m_motor.setVoltage(this.target_velocity);
                // Tick clock to set velocity
            }
            if(this.num_balls_loaded == 0)
            {
                break;
            }
        }
        
        // Find a way to keep the spark max to increase vultage input into the motor
    }

    

    /**
     * As each ball shoots out, the speed of the wheel slows, so this takes the rpm of the neo motor before shooting
     * We delay the sparkmax before shooting and it will automatically bring the motor back up to speed
     * The spark max(Controls voltage input into motor) takes input from the encoder(measures the rotations in the motor) 
     *      - we program the spak max to tell it when to send in vultage based on the encoder info
     */
    public boolean adjust_speed()
    {
        if(this.readEncoderVelocity() != curren_velocity)
        {
            return false;
        }else{
            return true;
        }

    }

    /**
     * Literaly finds the encoder rmp output
     * @return rmp output(double)
     */
    public double readEncoderVelocity()
    {
        return this.m_encoder.getVelocity();
    }
    
}