/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author Kelly
 */
public class Arm 
{
    Solenoid armUp;
    Solenoid armDown;
    Solenoid armLoadOut;
    Timer timer;
    Jaguar roller;
    
    public Arm(Pneumatics Pneumatics)
    {
        armUp = Pneumatics.armUp;
        armDown = Pneumatics.armDown;
        armLoadOut = Pneumatics.armLoadOut;
        timer = new Timer();
        timer.start();
        
        roller = new Jaguar(RobotMap.Roller);
    }
    
    public void armUp()
    {
        armUp.set(true);
        armDown.set(false);
    }
    
    public void armDown()
    {
        armUp.set(false);
        armDown.set(true);
        armLoadOut.set(false);
    }
    
    public void armLoadPosition()
    {
        armLoadOut.set(true);
        armUp.set(true);
        armDown.set(false);
    }
    
    public void armLoadOut()
    {
        armLoadOut.set(true);
    }
    
    public void armLoadIn()
    {
        armLoadOut.set(false);
    }
    
    public void rollerOut()
    {
        roller.set(0.85);
    }
    public void rollerOff()
    {
        roller.set(0.0);
    }
    public void rollerIn()
    {
        roller.set(-0.85);
    }
}