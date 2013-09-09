/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Kelly
 */
public class Pneumatics 
{
    NetworkTable networkTable = null;
    Relay compressor;
    DigitalInput pressureSwitch;
    Solenoid driveLow;
    Solenoid hopperUp;
    Solenoid fire;
    
    Solenoid armUp;
    Solenoid armDown;
    Solenoid armLoadOut;
    
    Solenoid xHopperDown;   // practiceBot only
    Solenoid xFireReset;    // practiceBot only
    
    int pressureDebounce;
    
    public Pneumatics(NetworkTable networkTable)
    {
        compressor = new Relay(RobotMap.compressor);
        pressureSwitch = new DigitalInput(RobotMap.pressureSwitch);
        this.networkTable = networkTable;
        driveLow= new Solenoid(1,1);
        hopperUp = new Solenoid(1,2);
        fire= new Solenoid(1,3);
        armDown= new Solenoid(1,4);
        armUp= new Solenoid(1,5);
        armLoadOut= new Solenoid(1,6);
        
        // stuff for practiceBot
        xHopperDown = new Solenoid(1,7);
        xFireReset = new Solenoid(1,8);
        
    }
    
    public void compress()
    {
        if(pressureSwitch.get() == true){
                compressor.set(Relay.Value.kOff);
                
                if (networkTable != null){
                    networkTable.putBoolean("fromRobot/compressorFullyCompressed", true);
                }
        }else{
                compressor.set(Relay.Value.kForward);
                
                if (networkTable != null){
                    networkTable.putBoolean("fromRobot/compressorFullyCompressed", false);
                }
        }
        
        if (RobotMap.PracticeBot) {
            // These simulate the spring return of the comp bot's solenoids
            if (fire.get()) {
                xFireReset.set(false);
            }else{
                xFireReset.set(true);                
            }
            if (hopperUp.get()) {
                xHopperDown.set(false);
            }else{
                xHopperDown.set(true);                
            }
        }
    }
}
