/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
    import edu.wpi.first.wpilibj.Joystick;


/**
 *
 * @author Kelly Malone
 */
public class XboxControllers
{
    Joystick driverXboxController;
    Joystick operatorXboxController;
    
    private void init (int JS1, int JS2)
    {
        operatorXboxController = new Joystick(JS2) ;
        driverXboxController = new Joystick(JS1);
        
        if (operatorXboxController == null) {
            System.out.println("Cannot get a Joystick !!!!!!!");
        } else {
            System.out.println("Got a Joystick !!!!!!!");
            
        }
    }
    
    public XboxControllers(int JS1, int JS2)
    {
        init(JS1,JS2);
    }

    /**
     *
     * @return boolean indicating the button is pressed
     */
/*
 * *********   Driver Controller    *************
 */
    public boolean driver_ButtonA()
    {
        if( (driverXboxController.getRawButton(1)) )
        {
//            System.out.println("Driver Button A");
            return (true);
        }else{
            return (false);
        }        
    }  
    
    public boolean driver_ButtonB()
    {
        if( (driverXboxController.getRawButton(2)) )
        {
//            System.out.println("Driver Button B");
            return (true);
        }else{
            return (false);
        }                 
    }  
    
    public boolean driver_ButtonX()
    {
        if( (driverXboxController.getRawButton(3))  )
        {
//            System.out.println("Driver Button X");
            return (true);
        }else{
            return (false);
        }                 
    }  
    
    public boolean driver_ButtonY()
    {
        if( (driverXboxController.getRawButton(4)))
        {
//            System.out.println("Driver Button Y");
            return (true);
        }else{
            return (false);
        }                 
    } 
    
    public boolean driver_LeftBumper()
    {
        if( (driverXboxController.getRawButton(5)) )
        {
//            System.out.println("Driver Left Bumper");
            return (true);
        }else{
            return (false);
        }                 
    } 
    
    public boolean driver_RightBumper()
    {
        if( (driverXboxController.getRawButton(6)) )
        {
//            System.out.println("Driver Right Bumper");
            return (true);
        }else{
            return (false);
        }                 
    } 
    
    public boolean driver_LeftClick()
    {
        if( (driverXboxController.getRawButton(9)) )
        {
//            System.out.println("Driver Left Click");
            return (true);
        }else{
            return (false);
        }                 
    }
    
    public boolean driver_RightClick()
    {
        if( (driverXboxController.getRawButton(10)))
        {
//            System.out.println("Driver Right Click");
            return (true);
        }else{
            return (false);
        }                 
    }
    
    public boolean driver_StartButton()
    {
        if( (driverXboxController.getRawButton(8)) )
        {
//            System.out.println("Driver Start Button");
            return (true);
        }else{
            return (false);
        }                 
    }
    
    public boolean driver_SelectButton()
    {
        if( (driverXboxController.getRawButton(7)) )
        {
 //           System.out.println("Driver Select Button");
            return (true);
        }else{
            return (false);
        }                 
    }

    public double driver_RightJoystick_Rotate()
    {
        double DRJR = driverXboxController.getRawAxis(4);
        //if((driverXboxController.getRawAxis(4) > 0.1) || (driverXboxController.getRawAxis(4) < -0.1))
        if((DRJR > 0.1) || (DRJR < -0.1))
        {
//            System.out.println("Driver Right Joystick Rotate");
        }
           
        return (DRJR);
    }
    
    public double driver_RightJoystick_Move()
    {
        double DRJM = driverXboxController.getRawAxis(5);
        if((DRJM > 0.1) || (DRJM < -0.1))
        {
//            System.out.println("Driver Right Joystick Move");
        }
           
        return (DRJM);
    }
    
    public double driver_LeftJoystick_Rotate()
    {
        double DLJR = driverXboxController.getRawAxis(2);
        if((DLJR > 0.1) || (DLJR < -0.1))
        {
//            System.out.println("Driver Left Joystick Rotate");
        }
           
        return (DLJR);
    }
    
    public double driver_LeftJoystick_Move()
    {
        double DLJM = driverXboxController.getRawAxis(1);
        if((DLJM > 0.1) || (DLJM < -0.1))
        {
//            System.out.println("Driver Left Joystick Move");
        }
           
        return (DLJM);
    }
    
    public boolean driver_getLeftJoystickMove()
    {
        if((driverXboxController.getRawAxis(2) > 0.1) || (driverXboxController.getRawAxis(2) < -0.1))
        {
            return true;
        }    
        
        return false;
    }    
    
    public boolean driver_getRightJoystickMove()
    {
        if((driverXboxController.getRawAxis(5) > 0.1) || (driverXboxController.getRawAxis(5) < -0.1))
        {
            return true;
        }    
        
        return false;
    }
    
    public double driver_getTriggerAxis()
    {
        return (driverXboxController.getRawAxis(3));
    }
    
    public double driver_LeftTriggerAxis()
    {
        if( (driverXboxController.getRawAxis(3)) > (0) )
        {
//            System.out.println("Driver Left Trigger");
            return (driverXboxController.getRawAxis(3));
        }else{
            return (0.0);
        }
    }   
    
