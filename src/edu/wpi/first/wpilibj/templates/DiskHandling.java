/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Kelly
 */

public class DiskHandling 
{  
    Solenoid hopperUp;
    
    public DiskHandling(Pneumatics Pneumatics) 
    {
        hopperUp = Pneumatics.hopperUp;
    }
    
    public void hopperUp() 
    {
        hopperUp.set(true);
    }
    
    public void hopperDown() 
    {
        hopperUp.set(false);
    }
    
    public void queue()
    {
        hopperUp();
        hopperDown();
    }
}
