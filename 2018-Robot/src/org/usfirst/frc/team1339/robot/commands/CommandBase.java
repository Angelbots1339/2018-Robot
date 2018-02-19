package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.OI;
import org.usfirst.frc.team1339.robot.subsystems.Chassis;
import org.usfirst.frc.team1339.robot.subsystems.Elevator;
import org.usfirst.frc.team1339.robot.subsystems.Intake;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers;
import org.usfirst.frc.team1339.robot.subsystems.Wrist;
import org.usfirst.frc.team1339.utils.LEDs;
import org.usfirst.frc.team1339.utils.Server;

import edu.wpi.first.wpilibj.command.Command;


/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static Chassis chassis;
    public static Elevator elevator;
    public static Intake intake;
    public static Wrist wrist;
    public static Pinchers pinchers;
    
    public static LEDs leds;

	public static Server server;

    public static OI oi;
    
    public static void init() {
    	chassis = new Chassis();
    	elevator = new Elevator();
    	intake = new Intake();
    	wrist = new Wrist();
    	pinchers = new Pinchers();
    	
    	leds = new LEDs();
    	
    	server = new Server(8080);

        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}