    public double driver_RightTriggerAxis()
    {
        if( (driverXboxController.getRawAxis(3)) < (0) )
        {
//            System.out.println("Driver Right trigger");
            return (driverXboxController.getRawAxis(3));
        }else{
            return (0.0);
        }                 
    }

    public boolean getDriver_LeftTrigger () 
    {
        if ( driverXboxController.getRawAxis(3) > 0.4 ) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getDriver_RightTrigger () 
    {
        if ( driverXboxController.getRawAxis(3) < -0.4 ) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getDriverHorizontalDPadLeft()
    {
        if (driverXboxController.getRawAxis(6) < 0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean getDriverHorizontalDPadRight()
    {
        if (driverXboxController.getRawAxis(6) > 0){
            return true;
        }else{
            return false;
        }
    }

/*
 * *********   Operator Controller    *************
 */
    public boolean operator_ButtonA()
    {
        if( (operatorXboxController.getRawButton(1)) )
        {
//            System.out.println("Operator Button A");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_ButtonB()
    {
        if( (operatorXboxController.getRawButton(2)) )
        {
//            System.out.println("Operator Button B");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_ButtonX()
    {
        if( (operatorXboxController.getRawButton(3))  )
        {
//            System.out.println("Operator Button X");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_ButtonY()
    {
        if( (operatorXboxController.getRawButton(4)))
        {
//            System.out.println("Operator Button Y");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_LeftBumper()
    {
        if( (operatorXboxController.getRawButton(5)) )
        {
 //           System.out.println("Operator Left Bumper");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_RightBumper()
    {
        if( (operatorXboxController.getRawButton(6)) )
        {
//            System.out.println("Operator Right Bumper");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_LeftClick()
    {
        if( (operatorXboxController.getRawButton(9)) )
        {
//            System.out.println("Operator Left Click");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_RightClick()
    {
        if( (operatorXboxController.getRawButton(10)))
        {
//            System.out.println("Operator Right Click");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_StartButton()
    {
        if( (operatorXboxController.getRawButton(8)) )
        {
//            System.out.println("Operator Start Button");
            return (true);
        }else{
            return (false);
        }
    }
    
    public boolean operator_SelectButton()
    {
        if( (operatorXboxController.getRawButton(7)) )
        {
//            System.out.println("Operator Select Button");
            return (true);
        }else{
            return (false);
        }
    }
    
    public double operator_RightJoystick_Rotate()
    {
        double ORJR = operatorXboxController.getRawAxis(4);
        if((ORJR > 0.1) || (ORJR < -0.1))
        {
//            System.out.println("Operator Right Joystick Rotate");
        }

        return (ORJR);
    }
    
    public double operator_RightJoystick_Move()
    {
        double ORJM = operatorXboxController.getRawAxis(5);
        if((ORJM > 0.1) || (ORJM < -0.1))
        {
//            System.out.println("Operator Right Joystick Move");
        }

        return (ORJM);
    }
    
    public double operator_LeftJoystick_Rotate()
    {
        double OLJR = operatorXboxController.getRawAxis(1);
        if((OLJR > 0.1) || (OLJR < -0.1))
        {
//            System.out.println("Operator Left Joystick Rotate");
        }

        return (OLJR);
    }
    
    public double operator_LeftJoystick_Move()
    {
        double OLJM = operatorXboxController.getRawAxis(2);
        if((OLJM > 0.1) || (OLJM < -0.1))
        {
//            System.out.println("Operator Left Joystick Move");
        }

        return (OLJM);
    }
    
    public boolean operator_getRightJoystickMove()
    {
        if((operatorXboxController.getRawAxis(5) > 0.1) || (operatorXboxController.getRawAxis(5) < -0.1))
        {
            return true;
        }    
        
        return false;
    }    
    
    public boolean operator_getLeftJoystickMove()
    {
        if((operatorXboxController.getRawAxis(1) > 0.1) || (operatorXboxController.getRawAxis(1) < -0.1))
        {
            return true;
        }    
        
        return false;
    }
    
    public double operator_getTriggerAxis()
    {
        return (driverXboxController.getRawAxis(3));
    }
    
    public double operator_LeftTriggerAxis()
    {
        if( (operatorXboxController.getRawAxis(3)) > (0) )
        {
 //           System.out.println("Operator Left Trigger");
            return (operatorXboxController.getRawAxis(3));
        }else{
            return (0.0);
        }
    }
    
    public double operator_RightTriggerAxis()
    {
        if( (operatorXboxController.getRawAxis(3)) < (0) )
        {
 //           System.out.println("Operator Right Trigger");
            return (operatorXboxController.getRawAxis(3));
        }else{
            return (0.0);
        }                 
    }

    public boolean getOperator_LeftTrigger () 
    {
        if ( operatorXboxController.getRawAxis(3) > 0.4 ) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getOperator_RightTrigger () 
    {
        if ( operatorXboxController.getRawAxis(3) < -0.4 ) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getOperatorHorizontalDPadLeft()
    {
        if (operatorXboxController.getRawAxis(6) < 0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean getOperatorHorizontalDPadRight()
    {
        if (operatorXboxController.getRawAxis(6) > 0){
            return true;
        }else{
            return false;
        }
    }
}
