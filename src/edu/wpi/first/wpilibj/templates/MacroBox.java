/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author tdiodato
 */
public class MacroBox {
    Joystick Macros;
    
    private void init(int JS3){
        Macros = new Joystick(JS3);
    }
    
    public MacroBox(int js) {
        init(js);
    }
    
    public int getTiltSetpoint(){
        
        int theSetpoint = 0;
        
        if (Macros.getRawButton(1)) {    // A button - load from feeder
            theSetpoint = 500;
        
        }
        if (Macros.getRawButton(2)) {    // B button - full field
            theSetpoint = 500;
            
        }
        if (Macros.getRawButton(4)) {    // Y button - the PRIME LOCATION
            theSetpoint = 500;
        }
        if (Macros.getRawButton(3)) {    // X button - shoot when hanging
            theSetpoint = 500;
            
        }
        return theSetpoint;
    }
    public int getShooterSetpoint(){
        
        int theSetpoint = 0;
        
        if (Macros.getRawButton(1)) {    // A button - load from feeder
            theSetpoint = 3500;
        
        }
        if (Macros.getRawButton(2)) {    // B button - full field
            theSetpoint = 3500;
            
        }
        if (Macros.getRawButton(4)) {    // Y button - the PRIME LOCATION
            theSetpoint = 3500;
        }
        if (Macros.getRawButton(3)) {    // X button - shoot when hanging
            theSetpoint = 3500;
            
        }
        return theSetpoint;
    }
}
