/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author Kelly
 */
public class DriveTrain 
{
    Talon rightDrive;
    Talon leftDrive;
    Encoder leftEncoder;
    Encoder rightEncoder;
    Gyro gyro;
    ADXL345_I2C accel;
    Pneumatics Pneumatics;
    
    int drivePIDMode = 0;
    boolean lockDown;
    boolean gyroRotate;
    boolean moveRate;
    
    int currentLeftPosition;
    int currentRightPosition;
    double leftDriveError;
    double rightDriveError;
    double leftDriveCorrection;
    double rightDriveCorrection;
    
    double gyroCurrent;
    double gyroError;
    double gyroCorrection;
    double gyroSetPoint;
    double leftSetpoint_Rate;
    double rightSetpoint_Rate;
    double maxDriveRate = 1.0;
    double kp_gyro = 0.01;
    
    boolean accelerating;
    boolean decelerating;
    boolean rampDirection_Pos;
    
    double accelCounter;
    double decelCounter;
    
    double leftCurrent_Rate;
    double rightCurrent_Rate;
    
    double leftError_Rate;
    double rightError_Rate;
    
    double leftSetPoint_Rate;
    double rightSetPoint_Rate;
    
    double leftCorrection_Rate;
    double rightCorrection_Rate;
    
    double kp_Drive_Rate = 0.001;
    
    double leftError_rate = 0;
    double rightError_rate = 0;
    
    double leftSetPoint_rate = 0;
    double rightSetPoint_rate = 0;   
    
    double leftCorrection = 0;
    double rightCorrection = 0;
    
    double kp_rate = 0.01;
    
    public DriveTrain(Pneumatics P)
    {
        leftDrive = new Talon(RobotMap.leftDriveMotors);
        rightDrive = new Talon(RobotMap.rightDriveMotors);
        
        leftEncoder = new Encoder(RobotMap.leftDriveEncoderA, RobotMap.leftDriveEncoderB);
        rightEncoder = new Encoder(RobotMap.rightDriveEncoderB, RobotMap.rightDriveEncoderA);
        
        gyro = new Gyro(RobotMap.gyro);
        
        accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
                
        Pneumatics = P;
        
        leftEncoder.setDistancePerPulse(.02453); //inches
        rightEncoder.setDistancePerPulse(.02453); //inches

        leftEncoder.start();
        rightEncoder.start();
        
        leftEncoder.reset();
        rightEncoder.reset();
        
        gyro.reset();
        gyro.setSensitivity(1.1);
    }
    
    public void manyModePID()
    {
        drivePIDMode = ((lockDown?1:0)*4) + ((gyroRotate?1:0)*2) + ((moveRate?1:0)*1);
        
        switch(drivePIDMode){
            //move set distance at set rate
            case 5:
                break;
            //LOCKDOWN
            case 4:
                 currentLeftPosition = leftEncoder.get();
                 currentRightPosition = rightEncoder.get();
                 
                 leftDriveError = currentLeftPosition - 0;
                 rightDriveError = currentRightPosition - 0;
                 
                 leftDriveCorrection = 0.001 * leftDriveError;
                 leftDriveCorrection = 0.001 * leftDriveError;
                 
                 leftDrive.set(leftDriveCorrection);
                 rightDrive.set(-rightDriveCorrection);
                break;
            case 3:
                
                break;
            case 2:
                gyroCurrent = gyro.getAngle();
                gyroError = gyroCurrent - gyroSetPoint;
                gyroCorrection = 0.05 * gyroError;
                
                leftSetpoint_Rate = maxDriveRate * gyroCorrection;
                rightSetpoint_Rate = -(maxDriveRate * gyroCorrection);
                break;
            case 1:
                // single sided PID with ramp up/down
                // make setters that Accel, Coast, Decel
                // increase effect of kP on correction during accel
                // 100% kP during coast
                // increase effect of kP on reverse direction during decel
                // 1% --> 100% in 1 second
                if (accelerating && (accelCounter < 1.0)){
                    accelCounter += 0.01;
                }
                if (decelerating && (decelCounter < 1.0)){
                    decelCounter += 0.01;
                }
                
                leftCurrent_Rate = leftEncoder.getRate();
                rightCurrent_Rate = rightEncoder.getRate();
                
                leftError_Rate = leftCurrent_Rate - leftSetPoint_Rate;
                rightError_Rate = rightCurrent_Rate - rightSetPoint_Rate;
                
                //test again for Accel/Decel
                //no reverse
                if (accelerating){
                    leftCorrection_Rate = kp_Drive_Rate * leftError_Rate * accelCounter;
                    rightCorrection_Rate = kp_Drive_Rate * rightError_Rate * accelCounter;
                }else{
                    if (decelerating){
                        leftCorrection_Rate = kp_Drive_Rate * leftError_Rate * decelCounter;
                        rightCorrection_Rate = kp_Drive_Rate * rightError_Rate * decelCounter; 
                    }else{
                        leftCorrection_Rate = kp_Drive_Rate * leftError_Rate;
                        rightCorrection_Rate = kp_Drive_Rate * rightError_Rate;
                    }
                }
                
                if (rampDirection_Pos){
                    if (leftCorrection_Rate > 0){
                        leftDrive.set(leftCorrection_Rate);
                        rightDrive.set(-rightCorrection_Rate);
                    }
                }else{
                    if (leftCorrection_Rate < 0){
                        leftDrive.set(leftCorrection_Rate);
                        rightDrive.set(-rightCorrection_Rate);                        
                    }
                }
                break;
            default:
                break;
        }
    }

