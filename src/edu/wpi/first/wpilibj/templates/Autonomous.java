/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kelly
 */
public class Autonomous 
{
    NetworkTable networkTable = null;
    Arm arm;
    DiskHandling diskHandling;
    DriveTrain driveTrain;
    Shooter shooter;
    XboxControllers controllers;
    Pneumatics pneumatics;
    Timer timer;
    
    String autonModeString;
    char[] autonModeArray;
    int count;
    int autonMode = 0;
    double leftDistance;
    double rightDistance;
    int subAutonMode = 0;
    int shotStage = 0;
    int netStage = 0;
    boolean compress = true;
    
    int autoFireCount = 0;
    
    double maxFpS = 13.5513;
    
    int rapidFireCounter = 0;
    int rapidFireState = 0;
    
    int lineStepCount = 0;
    int counter0 = 0;
    int counter5 = 0;
    int counter2 = 0;
    int counter6 = 0;
    int counter3 = 0;
    int counter7 = 0;
    
    public Autonomous(Arm Arm, DiskHandling DiskHandling, DriveTrain drive, Shooter Shooter, XboxControllers xboxControllers, Pneumatics Pneumatics)
    {
        arm = Arm;
        diskHandling = DiskHandling;
        driveTrain = drive;
        shooter = Shooter;
        controllers = xboxControllers;
        pneumatics = Pneumatics; 
        timer = new Timer();
        
        timer.start();
        count = -1;
    }
    
    public void autonInit()
    {
        subAutonMode = 0;
    }
     
    public void shoot3FAST()
    {           
        if (count == 0){
            shooter.tiltSetPoint = 519;
            shooter.wheelSetPoint = 3700;
            arm.armDown();
        }        
        
        if ((count == 200)){
            if (shooter.wheelError < 100){
                System.out.println("Up To Speed!!!!!!!!!!!!");
            }
            diskHandling.hopperUp();
            shooter.fire();
            netStage = 1;
        }
        
        if ((count == 250)){
            diskHandling.hopperDown();
            shooter.reset();
            netStage = 2;
        }
        
        if ((count == 350)){
            diskHandling.hopperUp();
            shooter.fire();
            netStage = 3;
        }
        
        if ((count == 400)){
            diskHandling.hopperDown();
            shooter.reset();
            netStage = 4;
        }
        
        if ((count == 500)){
            diskHandling.hopperUp();
            shooter.fire();
            netStage = 5;
        }
        
        if ((count == 550)){
            diskHandling.hopperDown();
            shooter.reset();
            count = -1;
            subAutonMode ++;
            netStage = 0;
        }
        
        count++;
    } 
    
    public void shoot2()
    {        
        if (count == 0){
            shooter.wheelSetPoint = 3700;
            arm.armDown();
        }        
        
        if ((count == 200)){
            if (shooter.wheelError < 100){
                System.out.println("Up To Speed!!!!!!!!!!!!");
            }
            diskHandling.hopperUp();
            shooter.fire();
            netStage = 1;
        }
        
        if ((count == 250)){
            diskHandling.hopperDown();
            shooter.reset();
            shooter.tiltSetPoint = 504;
            netStage = 2;
        }
        
        if ((count == 350)){
            diskHandling.hopperUp();
            shooter.fire();
            netStage = 3;
        }
        
        if ((count == 400)){
            diskHandling.hopperDown();
            shooter.reset();
            count = -1;
            subAutonMode++;
        }
        
        count++;
    }
    
    public void rapidFireShoot3()
    {   
        if (count == 0){
            shooter.tiltSetPoint = 525;
            shooter.wheelSetPoint = 3700;
            rapidFireCounter = 0;
            rapidFireState = 0;     
            System.out.println("Rapid Fire Count == 0");
        }
        
        if (count > 0){
            switch(rapidFireState){
                case 0:
                    diskHandling.hopperUp();
                    shooter.fire();
                    rapidFireState = 1;
                    break;
                case 1:
                    if ((shooter.wheelError <= 100) && (shooter.actualRPM > 2000) ){
                        diskHandling.hopperDown();
                        shooter.reset();
                        rapidFireState = 2;
                    }
                    break;
                case 2:
                        diskHandling.hopperUp();
                        shooter.fire();
                        rapidFireState = 3;
                    break;
                case 3:
                    if ((shooter.wheelError <= 100) && (shooter.actualRPM > 2000)){
                        diskHandling.hopperDown();
                        shooter.reset();
                        rapidFireState = 4;
                    }
                    break;
                case 4:
                    diskHandling.hopperUp();
                    shooter.fire();
                    rapidFireState = 5;                    
                    break;
                case 5:
                    if ((shooter.wheelError <= 100) && (shooter.actualRPM > 2000)){
                        diskHandling.hopperDown();
                        shooter.reset();
                        rapidFireState = 6;
                    }
                    break;
                case 6:
                    count = -1;
                    subAutonMode++;
                    break;      
                default:
                    break;
            }            
        }    
            count++;
    }
    
    public void rapidFireShoot2()
    {   
        if (count == 0){
            shooter.tiltSetPoint = 513;
            shooter.wheelSetPoint = 3700;
            rapidFireCounter = 0;
            rapidFireState = 0;            
        }
        
        if (count > 0){
            switch(rapidFireState){
                case 0:
                    diskHandling.hopperUp();
                    shooter.fire();
                    rapidFireState = 1;
                    break;
                case 1:
                    if ((shooter.wheelError <= 100) && (shooter.actualRPM > 2000)){
                        diskHandling.hopperDown();
                        shooter.reset();
                        rapidFireState = 2;
                    }
                    break;
                case 2:
                    diskHandling.hopperUp();
                    shooter.fire();
                    rapidFireState = 3;                    
                    break;
                case 3:
                    if (shooter.wheelError <= 100){
                        diskHandling.hopperDown();
                        shooter.reset();
                        rapidFireState = 4;
                    }
                    break;
                case 4:
                    count = -1;
                    subAutonMode++;
                    break;      
                default:
                    break;
            }
            count++;
        }    
    }
    
