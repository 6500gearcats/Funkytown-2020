package frc;

import frc.team6500.trc.util.TRCTypes.*;
import edu.wpi.first.wpilibj.DigitalInput;

// TODO: Finalize input/motor/pnematics ports for mechanisms
public class Constants
{
	// Input Constants
	public static final int INPUT_DRIVER_PORT           = 0;
	public static final int INPUT_GUNNER_PORT           = 1;
	public static final int INPUT_PORTS[]               = {INPUT_DRIVER_PORT, INPUT_GUNNER_PORT};
	public static final ControllerType INPUT_TYPES[]    = {ControllerType.Xbox360, ControllerType.Generic};

	public static final double INPUT_DRIVE_DEADBAND     = 0.1;
	public static final int INPUT_DRIVE_SLOW            = (INPUT_TYPES[INPUT_DRIVER_PORT] == ControllerType.Xbox360) ? 5 : 2;
	public static final Object INPUT_DRIVE_BUTTONS[]    = {INPUT_DRIVE_SLOW};

	public static final int INPUT_INTAKE_DROP           = 0;
	public static final int INPUT_INTAKE_RAISE          = 0;
	public static final int INPUT_INTAKE_PULLIN         = 0;
	public static final int INPUT_INTAKE_PUSHOUT        = 0;

	public static final int INPUT_WINCH_BRAKE           = 0;
	public static final int INPUT_WINCH_RELEASE         = 0;

	public static final String TABLE_KEY_FORWARDBACK	= "AIcmdFB";
	public static final String TABLE_KEY_ROTATE			= "AIcmdRT";
	public static final String TABLE_KEY_LEFTPOWER		= "AIcmdL";
	public static final String TABLE_KEY_RIGHTPOWER		= "AIcmdR";

	/*
	public static final int INPUT_DRIVE_TURN_LEFT_SLOW  = 1;
	public static final int INPUT_DRIVE_TURN_LEFT_FAST  = 5;
	public static final int INPUT_DRIVE_TURN_RIGHT_SLOW  = 3;
	public static final int INPUT_DRIVE_TURN_RIGHT_FAST  = 7;

	public static final int INPUT_DRIVE_FORWARD_SLOW  = 2;
	public static final int INPUT_DRIVE_FORWARD_FAST  = 6;
	public static final int INPUT_DRIVE_BACKWARD_SLOW  = 4;
	public static final int INPUT_DRIVE_BACKWARD_FAST  = 8;

	public static final int INPUT_DRIVE_COIN = 9;
	public static final int INPUt_DRIVE_PLAYER = 10;

	public static final Object INPUT_DRIVE_BUTTONS_MANUAL[] = {INPUT_DRIVE_TURN_LEFT_SLOW, INPUT_DRIVE_TURN_LEFT_FAST, INPUT_DRIVE_TURN_RIGHT_SLOW, INPUT_DRIVE_TURN_RIGHT_FAST, INPUT_DRIVE_FORWARD_SLOW, INPUT_DRIVE_FORWARD_FAST, INPUT_DRIVE_BACKWARD_SLOW, INPUT_DRIVE_BACKWARD_FAST};
	*/

	// Speed Constants
	public static final double SPEED_BASE              = 0.85;
	public static final double SPEED_BOOST             = 0.95;

	// Drive Constants
	public final static int DRIVE_MOTOR_FRONTLEFT               = 2;
	public final static int DRIVE_MOTOR_FRONTRIGHT              = 3;
	public final static int DRIVE_MOTOR_REARLEFT                = 1;
	public final static int DRIVE_MOTOR_REARRIGHT               = 4;
	public final static boolean DRIVE_MOTOR_FRONTLEFT_INVERTED	= true;
	public final static boolean DRIVE_MOTOR_REARLEFT_INVERTED	= true;
	public final static boolean DRIVE_MOTOR_FRONTRIGHT_INVERTED	= true;
	public final static boolean DRIVE_MOTOR_REARRIGHT_INVERTED	= true;

	// Mechanism Constants
	public final static int INTAKE_MOTOR_A = 0;
	public final static int INTAKE_MOTOR_PORTS[] = {INTAKE_MOTOR_A};
	public final static SpeedControllerType INTAKE_MOTOR_TYPES[] = {SpeedControllerType.CANSparkMax};
	public final static int INTAKE_PNEUMATICS_A = 0;
	public final static int INTAKE_PNEUMATICS_PORTS[] = {INTAKE_PNEUMATICS_A};

	public final static int WINCH_MOTOR_A = 0;
	public final static int WINCH_MOTOR_PORTS[] = {WINCH_MOTOR_A};
	public final static SpeedControllerType WINCH_MOTOR_TYPES[] = {SpeedControllerType.CANSparkMax};
	public final static int WINCH_SERVO_PORT_A = 0;
	public final static int WINCH_SERVO_ANGLE_BRAKE = 0;
	public final static int WINCH_SERVO_ANGLE_RELEASE = 0;

	// Pneumatics Constants
	public final static int PNEUMATICS_PCM_ID = 10;


	// PDP Constants
	public final static int PDP_CAN_ID = 0;

	public final static DigitalInput DEAD_INPUT = new DigitalInput(0);
}