    public void arcadeDrive(double moveValue, double rotateValue, boolean hello)
    {
         // local variables to hold the computed PWM values for the motors        
        double leftMotorSpeed;
        double rightMotorSpeed;
        double movevalue;
        double rotatevalue;

        if (moveValue > 1.0){
            moveValue = 1.0;
        }
        
        if (moveValue < -1.0){
            moveValue = -1.0;
        }
        
        if (rotateValue > 1.0){
            rotateValue = 1.0;
        }
        
        if (rotateValue < -1.0){
            rotateValue = -1.0;
        }

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
 
        leftDrive.set(leftMotorSpeed);
        rightDrive.set(-rightMotorSpeed);
    }
    
    public void curve(double angle)
    {
        double leftArc;
        double rightArc;
        double leftWheelOffset = 36;
        double rightWheelOffset = 7;
        double leftChordLength;
        double rightChordLength;
        double leftRadius;
        double rightRadius;
        double totalAngle;
        double leftDistanceError;
        double rightDistanceError;
        double leftCurrentPosition = leftEncoder.getDistance();
        double rightCurrentPosition = rightEncoder.getDistance();
                
        leftChordLength = 60 + leftWheelOffset; //96
        rightChordLength = 60 + rightWheelOffset; //67
        
//        leftRadius = (((leftChordLength*leftChordLength)/(8*leftWheelOffset)) + (leftWheelOffset/2)); //50
//        rightRadius = (((rightChordLength*rightChordLength)/(8*rightWheelOffset)) + (rightWheelOffset/2)); //83

        totalAngle = 180 - (90 - angle);

        leftRadius = Math.sqrt(   MathUtils.pow(  ((leftChordLength/2)*(Math.sin(  90-(totalAngle/2)  ))), 2  ) + MathUtils.pow(  (leftChordLength/2), 2  )  );//49
        rightRadius = Math.sqrt(   MathUtils.pow(  ((rightChordLength/2)*(Math.sin(  90-(totalAngle/2)  ))), 2  ) + MathUtils.pow(  (rightChordLength/2), 2  )  );//34
                
        leftArc = (((((2*leftRadius*Math.PI)*(totalAngle))/360)));//131.6
        rightArc = (((((2*rightRadius*Math.PI)*(totalAngle))/360)));//91.8
//        leftArc = 230;
//        rightArc = 80;
        
        leftDistanceError = leftArc - leftCurrentPosition;
        rightDistanceError = rightArc - rightCurrentPosition;
        
        System.out.println(" lefterror   " + leftDistanceError);
        System.out.println(" righterror   " + rightDistanceError);

        System.out.println(" leftArc   " + leftArc);
        System.out.println(" rightArc   " + rightArc);
        System.out.println(" left encoder value   " + leftCurrentPosition);
        System.out.println(" right encoder value   " + rightCurrentPosition);
                
        if (leftDistanceError > (leftArc/4)){
            leftDrive.set(0.75);
        }
        if (leftDistanceError > (leftArc/2)){
            leftDrive.set(1.0);
        }
        if (leftDistanceError > (3*(leftArc/4))){
            leftDrive.set(0.75);
        }
        if (leftDistanceError <= 0.0){
            leftDrive.set(0.0);
        }
        
        if (rightDistanceError > (rightArc/4)){
            rightDrive.set(-0.1);
        }
        if (rightDistanceError > (rightArc/2)){
            rightDrive.set(-0.2);
        }
        if (rightDistanceError > (3*(rightArc/4))){
            rightDrive.set(-0.1);
        }
        if (rightDistanceError <= 0.0){
            rightDrive.set(0.0);
        }
    }
    
    public void autonRatePID()
    {
        double leftRate = leftEncoder.getRate();
        double rightRate = rightEncoder.getRate();
        
        leftError_rate = leftSetPoint_rate - leftRate;
        rightError_rate = rightSetPoint_rate - rightRate;
        
        leftCorrection = leftError_rate * kp_rate;
        rightCorrection = rightError_rate * kp_rate;
        
        leftDrive.set(-leftCorrection);
        rightDrive.set(rightCorrection);
    }
    
    public void feederSpin()
    {
        gyroCurrent = gyro.getAngle();
        gyroError = gyroCurrent - gyroSetPoint;
        gyroCorrection = 0.05 * gyroError;

        leftSetpoint_Rate = maxDriveRate * gyroCorrection;
        rightSetpoint_Rate = -(maxDriveRate * gyroCorrection);
        
        shiftLow();

//        gyroCurrent = gyro.getAngle();
//        gyroError = 150 - gyroCurrent;
//        
//        if ((gyroError <= 150) && (gyroError > 100)){
//            leftDrive.set(0.5);
//            rightDrive.set(0.5);
//        }
//        
//        if ((gyroError <= 100) && (gyroError > 50)){
//            leftDrive.set(0.6);
//            rightDrive.set(0.6);
//        } 
//        
//        if ((gyroError <= 50) && (gyroError > 10)){
//            leftDrive.set(0.5);
//            rightDrive.set(0.5);
//        }
//        
//        if ((gyroError <= 10) && (gyroError > 0)){
//            leftDrive.set(0.0);
//            rightDrive.set(0.0);
//        }
       
    }
    
    public void shiftLow()
    {
            Pneumatics.driveLow.set(false);
    }
    
    public void shiftHigh()
    {
            Pneumatics.driveLow.set(true);
    }
}
