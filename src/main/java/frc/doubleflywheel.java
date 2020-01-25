package frc;

import com.revrobotics.*;

/**
 *	Control a single flywheel mechanism
 */
public class DoubleFlywheel
{
    private CANSparkMax drive1;
    private CANSparkMax drive2;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel
	 *	@param port port number of the SparkMax driving the Neo
	 *	@param optimalSpeed the speed at which the motor should run in rotations per second
	 */
	public DoubleFlywheel(int port1, int port2, double optimalSpeed)
	{
        drive1 = new CANSparkMax(port1, CANSparkMaxLowLevel.MotorType.kBrushless);
        drive2 = new CANSparkMax(port2, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.optimalSpeed = optimalSpeed;
		/* // code here to tune PID controller
		CANPIDController pidc = drive.getPIDController();
		pidc.setP(0.2);
		pidc.setI(0.2);
		pidc.setD(0.5);
		*/
	}

	/**
	 *	Start and spin flywheel up to optimal speed
	 */
	public void spinUp()
	{
        drive1.getPIDController().setReference(optimalSpeed, ControlType.kVelocity);
        drive2.getPIDController().setReference(optimalSpeed, ControlType.kVelocity);
	}

	/**
	 *	Stop motor power and allow flywheel to spin down
	 */
	public void spinDown()
	{
        drive1.stopMotor();
        drive2.stopMotor();
	}

	/**
	 *	@return if flywheel is spinning at optimal speed
	 */
	public boolean atSpeed()
	{
        if(drive1.getPIDController().getIAccum() == this.optimalSpeed && drive2.getPIDController().getIAccum() == this.optimalSpeed){
            return true;
        }else{
            return false;
        }
    }
    
    public void runConveyer()
    {
        
    }
}