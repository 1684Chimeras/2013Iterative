/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Kelly
 */
public class RobotMap 
{
    public static final boolean PracticeBot = false;
//SideCar 1
    //PWMs
    public static final int leftDriveMotors   = 1;
    public static final int rightDriveMotors  = 2;
    public static final int leftHookMotor     = 3;
    public static final int rightHookMotor    = 4;
    public static final int shooterWheelMotor = 5;
    public static final int flipperMotor      = 6;
    public static final int tiltMotor         = 7;
    public static final int Roller            = 8;
    public static final int shooterTrimServo  = 9;
    public static final int sparePWM1         = 10;
    
    //GPIO
    public static final int pressureSwitch            = 1;
    public static final int rightDriveEncoderA        = 2;
    public static final int rightDriveEncoderB        = 3;
    public static final int leftDriveEncoderA         = 4;
    public static final int leftDriveEncoderB         = 5;
    public static final int leftHookEncoderA          = 6;
    public static final int rightHookEncoderA         = 7;
    public static final int shooterSpeedEncoder       = 8;
    public static final int tiltLimitTop              = 9;
    public static final int tiltLimitBottom           = 10;    
    public static final int leftHookExtendLimit       = 11;    
    public static final int leftHookRetractLimit      = 12;    
    public static final int rightHookExtendLimit      = 13;    
    public static final int rightHookRetractLimit     = 14; 
    
    //Relays
    public static final int compressor     = 1;
    public static final int hookClamp      = 2;
    public static final int spareRelay1    = 3;
    public static final int hopperLED1     = 4;
    public static final int hopperLED2     = 5;
    public static final int hopperLED3     = 6;
    public static final int hopperLED4     = 7;
    public static final int spareRelay2    = 8;
    
//Analog Inputs
    public static final int tiltEncoder     = 1;
    public static final int gyro            = 2;
    public static final int flipperPosition = 3;
    public static final int spareAnalog2    = 4;
    public static final int spareAnalog3    = 5;
    public static final int spareAnalog4    = 6;
    public static final int spareAnalog5    = 7;
    public static final int battery         = 8;
}