    public void armDownMacro()
    {
        if (count > 350){
            arm.armLoadIn();
            arm.armDown();
            count = -1;
            subAutonMode++;
        }
        count++;
        shooter.tiltSetPoint = 504;
    } 
    
    public void armDownMacroNoTilt()
    {
        if (count > 200){
            arm.armLoadIn();
            arm.armDown();
            count = -1;
            subAutonMode++;
        }
        count++;
    }
    
    public void forwardToPyramidDisks()
    {   
        driveTrain.shiftLow();
        
        if ((count > 0) && (count < 36)){
            driveTrain.arcadeDrive(-0.3, 0.0, false);
        }
        
        if ((count >= 36) && (count < 72)){
            driveTrain.arcadeDrive(-0.4, 0.0, true);
        }
        
        if ((count >= 72) && (count < 108)){
            driveTrain.arcadeDrive(-0.3, 0.0, true);
        } 
        
        if ((count >= 108) && (count < 150)){
            driveTrain.arcadeDrive(0.0, 0.0, true);
            count = -1;
            subAutonMode ++;
        }
        
        count++;
    }
    
    public void stopEverything()
    {
        driveTrain.leftEncoder.reset();
        driveTrain.rightEncoder.reset();
        shooter.shooterOff();
        driveTrain.arcadeDrive(0.0, 0.0, false);
        arm.armDown();
        arm.rollerOff();
        subAutonMode = -1;
        count = -1;
        
    }
    
    public void update10ms()
    {
        if (compress){
            pneumatics.compress();
        }else{
            pneumatics.compressor.set(Value.kOff);
        }

        switch(autonMode){
            //SHOOT 3 ONLY!
            case 0:
                switch(subAutonMode){ 
                    case 0:
                        compress = true;
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shooter.wheelSetPoint = 3900;
                        subAutonMode = 1;
                        break;
                        //Shoot 3
                    case 1:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter0 = 20;
                        subAutonMode = 2;
                        break;
                    case 2:
                        compress = false;
                        if (counter0 <= 0){
                            subAutonMode = 3;                            
                        }
                        break;
                    case 3:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter0 = 10;
                            subAutonMode = 4;
                        }
                        break;
                    case 4:
                        compress = false;
                        if (counter0 <= 0){
                            subAutonMode = 5;
                        }
                        break;
                    case 5:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter0 = 20;
                        subAutonMode = 6;
                        break;
                    case 6:
                        compress = false;
                        if (counter0 <= 0){
                            subAutonMode = 7;                            
                        }
                        break;
                    case 7:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter0 = 10;
                            subAutonMode = 8;
                        }
                        break;
                    case 8:
                        compress = false;
                        if (counter0 <= 0){
                            subAutonMode = 9;
                        }
                        break;
                    case 9:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter0 = 20;
                        subAutonMode = 10;
                        break;
                    case 10:
                        compress = false;
                        if (counter0 <= 0){
                            subAutonMode = 11;
                        }
                        break;
                    case 11:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter0 = 10;
                            subAutonMode = 12;
                        }
                        break;
                    default:
                        stopEverything();
                        break;
                }
                counter0--;
                break;
            //SHOOT 3 & GET PYRAMID DISCS & SHOOT 2 
            //5 DISK EASY
            case 1:
                switch(subAutonMode){
                    case 0:
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shoot3FAST();
                        break;
                    case 1:
                        arm.rollerIn();
                        forwardToPyramidDisks();
                        break;
                    case 2:
                        arm.armLoadPosition();
                        arm.rollerIn();
                        subAutonMode++;
                        break;
                    case 3:
                        arm.rollerOff();
                        shooter.tiltSetPoint = 511;
                        armDownMacroNoTilt();
                        break;
                    case 4:
                        shoot2();
                        arm.rollerOff();
                        break;
                    default:
                        stopEverything();
                        break;
                }
                break;
            //SHOOT 3 & GET PYRAMID DISCS & SHOOT 2 & GET MID DISCS & SHOOT 2    
            //7 DISK
            case 2:
                switch(subAutonMode){
                    case 0:
                        compress = true;
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shooter.tiltSetPoint = 538;
                        shooter.wheelSetPoint = 3900;
                        subAutonMode = 1;
                        break;
                    //Shoot 3
                    case 1:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 2;
                        break;
                    case 2:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 3;                            
                        }
                        break;
                    case 3:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 4;
                        }
                        break;
                    case 4:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 5;
                        }
                        break;
                    case 5:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 6;
                        break;
                    case 6:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 7;                            
                        }
                        break;
                    case 7:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 8;
                        }
                        break;
                    case 8:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 9;
                        }
                        break;
                    case 9:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 10;
                        break;
                    case 10:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 11;
                        }
                        break;
                    case 11:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 12;
                        }
                        break;
                    //Move to pyramid disks
                    case 12:
                        compress = true;
                        arm.rollerIn();
                        if (counter2 <= 0){
                            subAutonMode = 13;
                        }
                        break;
                    case 13:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.4, 0.0, true);
                        counter2 = 20;
                        subAutonMode = 14;
                        break;
                    case 14:
                        if (counter2 <= 0){
                            subAutonMode = 15;
                        }
                        break;
                    case 15:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.5, 0.0, true);
                        counter2 = 20;
                        subAutonMode = 16;
                        break;
                    case 16:
                        if (counter2 <= 0){
                            subAutonMode = 17;
                        }
                        break;
                    case 17:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.4, 0.0, true);
                        counter2 = 25;
                        subAutonMode = 18;
                        break;
                    case 18:
                        if (counter2 <= 0){
                            subAutonMode = 19;
                        }
                        break;
                    case 19:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(0.0, 0.0, true);
                        counter2 = 20;
                        subAutonMode = 20;
                        break;
                    //Pick Up Pyramid disks
                    case 20:
                        if (counter2 <= 0){
                            subAutonMode = 21;
                        }
                        break;
                    case 21:
                        arm.armLoadPosition();
                        counter2 = 200;
                        subAutonMode = 22;
                        break;
                    case 22:
                        if (counter2 <= 0){
                            subAutonMode = 23;
                        }
                        break;
                    case 23:
                        arm.armDown();
                        arm.rollerOff();
                        counter2 = 100;
                        subAutonMode = 24;
                        break;
                    case 24:
                        if (counter2 <= 0){
                            subAutonMode = 25;
                        }
                        break;
                        //shoot 2
                    case 25:
                        compress = false;
