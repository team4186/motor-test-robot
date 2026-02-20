package frc.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.vision.LimelightRunner;
import frc.motors.Components;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    // Controllers
    final CommandJoystick joystickDriver = new CommandJoystick(0);
    final CommandJoystick joystickOperator = new CommandJoystick(1);

    // Components and Helper Systems
    private final Components motorComponents = Components.getInstance();
    private final LimelightRunner visionSubsystem = new LimelightRunner();

    // Robot Subsystems



    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
        DriverStation.silenceJoystickConnectionWarning(true);
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
     * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
     * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
     */
    private void configureBindings() {
        // Configure controller assignments
        if ( Robot.isSimulation() ){

        }

        // Test
        if ( DriverStation.isTest() ) {

        } else {

        }
    }

//    public void updateDriverAllianceControls(){
//        var alliance = DriverStation.getAlliance();
//        if( alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red ) { }
//    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
//    public Command getAutonomousCommand() {
//        // Create an InstantCommand that runs the drive forward at half speed for arcade drive
//        // Command driveHalfSpeed = runOnce(() -> drivetrain.arcadeDrive(0.5, 0.0), drivetrain); // arcadeDrive example
//        // return drivebase.driveToDistanceCommand(10,0.5); // swerve example
//
//        Command driveHalfSpeed = runOnce(() -> drivetrain.arcadeDrive(0.5, 0.0), drivetrain);
//
//        return driveHalfSpeed;
//    }


    // Adjust joystick input from linear to exponential curve
    private double attenuated(double value, int exponent, double scale) {
        double res = scale * Math.pow( Math.abs(value), exponent );
        if ( value < 0 ) { res *= -1; }
        return res;
    }
}
