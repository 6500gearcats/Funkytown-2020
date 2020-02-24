package frc;


import frc.team6500.trc.util.TRCTypes.*;


// TODO: Finalize input/motor/pnematics ports for mechanisms
public class Constants
{
    // Input Constants
    public static final double INPUT_DRIVE_DEADBAND     = 0.1;
    /*
    public static final int INPUT_DRIVER_PORT           = 0;
    public static final int INPUT_GUNNER_PORT           = 1;
    public static final int INPUT_PORTS[]               = {INPUT_DRIVER_PORT, INPUT_GUNNER_PORT};
    public static final ControllerType INPUT_TYPES[]    = {ControllerType.Xbox360, ControllerType.Generic};

    public static final int INPUT_DRIVE_SLOW            = (INPUT_TYPES[INPUT_DRIVER_PORT] == ControllerType.Xbox360) ? 5 : 2;
    public static final Object INPUT_DRIVE_BUTTONS[]    = {INPUT_DRIVE_SLOW};

    public static final int INPUT_INTAKE_DROP           = 0;
    public static final int INPUT_INTAKE_RAISE          = 0;
    public static final int INPUT_INTAKE_PULLIN         = 0;
    public static final int INPUT_INTAKE_PUSHOUT        = 0;

    public static final int INPUT_LIFT_UP               = 0;
    public static final int INPUT_LIFT_BALANCE          = 0;
    */

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
    public final static int DRIVE_WHEEL_FRONTLEFT               = 3;
    public final static int DRIVE_WHEEL_REARLEFT                = 4;
	public final static int DRIVE_WHEEL_FRONTRIGHT              = 2;
    public final static int DRIVE_WHEEL_REARRIGHT               = 1;
    public final static int DRIVE_WHEEL_PORTS[]                 = {DRIVE_WHEEL_FRONTLEFT, DRIVE_WHEEL_REARLEFT, DRIVE_WHEEL_FRONTRIGHT, DRIVE_WHEEL_REARRIGHT};
    public final static SpeedControllerType DRIVE_WHEEL_TYPES[] = {SpeedControllerType.CANSparkMax, SpeedControllerType.CANSparkMax, SpeedControllerType.CANSparkMax, SpeedControllerType.CANSparkMax};
    public final static boolean DRIVE_WHEEL_INVERTS[]           = {true, true, true, true};

    public final static GyroType GYRO_TYPE = GyroType.NavX;

    public final static double ENCODER_DISTANCE_MULTIPLIER_METERS   = 0.508;
    public final static double ENCODER_DISTANCE_MULTIPLIER_RPM      = 1.0;
    public final static double ENCODER_DISTANCE_MULTIPLIER_FEET     = 2.0;
    public final static int ENCODER_LEFT_PORTS[]                    = {DRIVE_WHEEL_FRONTLEFT, 0, DRIVE_WHEEL_REARLEFT, 0};
    public final static int ENCODER_RIGHT_PORTS[]                   = {DRIVE_WHEEL_FRONTRIGHT, 0, DRIVE_WHEEL_REARRIGHT, 0};
    public final static EncoderType ENCODER_LEFT_TYPES[]            = {EncoderType.SparkMax, EncoderType.SparkMax};
    public final static EncoderType ENCODER_RIGHT_TYPES[]           = {EncoderType.SparkMax, EncoderType.SparkMax};
    public final static double ENCODER_LEFT_DISTANCE_MULTIPLIERS[]  = {ENCODER_DISTANCE_MULTIPLIER_METERS, ENCODER_DISTANCE_MULTIPLIER_METERS};
    public final static double ENCODER_RIGHT_DISTANCE_MULTIPLIERS[] = {ENCODER_DISTANCE_MULTIPLIER_METERS, ENCODER_DISTANCE_MULTIPLIER_METERS};


    // Mechanism Constants
    
    public final static int INTAKE_MOTOR_A = 9;
    public final static int INTAKE_MOTOR_PORTS[] = {INTAKE_MOTOR_A};
    public final static SpeedControllerType INTAKE_MOTOR_TYPES[] = {SpeedControllerType.CANTalonSRX};
    public final static int INTAKE_PNEUMATICS_A = 0;
    public final static int INTAKE_PNEUMATICS_PORTS[] = {INTAKE_PNEUMATICS_A};
    public final static double INTAKE_SPEED = 0.2;