//                        shooter.tiltSetPoint = 528;
//                        shooter.tiltSetPoint = 537;
                        shooter.tiltSetPoint = 535;
                        subAutonMode = 26;
                        break;
                    case 26:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 27;
                        break;
                    case 27:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 28;                            
                        }
                        break;
                    case 28:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 29;
                        }
                        break;
                    case 29:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 30;
                        }
                        break;
                    case 30:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 31;
                        break;
                    case 31:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 32;                            
                        }
                        break;
                    case 32:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 33;
                        }
                        break;
                        // move to mid disks
                    case 33:
                        compress = true;
                        if (counter2 <= 0){
                            subAutonMode = 34;                            
                        }
                        break;
                    case 34:
                        arm.rollerIn();
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter2 = 55;
                        subAutonMode = 35;
                        break;
                    case 35:
                        if (counter2 <= 0){
                            subAutonMode = 36;                            
                        }
                        break;
                    case 36:
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.7, -0.045, false);
                        counter2 = 65;
                        subAutonMode = 37;
                        break;
                    case 37:
                        if (counter2 <= 0){
                            subAutonMode = 38;                            
                        }
                        break;
                    case 38:
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter2 = 55;
                        subAutonMode = 39;
                        break;
                    case 39:
                        if (counter2 <= 0){
                            subAutonMode = 40;                            
                        }
                        break; 
                    case 40:
                        driveTrain.arcadeDrive(-0.2, -0.045, false);
                        counter2 = 55;
                        subAutonMode = 41;
                        break;
                    case 41:
                        if (counter2 <= 0){
                            subAutonMode = 42;                            
                        }
                        break;      
                    case 42:
                        driveTrain.arcadeDrive(0.0, 0.0, false);
                        counter2 = 10;
                        subAutonMode = 43;
                        break;
                    case 43:
                        if (counter2 <= 0){
                            subAutonMode = 44;                            
                        }
                        break;
                    //Pick up Mid Disks
                    case 44:
                        arm.armLoadPosition();
                        counter2 = 10;
                        subAutonMode = 45;
                        break;
                    case 45:
                        if (counter2 <= 0){
                            subAutonMode = 49;
                        }
                        break;
                    case 49:
                        driveTrain.arcadeDrive(0.6, -0.045, false);
                        counter2 = 27;
                        subAutonMode = 50;
                        break;
                    case 50:
                        if (counter2 <= 0){
                            subAutonMode = 51;                            
                        }
                        break;
                    case 51:
                        driveTrain.arcadeDrive(0.7, -0.045, false);
                        counter2 = 32;
                        subAutonMode = 52;
                        break;
                    case 52:
                        if (counter2 <= 0){
                            subAutonMode = 53;                            
                        }
                        break;
                    case 53:
                        driveTrain.arcadeDrive(0.7, -0.045, false);
                        counter2 = 27;
                        subAutonMode = 54;
                        break;
                    case 54:
                        if (counter2 <= 0){
                            subAutonMode = 55;                            
                        }
                        break; 
                    case 55:
                        driveTrain.arcadeDrive(0.3, -0.045, false);
                        shooter.tiltSetPoint = 510;
                        counter2 = 27;
                        subAutonMode = 56;
                        break;
                    case 56:
                        if (counter2 <= 0){
                            subAutonMode = 57;                            
                        }
                        break;      
                    case 57:
                        driveTrain.arcadeDrive(0.0, 0.0, false);
                        counter2 = 75;
                        subAutonMode = 58;
                        break;
                    case 58:
                        if (counter2 <= 0){
                            arm.armDown();
                            arm.rollerOff();
                            counter2 = 80;
                            subAutonMode = 59;                            
                        }
                        break;
                    case 59:
                        if (counter2 <= 0){
                            subAutonMode = 60; 
                        }
                        break;
                    //Shoot 2
                    case 60:
                        compress = false;
                        subAutonMode = 61;
                        break;
                    case 61:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 30;
                        subAutonMode = 62;
                        break;
                    case 62:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 63;                            
                        }
                        break;
                    case 63:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 64;
                        }
                        break;
                    case 64:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 65;
                        }
                        break;
                    case 65:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter2 = 20;
                        subAutonMode = 66;
                        break;
                    case 66:
                        compress = false;
                        if (counter2 <= 0){
                            subAutonMode = 67;                            
                        }
                        break;
                    case 67:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter2 = 10;
                            subAutonMode = 68;
                        }
                        break;
                    default:
                        stopEverything();
                        break;
                }
                counter2--;
                break;
            //7 DISK NEW!!!!!!!!!!!!!
            case 3:
                switch(subAutonMode){
                    case 0:
                        compress = true;
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shooter.tiltSetPoint = 538;
                        shooter.wheelSetPoint = 3900;
                        subAutonMode = 1;
                        break;
                    //Shoot 3
                    case 1:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter3 = 20;
                        subAutonMode = 2;
                        break;
                    case 2:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 3;                            
                        }
                        break;
                    case 3:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter3 = 10;
                            subAutonMode = 4;
                        }
                        break;
                    case 4:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 5;
                        }
                        break;
                    case 5:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter3 = 20;
                        subAutonMode = 6;
                        break;
                    case 6:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 7;                            
                        }
                        break;
                    case 7:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter3 = 10;
                            subAutonMode = 8;
                        }
                        break;
                    case 8:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 9;
                        }
                        break;
                    case 9:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter3 = 20;
                        subAutonMode = 10;
                        break;
                    case 10:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 11;
                        }
                        break;
                    case 11:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter3 = 10;
                            subAutonMode = 12;
                        }
                        break;
                    //Move to pyramid disks
                    case 12:
                        compress = true;
                        arm.rollerIn();
                        if (counter3 <= 0){
                            subAutonMode = 13;
                        }
                        break;
                    case 13:
                        driveTrain.leftSetPoint_rate = 25;
                        driveTrain.rightSetPoint_rate = 25; 
                        driveTrain.leftEncoder.reset();
                        driveTrain.rightEncoder.reset();
                        arm.rollerIn();
                        counter3 = 22;
                        subAutonMode = 14;
                        break;
                    case 14:
                        if (counter3 <= 0){
                            subAutonMode = 15;
                            counter3 = 23;
                        }
                        
                        if ((counter3 <= 22) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate += 2;
                            driveTrain.rightSetPoint_rate += 2;
                        }
                        break;
                    case 15:
                        if (counter3 <= 0){
                            subAutonMode = 16;
                        }
                        break;
                    case 16:
                        if (counter3 <= 0){
                            subAutonMode = 17;
                            counter3 = 23;
                        }
                        
                        if ((counter3 <= 22) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate -= 2;
                            driveTrain.rightSetPoint_rate -= 2;
                        }
                        break;
                    case 17:
                        if (counter3 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter3 = 22;
                            subAutonMode = 20;
                        }
                        break;
                    case 20:
                        if (counter3 <= 0){
                            subAutonMode = 21;
                        }
                        break;
                    case 21:
                        arm.armLoadPosition();
                        counter3 = 200;
                        subAutonMode = 22;
                        break;
                    case 22:
                        if (counter3 <= 0){
                            subAutonMode = 23;
                        }
                        break;
                    case 23:
                        arm.armDown();
                        arm.rollerOff();
                        counter3 = 100;
                        subAutonMode = 24;
                        break;
                    case 24:
                        if (counter3 <= 0){
                            subAutonMode = 25;
                        }
                        break;
                    //shoot 2
                    case 25:
                        shooter.tiltSetPoint = 535;
                        subAutonMode = 26;
                        break;
                    case 26:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter3 = 20;
                        subAutonMode = 27;
                        break;
                    case 27:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 28;                            
                        }
                        break;
                    case 28:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter3 = 10;
                            subAutonMode = 29;
                        }
                        break;
                    case 29:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 30;
                        }
                        break;
                    case 30:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter3 = 20;
                        subAutonMode = 31;
                        break;
                    case 31:
                        compress = false;
                        if (counter3 <= 0){
                            subAutonMode = 32;                            
                        }
                        break;
                    case 32:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter3 = 10;
                            subAutonMode = 33;
                        }
                        break;
                    // move to mid disks
                    case 33:
                        compress = true;
                        if (counter3 <= 0){
                            subAutonMode = 34;                            
                        }
                        break;
                    case 34:
                        driveTrain.leftSetPoint_rate = 50;
                        driveTrain.rightSetPoint_rate = 50; 
                        driveTrain.leftEncoder.reset();
                        driveTrain.rightEncoder.reset();
                        counter3 = 44;
                        subAutonMode = 35;
                        break;
                    case 35:
                        if (counter3 <= 0){
                            subAutonMode = 36;
                            counter3 = 45;
                        }
                        
                        if ((counter3 <= 44) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate += 2;
                            driveTrain.rightSetPoint_rate += 2;
                        }
                        break;
                    case 36:
                        if (counter3 <= 0){
                            subAutonMode = 37;
                        }
                        break;
                    case 37:
                        if (counter3 <= 0){
                            subAutonMode = 38;
                            counter3 = 45;
                        }
                        
                        if ((counter3 <= 44) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate -= 2;
                            driveTrain.rightSetPoint_rate -= 2;
                        }
                        break;
                    case 38:
                        if (counter3 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter3 = 44;
                            subAutonMode = 39;
                        }
                        break;
                    case 39:
                        if (counter3 <= 0){
                            subAutonMode = 46;
                        }
                        break;
                    //Pick up Mid Disks
                    case 46:
                        arm.armDown();
                        arm.rollerOff();
                        counter3 = 75;
                        subAutonMode = 47;
                        break;
                    case 47:
                        if (counter3 <= 0){
                            subAutonMode = 48;
                        }
                        break;
                    //Go Back to Pyramid
                    case 48:
                        arm.armLoadPosition();
