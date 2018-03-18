package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.commands.DriveWrist;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.ResetElevator;
import org.usfirst.frc.team1339.robot.commands.ResetWrist;
import org.usfirst.frc.team1339.robot.commands.ShowBottomCam;
import org.usfirst.frc.team1339.robot.commands.ShowTopCam;
import org.usfirst.frc.team1339.robot.commands.WristToggle;
import org.usfirst.frc.team1339.robot.commands.groups.ClawClosed;
import org.usfirst.frc.team1339.robot.commands.groups.ClawMed;
import org.usfirst.frc.team1339.robot.commands.groups.ClawOpen;
import org.usfirst.frc.team1339.robot.commands.groups.GoToClimber;
import org.usfirst.frc.team1339.robot.commands.groups.GoToElevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick xboxStick = new Joystick(RobotMap.xboxPort);
	private Joystick operatorStick = new Joystick(RobotMap.operatorPort);
	
	//Xbox Buttons
	private JoystickButton aButton = new JoystickButton(xboxStick, RobotMap.xboxAButton);
	private JoystickButton bButton = new JoystickButton(xboxStick, RobotMap.xboxBButton);
	private JoystickButton xButton = new JoystickButton(xboxStick, RobotMap.xboxXButton);
	private JoystickButton yButton = new JoystickButton(xboxStick, RobotMap.xboxYButton);
	private JoystickButton rightBumper = new JoystickButton(xboxStick, RobotMap.xboxRightBumper);
	private JoystickButton leftBumper = new JoystickButton(xboxStick, RobotMap.xboxLeftBumper);
	private JoystickButton viewButton = new JoystickButton(xboxStick, RobotMap.xboxViewButton);
	private JoystickButton menuButton = new JoystickButton(xboxStick, RobotMap.xboxMenuButton);
	private JoystickButton rightStickButton = new JoystickButton(xboxStick, RobotMap.xboxRightStickButton);
	private JoystickButton leftStickButton = new JoystickButton(xboxStick, RobotMap.xboxLeftStickButton);
	
	//Operator Buttons
	private JoystickButton operatorAButton = new JoystickButton(operatorStick, RobotMap.xboxAButton);
	private JoystickButton operatorBButton = new JoystickButton(operatorStick, RobotMap.xboxBButton);
	private JoystickButton operatorXButton = new JoystickButton(operatorStick, RobotMap.xboxXButton);
	private JoystickButton operatorYButton = new JoystickButton(operatorStick, RobotMap.xboxYButton);
	private JoystickButton operatorRightBumper = new JoystickButton(operatorStick, RobotMap.xboxRightBumper);
	private JoystickButton operatorLeftBumper = new JoystickButton(operatorStick, RobotMap.xboxLeftBumper);
	private JoystickButton operatorViewButton = new JoystickButton(operatorStick, RobotMap.xboxViewButton);
	private JoystickButton operatorMenuButton = new JoystickButton(operatorStick, RobotMap.xboxMenuButton);
	private JoystickButton operatorRightStickButton = new JoystickButton(operatorStick, RobotMap.xboxRightStickButton);
	private JoystickButton operatorLeftStickButton = new JoystickButton(operatorStick, RobotMap.xboxLeftStickButton);
	
	/*	XBOX B ---> Cancels profile
	 *  XBOX A ---> Follow reverse circle
	 *  XBOX Y ---> Follow path from FMS data
	 *  XBOX X ---> Follow circle path
	 *  
	 *  XBOX Right Bumper ---> Take off limiter
	 *  XBOX Left Bumper ---> Apply Limiter
	 * */
	
	public OI(){
		xButton.whenPressed(new WristToggle());
		yButton.whenPressed(new PIDElevator(RobotMap.posScale)); //scale
		bButton.whenPressed(new PIDElevator(RobotMap.posSwitch)); //switch
		aButton.whenPressed(new PIDElevator(0)); //bottom
		
		leftStickButton.whenPressed(new ClawClosed());
		
		rightStickButton.whenPressed(new ClawOpen());
		rightStickButton.whenReleased(new ClawMed());
	
		viewButton.whenPressed(new ResetWrist());
		menuButton.whenPressed(new ResetElevator());
		
		operatorYButton.whenPressed(new GoToElevator());
		operatorAButton.whenPressed(new GoToClimber());
		operatorAButton.whenPressed(new DriveWrist());
		operatorRightBumper.whenPressed(new ShowTopCam());
		operatorLeftBumper.whenPressed(new ShowBottomCam());
	}
	
	//Get Functions
	public Joystick getOperatorStick(){
		return operatorStick;
	}
	public Joystick getXboxStick(){
		return xboxStick;
	}
	public JoystickButton getAButton(){
		return aButton;
	}
	public JoystickButton getBButton(){
		return bButton;
	}
	public JoystickButton getXButton(){
		return xButton;
	}
	public JoystickButton getYButton(){
		return yButton;
	}
	public JoystickButton getLeftBumper(){
		return leftBumper;
	}
	public JoystickButton getRightBumper(){
		return rightBumper;
	}
	public JoystickButton getLeftStickButton(){
		return leftStickButton;
	}
	public JoystickButton getRightStickButton(){
		return rightStickButton;
	}
	public JoystickButton getviewButton(){
		return viewButton;
	}
	public JoystickButton getMenuButton(){
		return menuButton;
	}
	public JoystickButton getOperatorAButton(){
		return operatorAButton;
	}
	public JoystickButton getOperatorBButton(){
		return operatorBButton;
	}
	public JoystickButton getOperatorXButton(){
		return operatorXButton;
	}
	public JoystickButton getOperatorYButton(){
		return operatorYButton;
	}
	public JoystickButton getOperatorLeftBumper(){
		return operatorLeftBumper;
	}
	public JoystickButton getOperatorRightBumper(){
		return operatorRightBumper;
	}
	public JoystickButton getOperatorLeftStickButton(){
		return operatorLeftStickButton;
	}
	public JoystickButton getOperatorRightStickButton(){
		return operatorRightStickButton;
	}
	public JoystickButton getOperatorViewButton(){
		return operatorViewButton;
	}
	public JoystickButton getOperatorMenuButton(){
		return operatorMenuButton;
	}
}