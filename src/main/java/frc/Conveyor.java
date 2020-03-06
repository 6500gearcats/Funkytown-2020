package frc;

import java.time.Duration;
import java.time.Instant;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team6500.trc.systems.TRCDirectionalSystem;
import frc.team6500.trc.util.TRCInputManager;
import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.SpeedControllerType;

public class Conveyor extends TRCDirectionalSystem
{
    private static WPI_TalonSRX m_talons[];
    private static WPI_TalonSRX m_upper;
    private static WPI_TalonSRX m_lower;
    private static DigitalInput m_entered, m_stored, m_full;
    private static AnalogInput m_stored_2;
    private static boolean m_shooting, m_entered_prev;
    private static Instant m_entered_activated;

    public Conveyor(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        super(motorPorts, motorTypes, false, 1.0, -1.0);
        m_talons = new WPI_TalonSRX[this.outputMotors.size()];
        for (int i = 0; i < this.outputMotors.size(); i++)
        {
            m_talons[i] = (WPI_TalonSRX) this.outputMotors.get(motorPorts[i]);
        }
        m_upper = m_talons[1];
        m_lower = m_talons[0];
        m_upper.setInverted(false);
        m_lower.setInverted(false);
        m_entered = new DigitalInput(Constants.CONVEYOR_DIO_ENTERED);
        m_stored = new DigitalInput(Constants.CONVEYOR_DIO_STORED);
        m_full = new DigitalInput(Constants.CONVEYOR_DIO_FULL);
        m_stored_2 = new AnalogInput(Constants.CONVEYOR_AIO_STORED);
        m_shooting = false;
    }

    public void setShooting(boolean shooting)
    {
        m_shooting = shooting;
    }

    public static double calculateUltrasonicDistance(double voltage) // See https://www.maxbotix.com/ultrasonic-sensor-hrlv%E2%80%91maxsonar%E2%80%91ez-guide-158
    {
        double Vcc = 5.0;          // Input Voltage
        double Vm = voltage;       // Measured Voltage

        double Vi = Vcc / 1024;    // Volts per 5mm
        double Ri = 5 * (Vm / Vi); // Range in mm

        return Ri;
    }

    @Override
    public void driveForward()
    {
        if (m_shooting)
        {
            if (Math.abs(Robot.shooter.getRPM() - Constants.SHOOTER_RPM_TARGET) < Constants.SHOOTER_TOLERANCE_RPM)
            {
                m_upper.set(Constants.CONVEYOR_UPPER_SPEED);
                m_lower.set(Constants.CONVEYOR_LOWER_SPEED);
            }
        }
        else
        {
            m_upper.set(Constants.CONVEYOR_UPPER_SPEED);
            m_lower.set(Constants.CONVEYOR_LOWER_SPEED);
        }
    }

    @Override
    public void driveReverse()
    {
        m_upper.set(-Constants.CONVEYOR_UPPER_SPEED);
        m_lower.set(-Constants.CONVEYOR_LOWER_SPEED);
    }

    public void update()
    {
        
        if (m_entered_activated == null && !m_entered.get())
        {
            m_entered_activated = Instant.now();

            this.driveForward();
        }
        else if (m_entered_prev == m_entered.get() && !m_entered.get())
        {
            Instant now = Instant.now();
            long millis = Duration.between(m_entered_activated, now).toMillis();
            if (millis >= 400)
            {
                Robot.intake.disable();
                this.driveReverse();
            }
            else
            {
                this.driveForward();
            }
        }
        else if (m_entered.get())
        {
            Robot.intake.enable();
            this.fullStop();
            m_entered_activated = null;
        }

        m_entered_prev = m_entered.get();

        TRCNetworkData.updateDataPoint("Ball Entered", m_entered.get());
        TRCNetworkData.updateDataPoint("Ball Stored", m_stored.get());
        TRCNetworkData.updateDataPoint("Conveyor Full", m_full.get());
        //TRCNetworkData.updateDataPoint("Ultrasonic", calculateUltrasonicDistance(m_stored_2.getVoltage()));
    }
}