//                        counter3 = 200;
                        counter3 = 10;
                        subAutonMode = 49;
                        break;
                    case 49:
                        if (counter3 <= 0){
                            subAutonMode = 50;
                        }
                        break;
                    case 50:
                        driveTrain.leftSetPoint_rate = -50;
                        driveTrain.rightSetPoint_rate = -50; 
                        driveTrain.leftEncoder.reset();
                        driveTrain.rightEncoder.reset();
                        counter3 = 15;
                        subAutonMode = 51;
                        break;
                    case 51:
                        if (counter3 <= 0){
                            subAutonMode = 52;
                            counter3 = 16;
                        }
                        
                        if ((counter3 <= 15) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate -= 2;
                            driveTrain.rightSetPoint_rate -= 2;
                        }
                        break;
                    case 52:
                        if (counter3 <= 0){
                            subAutonMode = 53;
                        }
                        break;
                    case 53:
                        if (counter3 <= 0){
                            subAutonMode = 54;
                            counter3 = 16;
                        }
                        
                        if ((counter3 <= 15) && (counter3 > 0)){
                            driveTrain.leftSetPoint_rate += 2;
                            driveTrain.rightSetPoint_rate += 2;
                        }
                        break;
                    case 54:
                        if (counter3 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter3 = 15;
                            subAutonMode = 58;
                        }
                        break;
                    case 58:
                        if (counter3 <= 0){
                            arm.armDown();
                            arm.rollerOff();
                            counter3 = 80;
                            subAutonMode = 59;                            
                        }
                        break;
                    case 59:
                        if (counter3 <= 0){
                            subAutonMode = 60;                            
                        }
                        break;
                    default:
                        stopEverything();
                        break;
                }
                driveTrain.autonRatePID();
                counter3--;
                break;
            //Other 5 disk  
            case 4:
                switch(subAutonMode){
                    case 0:
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        rapidFireShoot3();
                        break;
                    case 1:
                        arm.rollerIn();
                        forwardToPyramidDisks();
                        break;
                    case 2:
                        arm.armLoadPosition();
                        arm.rollerOff();
                        subAutonMode++;
                        break;
                    case 3:
                        arm.rollerOff();
                        shooter.tiltSetPoint = 495;
                        armDownMacroNoTilt();
                        break;
                    case 4:
                        rapidFireShoot2();
                        arm.rollerOff();
                        break;
                    default:
                        stopEverything();
                        break;
                }             
                break;
            case 5:
                switch(subAutonMode){
                    case 0:
                        // set up, init
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shooter.tiltSetPoint = 508;
                        shooter.wheelSetPoint = 3700;
                        subAutonMode = 10;              
                        break;           
                    // move back from pyramid
                    case 10:
                        driveTrain.leftSetPoint_rate = -150;
                        driveTrain.rightSetPoint_rate = -150;
                        counter5 = 100;
                        subAutonMode = 11;    
                        break;
                    case 11:
                        if (counter5 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter5 = 20;
                         subAutonMode = 12;    
                        }
                        break;
                    case 12:
                        if (counter5 <= 0) {
                            subAutonMode = 20;
                        }    
                        break;
                    // turn CCW 90-ish deg
                    case 20:
                        driveTrain.leftSetPoint_rate = -150;
                        driveTrain.rightSetPoint_rate = 150;
                        counter5 = 80;
                        subAutonMode = 21;    
                        break;
                    case 21:
                        if (counter5 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter5 = 20;
                            subAutonMode = 22;    
                        }
                        break;
                    case 22:
                        if (counter5 <= 0) {
                            subAutonMode = 30;
                        }    
                        break;
                    // move forward to the Center Line
                    case 30:
                        arm.rollerIn();
                        driveTrain.leftSetPoint_rate = 150;
                        driveTrain.rightSetPoint_rate = 150;
                        counter5 = 200;
                        subAutonMode = 31;
                        break;
                    case 31:
                        if (counter5 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            subAutonMode = 32;
                        }
                        break;
                    case 32:
                        if (counter5 <= 0) {
                            subAutonMode = 33;
                        }    
                        break;
                    case 33:
                        arm.armLoadPosition();
                        arm.rollerIn();
                        subAutonMode = 34;
                        break;
                    case 34:
                        arm.rollerOff();
                        armDownMacroNoTilt();
                        subAutonMode = 50;
                        break;
                    // go back to shoot location
                    case 50: 
                        driveTrain.leftSetPoint_rate = -150;
                        driveTrain.rightSetPoint_rate = -150;
                        counter5 = 200;
                        subAutonMode = 51;
                        break;
                    case 51:
                        if (counter5 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            subAutonMode = 52;
                        }
                        break;
                    case 52:
                        if (counter5 <= 0) {
                            subAutonMode = 60;
                        }    
                        break;
                    case 60:
                        driveTrain.leftSetPoint_rate = 150;
                        driveTrain.rightSetPoint_rate = -150;
                        counter5 = 77;
                        subAutonMode = 61;    
                        break;
                    case 61:
                        if (counter5 <= 0){
                            driveTrain.leftSetPoint_rate = 0;
                            driveTrain.rightSetPoint_rate = 0;
                            counter5 = 20;
                            subAutonMode = 62;    
                        }
                        break;
                    case 62:
                        if (counter5 <= 0) {
                            subAutonMode = 70;
                        }    
                        break;
                        
                    case 70:
                        shoot2();
                        subAutonMode = 80;
                        break;
                        
                    default:
                        break;
                }
                counter5--;
                driveTrain.autonRatePID();
                break;
            //5 DISK HARD LEFT SIDE!!!!!!!!!!!!!!!!!!!!!!
            case 6:
                switch(subAutonMode){
                    case 0:
                        // set up, init
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
                        shooter.tiltSetPoint = 511;
                        shooter.wheelSetPoint = 3900;
                        subAutonMode = 1;              
                        break;           
                    case 1:
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter6 = 20;
                        subAutonMode = 2;
                        break;
                    case 2:
                        if (counter6 <= 0){
                            subAutonMode = 3;                            
                        }
                        break;
                    case 3:
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter6 = 10;
                            subAutonMode = 4;
                        }
                        break;
                    case 4:
                        if (counter6 <= 0){
                            subAutonMode = 5;
                        }
                        break;
                    case 5:
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter6 = 20;
                        subAutonMode = 6;
                        break;
                    case 6:
                        if (counter6 <= 0){
                            subAutonMode = 7;                            
                        }
                        break;
                    case 7:
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter6 = 10;
                            subAutonMode = 8;
                        }
                        break;
                    case 8:
                        if (counter6 <= 0){
                            subAutonMode = 9;
                        }
                        break;
                    case 9:
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter6 = 20;
                        subAutonMode = 10;
                        break;
                    case 10:
                        if (counter6 <= 0){
                            subAutonMode = 11;
                        }
                        break;
                    case 11:
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter6 = 10;
                            subAutonMode = 12;
                        }
                        break;
