package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.OI;
import org.usfirst.frc.team1339.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;


/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    
    public static Chassis chassis;
    
    public static ElevatorSystem elevatorsystem;
    
    public static void init() {
    	elevatorsystem = new ElevatorSystem();
    	
    	chassis = new Chassis();
        
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}