    public final static int LIFT_MOTOR_A = 10;
    public final static int LIFT_MOTOR_B = 11;
    public final static int LIFT_MOTOR_PORTS[] = {LIFT_MOTOR_A, LIFT_MOTOR_B};
    public final static SpeedControllerType LIFT_MOTOR_TYPES[] = {SpeedControllerType.CANSparkMax, SpeedControllerType.CANSparkMax};
    public final static double LIFT_ANGLE_TOLERANCE = 5.0;
    public final static double LIFT_SPEED_UP = 0.5;
    public final static double LIFT_SPEED_DOWN = -0.5;
    public final static double LIFT_SPEED_BALANCE = 0.2;
    public final static double LIFT_MAX_HEIGHT = 4800.0;
    //public final static int WINCH_SERVO_PORT_A = 0;
    //public final static int WINCH_SERVO_ANGLE_BRAKE = 0;
    //public final static int WINCH_SERVO_ANGLE_RELEASE = 0;
    //public final static int LIFT_BRAKE_LEFT_PORT_A = 0;
    //public final static int LIFT_BRAKE_LEFT_PORT_B = 0;
    //public final static int LIFT_BRAKE_LEFT_PORTS[] = {LIFT_BRAKE_LEFT_PORT_A, LIFT_BRAKE_LEFT_PORT_B};
    //public final static int LIFT_BRAKE_RIGHT_PORT_A = 0;
    //public final static int LIFT_BRAKE_RIGHT_PORT_B = 0;
    //public final static int LIFT_BRAKE_RIGHT_PORTS[] = {LIFT_BRAKE_RIGHT_PORT_A, LIFT_BRAKE_RIGHT_PORT_B};

    public final static double SHOOTER_TOLERANCE_RPM = 0.0;
    public final static double SHOOTER_TOLERANCE_ANGLE = 0.0;
    public final static double SHOOTER_RPM_TARGET = 500.0;
    public final static double SHOOTER_RPM_IDLE = 0.0;
    public final static double SHOOTER_ANGLE_MULTIPLIER = 1.0;
    public final static double SHOOTER_ANGLE_INTERVAL = 5.0;
    public final static int SHOOTER_PORT_A = 20;
    public final static int SHOOTER_PORT_B = 21;
    public final static int SHOOTER_PORT_EJECTOR = 7;
    public final static int SHOOTER_PORT_ROTATOR = 5;
    public final static int SHOOTER_MOTOR_PORTS[] = {SHOOTER_PORT_A, SHOOTER_PORT_B, SHOOTER_PORT_EJECTOR, SHOOTER_PORT_ROTATOR};
    public final static SpeedControllerType SHOOTER_MOTOR_TYPES[] = {SpeedControllerType.CANSparkMax, SpeedControllerType.CANSparkMax, SpeedControllerType.CANTalonSRX, SpeedControllerType.CANTalonSRX};
    
    //public final static double CONVEYOR_RPM_TARGET = 30.0;
    //public final static double CONVEYOR_RPM_IDLE = 0.0;
    //public final static double CONVEYOR_RPM_MULTIPLIER = 1.0;
    public final static double CONVEYOR_UPPER_SPEED = 0.45;
    public final static double CONVEYOR_LOWER_SPEED = 0.55;
    public final static int CONVEYOR_DIO_ENTERED = 0;
    public final static int CONVEYOR_DIO_STORED = 1;
    public final static int CONVEYOR_DIO_FULL = 2;
    public final static int CONVEYOR_PORT_LOWER = 8;
    public final static int CONVEYOR_PORT_UPPER = 6;
    public final static int CONVEYOR_MOTOR_PORTS[] = {CONVEYOR_PORT_LOWER, CONVEYOR_PORT_UPPER};
    public final static SpeedControllerType CONVEYOR_MOTOR_TYPES[] = {SpeedControllerType.CANTalonSRX, SpeedControllerType.CANTalonSRX};

    // Pneumatics Constants
    public final static int PNEUMATICS_PCM_ID = 10;

    // PDP Constants
    public final static int PDP_CAN_ID = 0;

    // Auto Constants
    public final static double AUTO_KS            = 0.151;    // volts
    public final static double AUTO_KV            = 2.49;     // volt seconds per meter
    public final static double AUTO_KA            = 0.365;    // volt seconds squared per meter
    public final static double AUTO_WHEELDISTANCE = 0.73089;  // meters
    public final static double AUTO_MAX_SPEED     = 3.0;      // meters per second
    public final static double AUTO_MAX_ACCEL     = 3.0;      // meters per second squared
    public final static double AUTO_RAMSETE_B     = 2.0;      // meters
    public final static double AUTO_RAMSETE_ZETA  = 0.7;      // seconds
    public final static double AUTO_PID_P         = 2.59;
    public final static double AUTO_PID_I         = 0.0;
    public final static double AUTO_PID_D         = 0.0;
}