//                    // move back from pyramid
//                    case 12:
//                        driveTrain.leftSetPoint_rate = -150;
//                        driveTrain.rightSetPoint_rate = -150;
//                        counter6 = 100;
//                        subAutonMode = 13;    
//                        break;
//                    case 13:
//                        if (counter6 <= 0){
//                            driveTrain.leftSetPoint_rate = 0;
//                            driveTrain.rightSetPoint_rate = 0;
//                            counter6 = 20;
//                         subAutonMode = 14;    
//                        }
//                        break;
//                    case 14:
//                        if (counter6 <= 0) {
//                            subAutonMode = 20;
//                        }    
//                        break;
//                    // turn CCW 90-ish deg
//                    case 20:
//                        driveTrain.leftSetPoint_rate = 150;
//                        driveTrain.rightSetPoint_rate = -150;
//                        counter6 = 80;
//                        subAutonMode = 21;    
//                        break;
//                    case 21:
//                        if (counter6 <= 0){
//                            driveTrain.leftSetPoint_rate = 0;
//                            driveTrain.rightSetPoint_rate = 0;
//                            counter6 = 20;
//                            subAutonMode = 22;    
//                        }
//                        break;
//                    case 22:
//                        if (counter6 <= 0) {
//                            subAutonMode = 30;
//                        }    
//                        break;
//                        
//                    // move forward to the Center Line
//                    case 30:
//                        arm.rollerIn();
//                        driveTrain.leftSetPoint_rate = 150;
//                        driveTrain.rightSetPoint_rate = 150;
//                        counter6 = 200;
//                        subAutonMode = 31;
//                        break;
//                    case 31:
//                        if (counter6 <= 0){
//                            driveTrain.leftSetPoint_rate = 0;
//                            driveTrain.rightSetPoint_rate = 0;
//                            subAutonMode = 32;
//                        }
//                        break;
//                    case 32:
//                        if (counter6 <= 0) {
//                            subAutonMode = 33;
//                        }    
//                        break;
//                    
//                    case 33:
//                        arm.armLoadPosition();
//                        arm.rollerIn();
//                        subAutonMode = 34;
//                        break;
//                        
//                    case 34:
//                        arm.rollerOff();
//                        armDownMacroNoTilt();
//                        subAutonMode = 50;
//                        break;
//                        
//                        
//                    // go back to shoot location
//                    case 50: 
//                        driveTrain.leftSetPoint_rate = -150;
//                        driveTrain.rightSetPoint_rate = -150;
//                        counter6 = 200;
//                        subAutonMode = 51;
//                        break;
//                    case 51:
//                        if (counter6 <= 0){
//                            driveTrain.leftSetPoint_rate = 0;
//                            driveTrain.rightSetPoint_rate = 0;
//                            subAutonMode = 52;
//                        }
//                        break;
//                    case 52:
//                        if (counter6 <= 0) {
//                            subAutonMode = 60;
//                        }    
//                        break;
//                        
//                    case 60:
//                        driveTrain.leftSetPoint_rate = 150;
//                        driveTrain.rightSetPoint_rate = -150;
//                        counter6 = 77;
//                        subAutonMode = 61;    
//                        break;
//                    case 61:
//                        if (counter6 <= 0){
//                            driveTrain.leftSetPoint_rate = 0;
//                            driveTrain.rightSetPoint_rate = 0;
//                            counter6 = 20;
//                            subAutonMode = 62;    
//                        }
//                        break;
//                    case 62:
//                        if (counter6 <= 0) {
//                            subAutonMode = 70;
//                        }    
//                        break;
//                        
//                    case 70:
//                        shoot2(2000, 3);
//                        subAutonMode = 80;
//                        break;
//                        
                    default:
                        break;
                    
                }
                counter6--;
                driveTrain.autonRatePID();
                break;
            //7 disk shoot 1 ,shoot 4, shoot 2
            case 7:
                switch(subAutonMode){
                    case 0:
                        compress = true;
                        arm.armLoadIn();
                        arm.armDown();
                        arm.rollerOff();
//                        shooter.tiltSetPoint = 508;
//                        shooter.tiltSetPoint = 530;
//                        shooter.tiltSetPoint = 540;
                        shooter.tiltSetPoint = 538;
                        shooter.wheelSetPoint = 3900;
//                        shooter.wheelSetPoint = 3900;
                        subAutonMode = 1;
                        break;
                        //Shoot 1
                    case 1:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 20;
                        subAutonMode = 2;
                        break;
                    case 2:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 3;                            
                        }
                        break;
                    case 3:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 12;
                        }
                        break;
