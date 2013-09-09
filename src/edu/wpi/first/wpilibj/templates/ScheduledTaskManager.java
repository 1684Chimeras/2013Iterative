/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import java.util.Timer;

/**
 *
 * @author Kelly
 */
public class ScheduledTaskManager 
{
    long period = 10L;
    Timer loopTimer = null;
    Runnable callBack = null;
    
    public void setCallBack(Runnable callBack)
    {
        this.callBack = callBack;
    }
    
    public void start()
    {
        this.loopTimer = new Timer();
        ScheduledTask task = new ScheduledTask();
        task.setCallBack(callBack);
        this.loopTimer.schedule(task, 0L, period);
    }
    
    public void stop()
    {
        if (this.loopTimer != null){
            this.loopTimer.cancel();
        }
    }
}
