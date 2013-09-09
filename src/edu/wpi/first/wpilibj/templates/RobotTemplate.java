/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot implements Runnable
{
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    NetworkTable networkTable = null;
    Teleoperated teleop;
    Autonomous auton;
    Arm arm;
    DiskHandling diskHandling;
    DriveTrain driveTrain;
    Shooter shooter;
    XboxControllers controllers;
    MacroBox MB;
    Pneumatics Pneumatics;
    DriverStationLCD DS;
    ScheduledTaskManager stm = null;
    
    
    boolean prevModeSelect1;
    boolean prevModeSelect2; 
    
    boolean prevModeStart1;
    boolean prevModeStart2;
    
    boolean keyPressedSelect = false;
    boolean keyPressedStart = false;
    
    boolean displayModeDebounce = false;
    int displayMode = 0;
    final int kMAXDISPLAYMODE = 3;
    
    int LCDcounter = 0;
    
    boolean smartDashBoard = false;
    
    public void robotInit() 
    {   
        Pneumatics = new Pneumatics(networkTable);
        arm = new Arm(Pneumatics);
        diskHandling = new DiskHandling(Pneumatics);
        driveTrain = new DriveTrain(Pneumatics);
        shooter = new Shooter(driveTrain, Pneumatics);
        controllers = new XboxControllers(1,2);
        MB = new MacroBox(3);
        teleop = new Teleoperated(arm, diskHandling, driveTrain, shooter, controllers, Pneumatics, MB);
        auton = new Autonomous(arm, diskHandling, driveTrain, shooter, controllers, Pneumatics);
        DS = DriverStationLCD.getInstance();
        
        stm = new ScheduledTaskManager();
        stm.setCallBack(this);
    }

    public void run()
    {
        if (super.isSystemActive()){
            if (super.isAutonomous()){
                auton.update10ms(); 
            }
        }
    }
    
    public void autonomousInit()
    {
        stm.start();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()   
    { 
        if (auton.autonMode == 0){
            
        }else{
            shooter.tiltUpdate();
        }
        
        shooter.wheelUpdate();
        
        DS.println(Line.kUser1, 1, "Count      " + auton.count +  "          ");
        DS.println(Line.kUser2, 1, "SubAutonMode   " + auton.subAutonMode + "          ");
        DS.println(Line.kUser3, 1, "Auton Mode      " + auton.autonMode +    "          ");
        DS.println(Line.kUser4, 1, "Rapid Fire " + auton.rapidFireState + "         ");
        DS.println(Line.kUser5, 1, "Right Distance   " + driveTrain.rightEncoder.getDistance());
        DS.println(Line.kUser6, 1, "Left Distance   " + driveTrain.leftEncoder.getDistance());
        
        DS.updateLCD();
        
        if (smartDashBoard == true){
                    SmartDashboard.putNumber("Right Encoder Raw", driveTrain.rightEncoder.getRaw());
                    SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());
                    SmartDashboard.putNumber("Right Rate", driveTrain.rightEncoder.getRate());
                    SmartDashboard.putNumber("Right Setpoint", 0);
                    SmartDashboard.putNumber("Right Error", driveTrain.rightDriveError);
                    SmartDashboard.putNumber("Right Correction", driveTrain.rightDriveCorrection);
                    SmartDashboard.putNumber("Right Power", driveTrain.rightDrive.get());

                    SmartDashboard.putNumber("Left Encoder Raw", driveTrain.leftEncoder.getRaw());
                    SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
                    SmartDashboard.putNumber("Left Rate", driveTrain.leftEncoder.getRate());
                    SmartDashboard.putNumber("Left Setpoint", 0);
                    SmartDashboard.putNumber("Left Error", driveTrain.leftDriveError);
                    SmartDashboard.putNumber("Left Correction", driveTrain.leftDriveCorrection);
                    SmartDashboard.putNumber("Left Power", driveTrain.leftDrive.get());

                    SmartDashboard.putNumber("Shooter Period", shooter.wheelPeriod);
                    SmartDashboard.putNumber("Shooter Speed", shooter.actualRPM);
                    SmartDashboard.putNumber("RPM", shooter.actualRPM);
                    SmartDashboard.putNumber("Set Point", shooter.wheelSetPoint);
                    SmartDashboard.putNumber("Error", shooter.wheelError);
                    SmartDashboard.putNumber("Correction", shooter.wheelCorrection);

                    SmartDashboard.putNumber("Tilt Value", shooter.currentTilt);
                    SmartDashboard.putNumber("Tilt Set Point", shooter.tiltSetPoint);
                    SmartDashboard.putNumber("Tilt Correction", shooter.tiltCorrection);
                    SmartDashboard.putNumber("Tilt Error", shooter.tiltError);

                    SmartDashboard.putBoolean("LockDown", driveTrain.lockDown);
                    SmartDashboard.putBoolean("Gyro Rotate", driveTrain.gyroRotate);
                    SmartDashboard.putBoolean("Move Rate", driveTrain.moveRate);
                    SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                    

                    SmartDashboard.putBoolean("Compressor On", !Pneumatics.pressureSwitch.get());
                    SmartDashboard.putNumber("Pressure Debounce", Pneumatics.pressureDebounce);
           }
    }
    
    public void auton10ms()
    {
        
    }

    public void teleopInit()
    {
        shooter.tiltSetPoint = 544;
        LCDcounter = 0;
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        teleop.update();
                
        shooter.tiltUpdate();
        shooter.wheelUpdate();
        
        System.out.println("Gyro Ticks   " + driveTrain.gyro.getAngle());
        
        if (controllers.driver_SelectButton()||controllers.operator_SelectButton()) {
        if (!displayModeDebounce) {
            if (++displayMode > kMAXDISPLAYMODE) {
                displayMode = 0;
            }
            displayModeDebounce = true;
        }
        } else {
            displayModeDebounce = false;            
        }
                
        switch (displayMode) {
            case 0:
        //                                  12345678901234567890
                DS.println(Line.kUser1, 1, "Shooter " + shooter.actualRPM + "    ");
                DS.println(Line.kUser2, 1, "Tilt " + shooter.currentTilt + "    ");
                DS.println(Line.kUser3, 1, "Left " + driveTrain.leftDrive.get() + "    ");
                DS.println(Line.kUser4, 1, "Right " + driveTrain.rightDrive.get() + "    ");
                DS.println(Line.kUser5, 1, "Roller " + arm.roller.get() + "    ");
                if (teleop.autoFire) {
                    DS.println(Line.kUser6, 1, "Auto Fire " + teleop.autoFireState + "   "+teleop.autoFireCount +"        ");
                } else {
                    DS.println(Line.kUser6, 1, "                    ");                    
                }
                
                if (smartDashBoard == true){
                    SmartDashboard.putNumber("Right Encoder Raw", driveTrain.rightEncoder.getRaw());
                    SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());
                    SmartDashboard.putNumber("Right Rate", driveTrain.rightEncoder.getRate());
                    SmartDashboard.putNumber("Right Setpoint", 0);
                    SmartDashboard.putNumber("Right Error", driveTrain.rightDriveError);
                    SmartDashboard.putNumber("Right Correction", driveTrain.rightDriveCorrection);
                    SmartDashboard.putNumber("Right Power", driveTrain.rightDrive.get());

                    SmartDashboard.putNumber("Left Encoder Raw", driveTrain.leftEncoder.getRaw());
                    SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
                    SmartDashboard.putNumber("Left Rate", driveTrain.leftEncoder.getRate());
                    SmartDashboard.putNumber("Left Setpoint", 0);
                    SmartDashboard.putNumber("Left Error", driveTrain.leftDriveError);
                    SmartDashboard.putNumber("Left Correction", driveTrain.leftDriveCorrection);
                    SmartDashboard.putNumber("Left Power", driveTrain.leftDrive.get());

                    SmartDashboard.putNumber("Shooter Period", shooter.wheelPeriod);
                    SmartDashboard.putNumber("Shooter Speed", shooter.actualRPM);
                    SmartDashboard.putNumber("RPM", shooter.actualRPM);
                    SmartDashboard.putNumber("Set Point", shooter.wheelSetPoint);
                    SmartDashboard.putNumber("Error", shooter.wheelError);
                    SmartDashboard.putNumber("Correction", shooter.wheelCorrection);

                    SmartDashboard.putNumber("Tilt Value", shooter.currentTilt);
                    SmartDashboard.putNumber("Tilt Set Point", shooter.tiltSetPoint);
                    SmartDashboard.putNumber("Tilt Correction", shooter.tiltCorrection);
                    SmartDashboard.putNumber("Tilt Error", shooter.tiltError);

                    SmartDashboard.putBoolean("LockDown", driveTrain.lockDown);
                    SmartDashboard.putBoolean("Gyro Rotate", driveTrain.gyroRotate);
                    SmartDashboard.putBoolean("Move Rate", driveTrain.moveRate);
                    SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                    

                    SmartDashboard.putBoolean("Compressor On", !Pneumatics.pressureSwitch.get());
                    SmartDashboard.putNumber("Pressure Debounce", Pneumatics.pressureDebounce);
                }
                break;
            case 1:
                DS.println(Line.kUser1, 1, "Tilt       " + shooter.currentTilt +  "          ");
                DS.println(Line.kUser2, 1, "Setpoint   " + shooter.tiltSetPoint + "          ");
                DS.println(Line.kUser3, 1, "Error      " + shooter.tiltError +    "          ");
                DS.println(Line.kUser4, 1, "Correction " + shooter.tiltCorrection +   "          ");
                DS.println(Line.kUser5, 1, "                    ");
                DS.println(Line.kUser6, 1, "                    ");
                
                if (smartDashBoard == true){
                    SmartDashboard.putNumber("Right Encoder Raw", driveTrain.rightEncoder.getRaw());
                    SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());
                    SmartDashboard.putNumber("Right Rate", driveTrain.rightEncoder.getRate());
                    SmartDashboard.putNumber("Right Setpoint", 0);
                    SmartDashboard.putNumber("Right Error", driveTrain.rightDriveError);
                    SmartDashboard.putNumber("Right Correction", driveTrain.rightDriveCorrection);
                    SmartDashboard.putNumber("Right Power", driveTrain.rightDrive.get());

                    SmartDashboard.putNumber("Left Encoder Raw", driveTrain.leftEncoder.getRaw());
                    SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
                    SmartDashboard.putNumber("Left Rate", driveTrain.leftEncoder.getRate());
                    SmartDashboard.putNumber("Left Setpoint", 0);
                    SmartDashboard.putNumber("Left Error", driveTrain.leftDriveError);
                    SmartDashboard.putNumber("Left Correction", driveTrain.leftDriveCorrection);
                    SmartDashboard.putNumber("Left Power", driveTrain.leftDrive.get());

                    SmartDashboard.putNumber("Shooter Period", shooter.wheelPeriod);
                    SmartDashboard.putNumber("Shooter Speed", shooter.actualRPM);
                    SmartDashboard.putNumber("RPM", shooter.actualRPM);
                    SmartDashboard.putNumber("Set Point", shooter.wheelSetPoint);
                    SmartDashboard.putNumber("Error", shooter.wheelError);
                    SmartDashboard.putNumber("Correction", shooter.wheelCorrection);

                    SmartDashboard.putNumber("Tilt Value", shooter.currentTilt);
                    SmartDashboard.putNumber("Tilt Set Point", shooter.tiltSetPoint);
                    SmartDashboard.putNumber("Tilt Correction", shooter.tiltCorrection);
                    SmartDashboard.putNumber("Tilt Error", shooter.tiltError);

                    SmartDashboard.putBoolean("LockDown", driveTrain.lockDown);
                    SmartDashboard.putBoolean("Gyro Rotate", driveTrain.gyroRotate);
                    SmartDashboard.putBoolean("Move Rate", driveTrain.moveRate);
                                        SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                    


                    SmartDashboard.putBoolean("Compressor On", !Pneumatics.pressureSwitch.get());
                    SmartDashboard.putNumber("Pressure Debounce", Pneumatics.pressureDebounce);
                }
                break;
            case 2:
                DS.println(Line.kUser1, 1, "Period     " + shooter.speedEncoder.getPeriod() + "    ");
                DS.println(Line.kUser2, 1, "RPM        " + shooter.actualRPM + "        ");
                DS.println(Line.kUser3, 1, "setpoint   " + shooter.wheelSetPoint + "        ");
                DS.println(Line.kUser4, 1, "error      " + shooter.wheelError + "        ");
                DS.println(Line.kUser5, 1, "correction " + shooter.wheelCorrection + "        ");
                DS.println(Line.kUser6, 1, "                    ");
                
                if (smartDashBoard == true){
                    SmartDashboard.putNumber("Right Encoder Raw", driveTrain.rightEncoder.getRaw());
                    SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());
                    SmartDashboard.putNumber("Right Rate", driveTrain.rightEncoder.getRate());
                    SmartDashboard.putNumber("Right Setpoint", 0);
                    SmartDashboard.putNumber("Right Error", driveTrain.rightDriveError);
                    SmartDashboard.putNumber("Right Correction", driveTrain.rightDriveCorrection);
                    SmartDashboard.putNumber("Right Power", driveTrain.rightDrive.get());

                    SmartDashboard.putNumber("Left Encoder Raw", driveTrain.leftEncoder.getRaw());
                    SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
                    SmartDashboard.putNumber("Left Rate", driveTrain.leftEncoder.getRate());
                    SmartDashboard.putNumber("Left Setpoint", 0);
                    SmartDashboard.putNumber("Left Error", driveTrain.leftDriveError);
                    SmartDashboard.putNumber("Left Correction", driveTrain.leftDriveCorrection);
                    SmartDashboard.putNumber("Left Power", driveTrain.leftDrive.get());

                    SmartDashboard.putNumber("Shooter Period", shooter.wheelPeriod);
                    SmartDashboard.putNumber("Shooter Speed", shooter.actualRPM);
                    SmartDashboard.putNumber("RPM", shooter.actualRPM);
                    SmartDashboard.putNumber("Set Point", shooter.wheelSetPoint);
                    SmartDashboard.putNumber("Error", shooter.wheelError);
                    SmartDashboard.putNumber("Correction", shooter.wheelCorrection);

                    SmartDashboard.putNumber("Tilt Value", shooter.currentTilt);
                    SmartDashboard.putNumber("Tilt Set Point", shooter.tiltSetPoint);
                    SmartDashboard.putNumber("Tilt Correction", shooter.tiltCorrection);
                    SmartDashboard.putNumber("Tilt Error", shooter.tiltError);

                    SmartDashboard.putBoolean("LockDown", driveTrain.lockDown);
                    SmartDashboard.putBoolean("Gyro Rotate", driveTrain.gyroRotate);
                    SmartDashboard.putBoolean("Move Rate", driveTrain.moveRate);
                    SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                    

                    SmartDashboard.putBoolean("Compressor On", !Pneumatics.pressureSwitch.get());
                    SmartDashboard.putNumber("Pressure Debounce", Pneumatics.pressureDebounce);
//                    SmartDashboard.putNumber("Battery", battery.getAverageValue());
                }
                break;
            default:                
                if (smartDashBoard == true){
                    SmartDashboard.putNumber("Right Encoder Raw", driveTrain.rightEncoder.getRaw());
                    SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());
                    SmartDashboard.putNumber("Right Rate", driveTrain.rightEncoder.getRate());
                    SmartDashboard.putNumber("Right Setpoint", 0);
                    SmartDashboard.putNumber("Right Error", driveTrain.rightDriveError);
                    SmartDashboard.putNumber("Right Correction", driveTrain.rightDriveCorrection);
                    SmartDashboard.putNumber("Right Power", driveTrain.rightDrive.get());

                    SmartDashboard.putNumber("Left Encoder Raw", driveTrain.leftEncoder.getRaw());
                    SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
                    SmartDashboard.putNumber("Left Rate", driveTrain.leftEncoder.getRate());
                    SmartDashboard.putNumber("Left Setpoint", 0);
                    SmartDashboard.putNumber("Left Error", driveTrain.leftDriveError);
                    SmartDashboard.putNumber("Left Correction", driveTrain.leftDriveCorrection);
                    SmartDashboard.putNumber("Left Power", driveTrain.leftDrive.get());

                    SmartDashboard.putNumber("Shooter Period", shooter.wheelPeriod);
                    SmartDashboard.putNumber("Shooter Speed", shooter.actualRPM);
                    SmartDashboard.putNumber("RPM", shooter.actualRPM);
                    SmartDashboard.putNumber("Set Point", shooter.wheelSetPoint);
                    SmartDashboard.putNumber("Error", shooter.wheelError);
                    SmartDashboard.putNumber("Correction", shooter.wheelCorrection);

                    SmartDashboard.putNumber("Tilt Value", shooter.currentTilt);
                    SmartDashboard.putNumber("Tilt Set Point", shooter.tiltSetPoint);
                    SmartDashboard.putNumber("Tilt Correction", shooter.tiltCorrection);
                    SmartDashboard.putNumber("Tilt Error", shooter.tiltError);
                    SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                    

                    SmartDashboard.putBoolean("LockDown", driveTrain.lockDown);
                    SmartDashboard.putBoolean("Gyro Rotate", driveTrain.gyroRotate);
                    SmartDashboard.putBoolean("Move Rate", driveTrain.moveRate);

                    SmartDashboard.putBoolean("Compressor On", !Pneumatics.pressureSwitch.get());
                    SmartDashboard.putNumber("Pressure Debounce", Pneumatics.pressureDebounce);