//                    case 4:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 5;
//                        }
//                        break;
//                    case 5:
//                        compress = false;
//                        diskHandling.hopperUp();
//                        shooter.fire();
//                        counter7 = 20;
//                        subAutonMode = 6;
//                        break;
//                    case 6:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 7;                            
//                        }
//                        break;
//                    case 7:
//                        compress = false;
//                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
//                            diskHandling.hopperDown();
//                            shooter.reset();
//                            counter7 = 10;
//                            subAutonMode = 8;
//                        }
//                        break;
//                    case 8:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 9;
//                        }
//                        break;
//                    case 9:
//                        compress = false;
//                        diskHandling.hopperUp();
//                        shooter.fire();
//                        counter7 = 20;
//                        subAutonMode = 10;
//                        break;
//                    case 10:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 11;
//                        }
//                        break;
//                    case 11:
//                        compress = false;
//                        if ((Math.abs(shooter.wheelError ) < 60) && (shooter.actualRPM > 2000)){
//                            diskHandling.hopperDown();
//                            shooter.reset();
//                            counter7 = 10;
//                            subAutonMode = 12;
//                        }
//                        break;
                        //Move to pyramid disks
                    case 12:
                        compress = true;
                        arm.rollerIn();
                        if (counter7 <= 0){
                            subAutonMode = 13;
                        }
                        break;
                    case 13:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.4, 0.0, true);
                        shooter.tiltSetPoint = 510;
