/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import java.util.TimerTask;

/**
 *
 * @author Kelly
 */
public class ScheduledTask extends TimerTask
{
    Runnable callBack = null;
    
    public void setCallBack(Runnable callBack)
    {
        this.callBack = callBack;
    }
    
    public void run()
    {
        if (this.callBack != null){
            this.callBack.run();
        }
    }
}