//                    SmartDashboard.putNumber("Battery", battery.getAverageValue());
                }
                break;
        }
        if ((LCDcounter % 20) == 0){
            DS.updateLCD();
        }
        LCDcounter++;    
    }
    
    public void disabledInit()
    {
        DS.println(Line.kUser1, 1, "Hello World");
        stm.stop();
        driveTrain.gyro.reset();
        driveTrain.leftEncoder.reset();
        driveTrain.rightEncoder.reset();
        LCDcounter = 0;
    }
    
    public void disabledPeriodic()
    {
        DS.println(DriverStationLCD.Line.kUser1, 1, "Auton Mode " + auton.autonMode);
        SmartDashboard.putNumber("Gyro Angle", driveTrain.gyro.getAngle());                   
        SmartDashboard.putNumber("Left Distance", driveTrain.leftEncoder.getDistance());
        SmartDashboard.putNumber("Right Distance", driveTrain.rightEncoder.getDistance());

        if (controllers.driver_ButtonA()){
            smartDashBoard = false;
        }
        
        if (controllers.driver_ButtonB()){
            smartDashBoard = true;
        }
        
        if ((!controllers.driver_SelectButton()) && (prevModeSelect1) && (prevModeSelect2)){
            auton.autonMode += 1;
        }
        prevModeSelect2 = prevModeSelect1;
        prevModeSelect1 = controllers.driver_SelectButton();
        
        if ((!controllers.driver_StartButton()) && (prevModeStart1) && (prevModeStart2)){
            auton.autonMode -= 1;
        }
        prevModeStart2 = prevModeStart1;
        prevModeStart1 = controllers.driver_StartButton();
                
        if (auton.autonMode < 0){
            auton.autonMode = 0;
        }
        
        if (auton.autonMode > 7 ){
            auton.autonMode = 7;
        }
        
        switch (auton.autonMode){
            case 0:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "Shoot 3                      ");
                DS.println(Line.kUser3, 1, "TiltValue  " + shooter.currentTilt + "                    ");
                DS.println(Line.kUser4, 1, "                             ");
                DS.println(Line.kUser5, 1, "                             ");
                DS.println(Line.kUser6, 1, "                             ");
                break;
                
            case 1:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "Shoot 3                      ");
                DS.println(Line.kUser3, 1, "Get Pyramid Discs            ");
                DS.println(Line.kUser4, 1, "Go Back To Pyramid           ");
                DS.println(Line.kUser5, 1, "Shoot 2                      ");
                DS.println(Line.kUser6, 1, "                             ");
                break;
            case 2:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "Shoot 3                      ");
                DS.println(Line.kUser3, 1, "Get Pyramid Discs            ");
                DS.println(Line.kUser4, 1, "Get Middle Discs             ");
                DS.println(Line.kUser5, 1, "Shoot 4                      ");
                DS.println(Line.kUser6, 1, "                             ");
                break;
             case 3:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "Shoot 3                      ");
                DS.println(Line.kUser3, 1, "Get Line Discs               ");
                DS.println(Line.kUser4, 1, "5 DISK HARD WAY              ");
                DS.println(Line.kUser5, 1, "                             ");
                DS.println(Line.kUser6, 1, "Shoot 2                      ");
                break; 
             
             case 4:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "Shoot 3                      ");
                DS.println(Line.kUser3, 1, "Get Pyramid Discs            ");
                DS.println(Line.kUser4, 1, "Go Back To Pyramid           ");
                DS.println(Line.kUser5, 1, "Shoot 2                      ");
                DS.println(Line.kUser6, 1, "NEW FIVE DISK!!!!!           ");
                break;
             
             case 5:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "                             ");
                DS.println(Line.kUser3, 1, "RIGHT                        ");
                DS.println(Line.kUser4, 1, "                             ");
                DS.println(Line.kUser5, 1, "                             ");
                DS.println(Line.kUser6, 1, "                             ");
               break;  
             
             case 6:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "                             ");
                DS.println(Line.kUser3, 1, "LEFT                         ");
                DS.println(Line.kUser4, 1, "                             ");
                DS.println(Line.kUser5, 1, "                             ");
                DS.println(Line.kUser6, 1, "                             ");
               break; 
             
             case 7:
                //                          12345678901234567890
                DS.println(Line.kUser1, 1, "Auton Mode " + auton.autonMode + "                      ");
                DS.println(Line.kUser2, 1, "                              ");
                DS.println(Line.kUser3, 1, "7 DISK                        ");
                DS.println(Line.kUser4, 1, "SHOOT 4                       ");
                DS.println(Line.kUser5, 1, "                              ");
                DS.println(Line.kUser6, 1, "                              ");
               break;
        }
        if ((LCDcounter % 20) == 0){
            DS.updateLCD();
        }
        LCDcounter++;
    }
}