//                        driveTrain.leftSetPoint_rate = 70;
//                        driveTrain.rightSetPoint_rate = 70;
                        counter7 = 20;
                        subAutonMode = 14;
                        break;
                    case 14:
                        if (counter7 <= 0){
                            subAutonMode = 15;
//                            subAutonMode = 21;
                        }
                        break;
                    case 15:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.5, 0.0, true);
                        counter7 = 20;
                        subAutonMode = 16;
                        break;
                    case 16:
                        if (counter7 <= 0){
                            subAutonMode = 17;
                        }
                        break;
                    case 17:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(-0.4, 0.0, true);
                        counter7 = 25;
                        subAutonMode = 18;
                        break;
                    case 18:
                        if (counter7 <= 0){
                            subAutonMode = 19;
                        }
                        break;
                    case 19:
                        driveTrain.shiftLow();
                        driveTrain.arcadeDrive(0.0, 0.0, true);
                        counter7 = 20;
                        subAutonMode = 20;
                        break;
                        //Pick Up Pyramid disks
                    case 20:
                        if (counter7 <= 0){
                            subAutonMode = 21;
                        }
                        break;
                    case 21:
                        arm.armLoadPosition();
                        counter7 = 200;
                        subAutonMode = 22;
                        break;
                    case 22:
                        if (counter7 <= 0){
                            subAutonMode = 23;
                        }
                        break;
                    case 23:
                        arm.armDown();
                        arm.rollerOff();
                        counter7 = 100;
                        subAutonMode = 24;
                        break;
                    case 24:
                        if (counter7 <= 0){
                            subAutonMode = 33;
                        }
                        break;
                        //shoot 2
//                    case 25:
//                        compress = false;
////                        shooter.tiltSetPoint = 528;
////                        shooter.tiltSetPoint = 537;
//                        shooter.tiltSetPoint = 535;
//                        subAutonMode = 26;
//                        break;
//                    case 26:
//                        compress = false;
//                        diskHandling.hopperUp();
//                        shooter.fire();
//                        counter7 = 20;
//                        subAutonMode = 27;
//                        break;
//                    case 27:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 28;                            
//                        }
//                        break;
//                    case 28:
//                        compress = false;
//                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
//                            diskHandling.hopperDown();
//                            shooter.reset();
//                            counter7 = 10;
//                            subAutonMode = 29;
//                        }
//                        break;
//                    case 29:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 30;
//                        }
//                        break;
//                    case 30:
//                        compress = false;
//                        diskHandling.hopperUp();
//                        shooter.fire();
//                        counter7 = 20;
//                        subAutonMode = 31;
//                        break;
//                    case 31:
//                        compress = false;
//                        if (counter7 <= 0){
//                            subAutonMode = 32;                            
//                        }
//                        break;
//                    case 32:
//                        compress = false;
//                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
//                            diskHandling.hopperDown();
//                            shooter.reset();
//                            counter7 = 10;
//                            subAutonMode = 33;
//                        }
//                        break;
                        
                        // move to front pyramid
                    case 33:
                        compress = true;
                        if (counter7 <= 0){
                            subAutonMode = 34;                            
                        }
                        break;
                    case 34:
                        arm.rollerIn();
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 35;
                        break;
                    case 35:
                        if (counter7 <= 0){
                            subAutonMode = 36;                            
//                            subAutonMode = 42;                            
                        }
                        break;
                    case 36:
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.7, -0.045, false);
                        counter7 = 32;
//                        counter7 = 40;
                        subAutonMode = 37;
                        break;
                    case 37:
                        if (counter7 <= 0){
                            subAutonMode = 38;                            
                        }
                        break;
                    case 38:
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 39;
                        break;
                    case 39:
                        if (counter7 <= 0){
                            subAutonMode = 40;                            
                        }
                        break; 
                    case 40:
                        driveTrain.arcadeDrive(-0.2, -0.045, false);
                        counter7 = 32;
                        subAutonMode = 41;
                        break;
                    case 41:
                        if (counter7 <= 0){
                            subAutonMode = 42;                            
                        }
                        break;      
                    case 42:
                        driveTrain.arcadeDrive(0.0, 0.0, false);
                        counter7 = 10;
                        subAutonMode = 43;
                        break;
                    case 43:
                        if (counter7 <= 0){
                            subAutonMode = 44;                            
                        }
                        break;
                        // SHOOT 4
                   case 44:
                        compress = false;
//                        shooter.tiltSetPoint = 484;
                        shooter.tiltSetPoint = 510;
                        subAutonMode = 45;
                        break;
                    case 45:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 30;
                        subAutonMode = 46;
                        break;
                    case 46:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 47;                            
                        }
                        break;
                    case 47:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 48;
                        }
                        break;
                    case 48:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 49;
                        }
                        break;
                    case 49:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 20;
                        subAutonMode = 50;
                        break;
                    case 50:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 51;                            
                        }
                        break;
                    case 51:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 52;
                        }
                        break;
                   case 52:
                        compress = false;
