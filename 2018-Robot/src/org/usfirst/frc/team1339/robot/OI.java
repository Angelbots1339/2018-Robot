
package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.commands.CenterSwitchAuto;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.commands.groups.ClawClosed;
import org.usfirst.frc.team1339.robot.commands.groups.ClawMed;
import org.usfirst.frc.team1339.robot.commands.groups.ClawOpen;

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
	private JoystickButton oneButton = new JoystickButton(operatorStick, RobotMap.operatorOneButton);
	private JoystickButton twoButton = new JoystickButton(operatorStick, RobotMap.operatorTwoButton);
	private JoystickButton threeButton = new JoystickButton(operatorStick, RobotMap.operatorThreeButton);
	private JoystickButton fourButton = new JoystickButton(operatorStick, RobotMap.operatorFourButton);
	private JoystickButton fiveButton = new JoystickButton(operatorStick, RobotMap.operatorFiveButton);
	private JoystickButton sixButton = new JoystickButton(operatorStick, RobotMap.operatorSixButton);
	private JoystickButton sevenButton = new JoystickButton(operatorStick, RobotMap.operatorSevenButton);
	
	/*	XBOX B ---> Cancels profile
	 *  XBOX A ---> Follow reverse circle
	 *  XBOX Y ---> Follow path from FMS data
	 *  XBOX X ---> Follow circle path
	 *  
	 *  XBOX Right Bumper ---> Take off limiter
	 *  XBOX Left Bumper ---> Apply Limiter
	 * */
	
	public OI(){
		
		aButton.whenPressed(new PIDWrist(-82));
		xButton.whenPressed(new ClawOpen());
		yButton.whenPressed(new ClawMed());
		bButton.whenPressed(new ClawClosed());
		//rightBumper.whenPressed(new ShiftHigh());
		//leftBumper.whenPressed(new ShiftLow());
		//viewButton.whenPressed(new CenterSwitchAuto());
		viewButton.whenPressed(new PIDGyro(180, 2));
		//threeButton.whenPressed(new DriveClimber());
		//fourButton.whenPressed(new DriveElevator());
		threeButton.whenPressed(new PIDElevator(0));
		fiveButton.whenPressed(new PIDElevator(65));
		sixButton.whenPressed(new PIDElevator(170));
	}
	
	//Get Functions
	public Joystick getMadCatzStick(){
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
	public JoystickButton getOneButton(){
		return oneButton;
	}
	public JoystickButton getTwoButton(){
		return twoButton;
	}
	public JoystickButton getThreeButton(){
		return threeButton;
	}
	public JoystickButton getFourButton(){
		return fourButton;
	}
	public JoystickButton getFiveButton(){
		return fiveButton;
	}
	public JoystickButton getSixButton(){
		return sixButton;
	}
	public JoystickButton getSevenButton(){
		return sevenButton;
	}
}
