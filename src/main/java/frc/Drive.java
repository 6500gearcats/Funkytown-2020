package frc;


import frc.team6500.trc.wrappers.systems.drives.TRCDifferentialDrive;
import frc.team6500.trc.util.TRCTypes.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.team6500.trc.util.TRCDriveParams;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCSpeed;

import edu.wpi.first.wpilibj.SpeedController;


public class Drive extends TRCDifferentialDrive
{
    TRCSpeed xS, yS, zS;
    boolean slow;

    public Drive(int[] motorPorts, SpeedControllerType[] motorTypes, boolean[] inversion, boolean swapxy)
    {
        super(motorPorts, motorTypes, inversion);

        this.xS = new TRCSpeed();
        this.yS = new TRCSpeed();
        this.zS = new TRCSpeed();

        this.slow = false;
        this.coast();
    }


    public void setSlowOn()  { this.slow = true;  }
    public void setSlowOff() { this.slow = false; }

    /**
     * Scale the input from an axis by first eliminating it if it is under the deadband,
     * otherwise square it because the squared values feel better for drivers.
     * 
     * @param input
     * @return Properly scaled result
     */
    double scaleInput(double input)
    {
        if (Math.abs(input) < Constants.INPUT_DRIVE_DEADBAND) { input = 0.0; }
        if (input < 0.0) { return -(input * input); }
        else { return input * input; }
    }

    /**
     * Do any extra operations required to transform the original
     * controller inputs into real values the drivetrain can use.
     * 
     * @param dps Original inputs
     * @return TRCDriveParams that should be actually used to drive the robot
     */
    TRCDriveParams filter(TRCDriveParams dps)
    {
        dps.setRawX(this.xS.calculateSpeed(-scaleInput(dps.getRawX())));
        dps.setRawY(this.yS.calculateSpeed(-scaleInput(dps.getRawY())));
        dps.setRawZ(this.zS.calculateSpeed(-scaleInput(dps.getRawZ())));

        if (this.slow)
        {
            dps.setM(dps.getM() / 2);
        }

        return dps;
    }

    /**
     * Drive the robot + filtering inputs
     * 
     * @param dps The original inputs
     */
    @Override
    public void tankDrive(TRCDriveParams dps)
    {
        this.drive.feed();
        super.tankDrive(filter(dps));
    }

    public void setLeftVolts(double volts)
    {
        this.leftMotor.setVoltage(volts);
    }

    public void setRightVolts(double volts)
    {
        this.rightMotor.setVoltage(volts);
    }

    public void brake()
    {
        for (SpeedController sm : this.outputMotors)
        {
            ((CANSparkMax) sm).setIdleMode(IdleMode.kBrake);
        }
    }

    public void coast()
    {
        for (SpeedController sm : this.outputMotors)
        {
            ((CANSparkMax) sm).setIdleMode(IdleMode.kCoast);
        }
    }

    public void autoDrive()
    {
        drive.tankDrive(TRCNetworkData.getDataPoint("AIcmdL"), TRCNetworkData.getDataPoint("AIcmdR"), false);
    }

    public void manualDrive()
    {
        if (!AutoManager.getAutoing())
        {
            TRCDriveParams dps = TRCInputManager.getDPS("drive");
            dps = filter(dps);
            arcadeDrive(dps);
        }
    }
}