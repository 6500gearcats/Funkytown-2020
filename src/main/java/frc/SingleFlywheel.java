package frc;

import org.usfirst.frc.team6500.trc.util.TRCTypes;
import edu.wpi.first.wpilibj.SpeedController;
import java.lang.Thread;

public class SingleFlywheel
{
	private SpeedController drive;
	private Thread thread;
	private boolean atSpeed;
	private double optimalSpeed;

	/**
	 *	Initialize a new SingleFlywheel class
	 *	@param port port number of the motor driving the device
	 *	@param controller the type of SpeedController
	 *	@param optimalSpeed the speed at which the motor should run at (-1.0 - 1.0)
	 */
	public SingleFlywheel(int port, TRCTypes.SpeedControllerType controller, double optimalSpeed)
	{
		drive = TRCTypes.controllerTypeToObject(port, controller);
		this.optimalSpeed = optimalSpeed;
		this.atSpeed = false;
	}

	public void spinUp()
	{
		
	}

	public void spinDown()
	{

	}

	/**
	 *	@return if flywheel is spinning at optimal speed
	 */
	public boolean atSpeed() { return this.atSpeed; }
}
