/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Kelly
 */
public class Teleoperated 
{
    NetworkTable networkTable = null;
    Arm arm;
    DiskHandling diskHandling;
    DriveTrain driveTrain;
    Shooter shooter;
    XboxControllers controllers;
    MacroBox MB;
    Pneumatics pneumatics;
    Timer timer;
    
    boolean climbMode = false;
    boolean increaseRollover = false;
    boolean decreaseRollover = false;
    int RPM = 0;
    
    double operatorTiltValue = 0.0;
    int macroTiltValue = 0;
    int macroSpeedValue = 0;
    
    boolean autoFire = false;
    int autoFireState = 0;
    int autoFireCount = 0;
    
    public Teleoperated(Arm Arm, DiskHandling DiskHandling, DriveTrain drive, Shooter Shooter, XboxControllers xboxControllers, Pneumatics Pneumatics, MacroBox mb)
    {
        arm = Arm;
        diskHandling = DiskHandling;
        driveTrain = drive;
        shooter = Shooter;
        controllers = xboxControllers;
        MB = mb;
        pneumatics = Pneumatics;    
        timer = new Timer();
        timer.start();
    }
    
    public void update()
    {
        pneumatics.compress();
        
        if (controllers.getOperatorHorizontalDPadLeft() == true){
            climbMode = true;
        }
        
        if (controllers.getOperatorHorizontalDPadRight() == true){
            climbMode = false;
        }

        //DRIVING
            //switched because opposite
        driveTrain.arcadeDrive(controllers.driver_LeftJoystick_Rotate(), controllers.driver_LeftJoystick_Move(), true);

        //SHIFTING Things
        if (controllers.getDriver_LeftTrigger() == true){
            //LOW GEAR
            driveTrain.shiftLow();
            driveTrain.arcadeDrive(controllers.driver_LeftJoystick_Rotate(), controllers.driver_LeftJoystick_Move(), true);
        }
        
        if (controllers.getDriver_LeftTrigger() == false){
            //HIGH GEAR
            driveTrain.shiftHigh();
            driveTrain.arcadeDrive(controllers.driver_LeftJoystick_Rotate(), controllers.driver_LeftJoystick_Move(), true);
        }

        //ARM UP DOWN LOAD
        if ((controllers.driver_ButtonY() == true)){
            arm.armUp();
        }

        if ((controllers.driver_ButtonA() == true)){
            arm.armLoadIn();
            arm.armDown();
        }

        if (controllers.driver_RightBumper() == true){
            arm.armLoadOut();
        }

        if (controllers.driver_LeftBumper() == true){
            arm.armLoadIn();
        }

        if ((controllers.driver_ButtonB() == true)){
            arm.armLoadPosition();
        }

        if ((!controllers.getDriver_RightTrigger()) && (controllers.driver_ButtonX())){
            arm.rollerOut();
        }

        if (((controllers.getDriver_RightTrigger()) && (!controllers.driver_ButtonX()))) {
            arm.rollerIn();
        }
        
        if ((!controllers.getDriver_RightTrigger()) && (!controllers.driver_ButtonX())) {
            arm.rollerOff();
        }
        
        //TILT MACRO
          if (controllers.operator_ButtonA()){
              shooter.tiltSetPoint = 544;
              shooter.wheelSetPoint = 3900;
          }    

        //TILT
        operatorTiltValue = controllers.operator_RightJoystick_Move();
        
        if (Math.abs(operatorTiltValue) > 0.5) {
            shooter.manualTilt(operatorTiltValue);
        }

        // Shooter speed control - try to use macro first. manual override follows
        macroSpeedValue = MB.getShooterSetpoint();
        
        if (macroSpeedValue > 1000) {
            shooter.setShooterSpeed(macroSpeedValue);
        } else {
        //SHOOTER INCREASE SPEED
            if (controllers.operator_ButtonY() == true){
                shooter.wheelSetPoint = 5000;
             }

            //SHOOTER DECREASE SPEED
            if (controllers.operator_ButtonX() == true){
                  shooter.shooterOff();
                  shooter.wheelSetPoint = 0;
            }
        }

        if (controllers.operator_LeftBumper()){
            shooter.wheelSetPoint = 3900;
            shooter.tiltSetPoint = 589;
        }

        if (controllers.operator_ButtonB()){
            if (!autoFire) {
                shooter.rapidFireState = 0;
                autoFire = true;
                shooter.rapidFireCounter = 0;
            }
        }else{
            autoFire = false;                
            shooter.rapidFireState = 0;
            shooter.rapidFireCounter = 0;

        }

        if (controllers.operator_RightBumper()== true){
            shooter.tiltSetPoint = 578;
            shooter.wheelSetPoint = 4600;
        }

        //FIRE DISC
        if (!autoFire) { 
            // manual firing
            if (controllers.getOperator_RightTrigger() == true){
                shooter.fire();
                diskHandling.hopperUp();
                System.out.println("Shoot at "+ shooter.actualRPM + "        ");
            }else{                    
                //FIRE ONLY!!!!!!!
                if (controllers.getOperator_LeftTrigger() == true){
                    diskHandling.hopperUp();
                }else{
                    diskHandling.hopperDown();
                }
                shooter.reset();
                diskHandling.hopperDown();
            }                
        } else {        
            // auto firing
            pneumatics.compressor.set(Value.kOff);
            shooter.rapidFire();
        }
    }
}
