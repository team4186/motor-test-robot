package frc.robot;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.commands.actions.SampleCommand;
import frc.subsystems.ElevatorTemplate;
import frc.subsystems.TurretTemplate;
import frc.vision.LimelightRunner;
import frc.motors.Components;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    // Controllers
    private final CommandJoystick joystickDriver = new CommandJoystick(0);
    private final CommandJoystick joystickSupport = new CommandJoystick(1);


    // Components and Helper Systems
    private final Components motorComponents = Components.getInstance();
    private final LimelightRunner visionSubsystem = new LimelightRunner();


    // Robot Subsystems
    private final TurretTemplate turret = new TurretTemplate(
        // limit switches (DigitalInput)
        // motor (SparkMax or SparkFlex Obj)
        // encoders (Non-Integrated Encoders, Absolute or thru-bore)
        // ProfiledPIDController (Without )
    );

    private final ElevatorTemplate elevator = new ElevatorTemplate();


    // Commands
    SampleCommand sampleCommand = new SampleCommand(
        // required subsystems
        // other systems, vision, ect.
        // adjustable
        // PID Controller
    );


    // Simple motor setup testing
    private final SparkMax turretMotor = motorComponents.getTurretMotor();
    private final RelativeEncoder turretEncoder = turretMotor.getEncoder();
    private final SparkClosedLoopController turretCLController = turretMotor.getClosedLoopController();

    private final SparkFlex shooterMotor = motorComponents.getShooterMotor();
    private final RelativeEncoder shooterEncoder = shooterMotor.getEncoder();
    private final SparkClosedLoopController shooterClosedLoopController = shooterMotor.getClosedLoopController();

    private final int[] turretPositionPresets = { -135, -90,-45, 0,  45, 90, 135};
    private final int[] shooterVelocityPresets = { 0, 2500, 4000, 5200, 5800};

    private int goalPosition = 0;
    private int goalVelocity = 0;


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
        DriverStation.silenceJoystickConnectionWarning(true);
        // NamedCommands.registerCommand("test", Commands.print("I EXIST")); // PathPlanner
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
     * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
     * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
     */
    private void configureBindings() {
        // Generic Command Setup
        // Command defaultDriveCommand = sampleCommand;

        // Set Default Subsystem Commands
        // drivebase.setDefaultCommand( defaultDriveCommand );

        // Simulation Controller assignments
        if ( Robot.isSimulation() ){
            // Setting new pose in sim with Yagsl Swerve
//            joystickDriver.button(3).onTrue(
//                Commands.runOnce(
//                    () -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
        }

        // Test and Non-test Controller assignments
        if ( DriverStation.isTest() ) {
            joystickDriver.button(1).onTrue(Commands.runOnce(() -> updateGoalPosition(0)));
            joystickDriver.button(2).onTrue(Commands.runOnce(() -> updateGoalPosition(1)));
            joystickDriver.button(3).onTrue(Commands.runOnce(() -> updateGoalPosition(2)));
            joystickDriver.button(4).onTrue(Commands.runOnce(() -> updateGoalPosition(3)));
            joystickDriver.button(5).onTrue(Commands.runOnce(() -> updateGoalPosition(4)));


            joystickSupport.button(1).onTrue(Commands.runOnce(() -> updateGoalVelocity(0)));

        } else  {
        }
    }


    public void updateDriverAllianceControls(){
        var alliance = DriverStation.getAlliance();
        if ( alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red ) {
            // Invert X and Y inputs for field oriented control
        }
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // Create an InstantCommand that runs the drive forward at half speed for arcade drive
        // Command driveHalfSpeed = runOnce(() -> drivetrain.arcadeDrive(0.5, 0.0), drivetrain); // arcadeDrive example
        // return drivebase.driveToDistanceCommand(10,0.5); // swerve example

        // Command driveHalfSpeed = runOnce(() -> drivetrain.arcadeDrive(0.5, 0.0), drivetrain);

        return Commands.none();
    }


    /**
     * Use this to smooth controller inputs from a linear to an exponential curve.
     *
     * @return the adjusted value
     */
    private double attenuated(double value, int exponent, double scale){
        double result = scale * Math.pow( Math.abs(value), exponent);
        if (value < 0){ result *= -1; }
        return result;
    }


    /**
     * Only use this to track and publish important values in combination with basicMotorTrialsPeriodic()
     *      using robotPeriodic {@link Robot}.
     */
    public void motorTrialsPeriodicValues(){
        SmartDashboard.putNumber("Goal Position", goalPosition );
        SmartDashboard.putNumber("Shooter_Position", shooterEncoder.getPosition());
        SmartDashboard.putNumber("Shooter_RPM", shooterEncoder.getVelocity());


        SmartDashboard.putNumber("Goal Velocity", goalVelocity);
        SmartDashboard.putNumber("Turret_Position", turretEncoder.getPosition());
        SmartDashboard.putNumber("Turret_RPM", turretEncoder.getVelocity() );
    }


    /**
     * Only use this to run periodic for Simple motor setups in testPeriodic {@link Robot}.
     */
    public void basicMotorTrialsPeriodic(){
        // Manually move motors
        // target is 78% of max speed 5800 rpm
//        double requestDriver = attenuated( joystickDriver.getX(), 2, 0.78 );
//        double requestSupport = attenuated( joystickSupport.getX(), 2, 0.2);
//        shooterMotor.set( requestDriver );
//        turretMotor.set( requestSupport );


        // Updated in test controller inputs
        turretCLController.setSetpoint(goalPosition, SparkBase.ControlType.kPosition, ClosedLoopSlot.kSlot0);
        shooterClosedLoopController.setSetpoint(goalVelocity, SparkBase.ControlType.kVelocity, ClosedLoopSlot.kSlot1);


        // SYSTEM TESTING: With Advantage and SmartDashboard to track the velocity and voltage values, we can obtain the
        // number of volts required to break static friction and obtain kS by taking the value just before the velocity
        // changes for the system. For Elevator, you need positive and negative precise control over Voltage.
        //      voltsSystemTest += 0.01;
        //      sparkMaxMotor.acceptVoltage( voltsSystemTest ); // kS turret = 0.24 Volts
        //      sparkFlexMotorPair.acceptVoltage( voltsSystemTest ); // ks Shooter = 0.10 Volts
    }


    private void updateGoalPosition(int i) {
        try {
            goalPosition = turretPositionPresets[i];;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            goalPosition = 0;
        }
    }


    private void updateGoalVelocity(int i) {
        try {
            goalVelocity = shooterVelocityPresets[i];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            goalVelocity = 0;
        }
    }
}
