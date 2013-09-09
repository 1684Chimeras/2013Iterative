/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kelly
 */
public class Shooter 
{
    Talon shooterMotor;
    Talon tiltMotor;
    AnalogChannel tiltEncoder;
    Counter speedEncoder;
    DigitalInput deckTiltUp;
    DigitalInput deckTiltDown;
    DriveTrain driveTrain;
    Solenoid fire;
    Solenoid hopper;
    Timer timer;
    
    
    double kp_Tilt = 0.08;
    int currentTilt = 500;
    int maxValue = 511;
    int minValue = 595;
    int tiltSetPoint = 537;
    double tiltError = 0;
    double tiltCorrection = 0;
 
    double kp_Shooter = 0.1;
    double wheelPeriod = 0.0;
    double wheelSetPoint = 0.0;
    double actualRPM = 0.0;
    double wheelError = 0.0;
    double wheelCorrection = 0.0;
    
    int rapidFireState = 0;
    int rapidFireCounter = 0;
    
    int executed = 0;
    
    public Shooter(DriveTrain driveTrain, Pneumatics Pneumatics)
    {
        shooterMotor = new Talon(RobotMap.shooterWheelMotor);
        tiltMotor = new Talon(RobotMap.tiltMotor);
        tiltEncoder = new AnalogChannel(RobotMap.tiltEncoder);
        speedEncoder = new Counter(RobotMap.shooterSpeedEncoder);
        deckTiltUp = new DigitalInput(RobotMap.tiltLimitTop);
        deckTiltDown = new DigitalInput(RobotMap.tiltLimitBottom);
        fire = Pneumatics.fire;
        hopper = Pneumatics.hopperUp;
        this.driveTrain = driveTrain;
        
        timer = new Timer();
        
        speedEncoder.start();
        timer.start();
        speedEncoder.reset();
    }
    
    public void shooterOff()
    {
        shooterMotor.set(0.0);
    }
    
    public void wheelUpdate()
    {        
        if (speedEncoder.getPeriod() > 0.012){
            wheelPeriod = speedEncoder.getPeriod();
        }
        
        actualRPM = (1/wheelPeriod)*60;
        wheelError = wheelSetPoint - actualRPM;
        wheelCorrection = kp_Shooter * wheelError;
        
        if (wheelCorrection > 1.0){
            wheelCorrection = 1.0;
        }else{
            if (wheelCorrection < 0.0){
                if (wheelError < -500){
                    wheelCorrection = 0.0;
                }else{
                    if (wheelSetPoint != 0.0){
                        wheelCorrection = 0.5;
                    }
                }
            }
        }
        
        shooterMotor.set(wheelCorrection);
    }
    
    // this is the one that works !!!!! Should be in a 10ms interrupt but teleopPeriodic is OK for now
    public void tiltUpdate()
    {        
        currentTilt = tiltEncoder.getAverageValue();
        
        tiltError = currentTilt - tiltSetPoint;
        tiltCorrection = kp_Tilt * tiltError;
        
        if (tiltCorrection < 0.0){
            if (currentTilt <= minValue){
                tiltMotor.set(-tiltCorrection);
            }else{
                tiltMotor.set(0.0);
            }
        }else{
            if (currentTilt >= maxValue){
                tiltMotor.set(-tiltCorrection);
            }else{
                tiltMotor.set(0.0);
            }
        }
    }
    
    public void rapidFire()
    {
        switch (rapidFireState){
            case 0:
                hopper.set(true);
                fire.set(true);
                rapidFireState = 1;
                rapidFireCounter = 0;
                break;
            case 1:
                if (rapidFireCounter >= 10){
                    if ((actualRPM > 1000) && (Math.abs(wheelError) < 100)){
                        hopper.set(false);
                        fire.set(false);
                        System.out.println("Fire at   " + rapidFireCounter + "Speed:  " + actualRPM);
                        rapidFireCounter = 0;
                        rapidFireState = 2;
                    }
                }
                break;
            case 2: 
                if (rapidFireCounter >= 5){
                    executed++;
                    rapidFireState = 0;
                }
                break;
            default:
                break;
        }
        rapidFireCounter++;
    }
    
    public void fire()
    {
        fire.set(true);
    }
    
    public void reset()
    {
        fire.set(false);
    }
    
    public void setShooterSpeed(int RPM) 
    {
        wheelSetPoint = (int) RPM;
    }
    
    public void setTiltSetpoint(int setpoint)
    {
        tiltSetPoint = setpoint;
    }
    
    // this will increase or decrease the setpoint by operator override
    public void manualTilt (double tiltValue) 
    {
        if (tiltValue > 0.5) {
            tiltSetPoint--; 
        } else {
            tiltSetPoint++;             
        }
        
        if (tiltSetPoint < maxValue){
            tiltSetPoint = maxValue;
        }
        
        if (tiltSetPoint > minValue){
            tiltSetPoint = minValue;
        }
    }
}