//                        shooter.tiltSetPoint = 484;
//                        shooter.tiltSetPoint = 510;
                        subAutonMode = 53;
                        break;
                    case 53:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 30;
                        subAutonMode = 54;
                        break;
                    case 54:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 55;                            
                        }
                        break;
                    case 55:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 56;
                        }
                        break;
                    case 56:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 57;
                        }
                        break;
                    case 57:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 20;
                        subAutonMode = 58;
                        break;
                    case 58:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 59;                            
                        }
                        break;
                    case 59:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 60;
                        }
                        break;
                    case 60:
                        compress = true;
                        if (counter7 <= 0){
                            subAutonMode = 61;                            
                        }
                        break;
                        // move to mid disks
                    case 61:
                        arm.rollerIn();
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 62;
                        break;
                    case 62:
                        if (counter7 <= 0){
                            subAutonMode = 63;                            
//                            subAutonMode = 42;                            
                        }
                        break;
                    case 63:
                        driveTrain.shiftHigh();
                        driveTrain.arcadeDrive(-0.7, -0.045, false);
                        counter7 = 32;
//                        counter7 = 40;
                        subAutonMode = 64;
                        break;
                    case 64:
                        if (counter7 <= 0){
                            subAutonMode = 65;                            
                        }
                        break;
                    case 65:
                        driveTrain.arcadeDrive(-0.6, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 66;
                        break;
                    case 66:
                        if (counter7 <= 0){
                            subAutonMode = 67;                            
                        }
                        break; 
                    case 67:
                        driveTrain.arcadeDrive(-0.2, -0.045, false);
                        counter7 = 32;
                        subAutonMode = 68;
                        break;
                    case 68:
                        if (counter7 <= 0){
                            subAutonMode = 69;                            
                        }
                        break;      
                    case 69:
                        driveTrain.arcadeDrive(0.0, 0.0, false);
                        counter7 = 10;
                        subAutonMode = 70;
                        break;
                    case 70:
                        if (counter7 <= 0){
                            subAutonMode = 71;                            
                        }
                        break;
                        //Pick up Mid Disks
                    case 71:
                        arm.armLoadPosition();
//                        counter7 = 200;
                        counter7 = 10;
                        subAutonMode = 72;
                        break;
                    case 72:
                        if (counter7 <= 0){
                            subAutonMode = 73;
                        }
                        break;
                    case 73:
                        arm.armDown();
                        arm.rollerOff();
                        counter7 = 75;
                        subAutonMode = 74;
                        break;
                    case 74:
                        if (counter7 <= 0){
                            subAutonMode = 75;
                        }
                        break;
                        //Go Back to Pyramid
//                    case 48:
//                        if (counter7 <= 0){
//                            subAutonMode = 49;                            
//                        }
//                        break;
                    case 75:
//                        driveTrain.arcadeDrive(0.5, -0.05, false);
                        driveTrain.arcadeDrive(0.6, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 76;
                        break;
                    case 76:
                        if (counter7 <= 0){
                            subAutonMode = 77;                            
                        }
                        break;
                    case 77:
                        driveTrain.arcadeDrive(0.7, -0.045, false);
                        counter7 = 32;
                        subAutonMode = 78;
                        break;
                    case 78:
                        if (counter7 <= 0){
                            subAutonMode = 79;                            
                        }
                        break;
                    case 79:
//                        driveTrain.arcadeDrive(0.5, -0.05, false);
                        driveTrain.arcadeDrive(0.7, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 80;
                        break;
                    case 80:
                        if (counter7 <= 0){
                            subAutonMode = 81;                            
                        }
                        break; 
                    case 81:
                        driveTrain.arcadeDrive(0.3, -0.045, false);
                        counter7 = 27;
                        subAutonMode = 82;
                        break;
                    case 82:
                        if (counter7 <= 0){
                            subAutonMode = 83;                            
                        }
                        break;      
                    case 83:
                        driveTrain.arcadeDrive(0.0, 0.0, false);
                        counter7 = 75;
//                        counter7 = 50;
                        subAutonMode = 84;
                        break;
                    case 84:
                        if (counter7 <= 0){
                            arm.armDown();
                            arm.rollerOff();
//                            shooter.tiltSetPoint = 510;
                            counter7 = 80;
                            subAutonMode = 85;                            
                        }
                        break;
                    case 85:
                        if (counter7 <= 0){
                            subAutonMode = 86; 
                        }
                        break;
                    //Shoot 2
                    case 86:
                        compress = false;
//                        shooter.tiltSetPoint = 484;
//                        shooter.tiltSetPoint = 510;
                        subAutonMode = 87;
                        break;
                    case 87:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 30;
                        subAutonMode = 88;
                        break;
                    case 88:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 89;                            
                        }
                        break;
                    case 89:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 90;
                        }
                        break;
                    case 90:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 91;
                        }
                        break;
                    case 91:
                        compress = false;
                        diskHandling.hopperUp();
                        shooter.fire();
                        counter7 = 20;
                        subAutonMode = 92;
                        break;
                    case 92:
                        compress = false;
                        if (counter7 <= 0){
                            subAutonMode = 93;                            
                        }
                        break;
                    case 93:
                        compress = false;
                        if ((Math.abs(shooter.wheelError ) < 100) && (shooter.actualRPM > 2000)){
                            diskHandling.hopperDown();
                            shooter.reset();
                            counter7 = 10;
                            subAutonMode = 94;
                        }
                        break;
                    default:
                        stopEverything();
                        break;
                }
                counter7--;
                break;
            default:
                stopEverything();
                break;
        }               
    }
}
