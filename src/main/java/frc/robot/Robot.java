// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.motorcontrollerconfig.Components;
import frc.motorcontrollerconfig.SingleSparkMaxMotor;
import frc.motorcontrollerconfig.SparkFlexMotorPair;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  private final Components motorComponents = Components.getInstance();
  private final SingleSparkMaxMotor sparkMaxMotor = motorComponents.getTestMotor();
  private final RelativeEncoder sparkMaxEncoder = motorComponents.getTestMotor().getRelativeEncoder();
  private final SparkClosedLoopController turretClosedLoopController = motorComponents.getTestMotor().getClosedLoopController();

  private final SparkFlexMotorPair sparkFlexMotorPair = motorComponents.getTestFlexMotorPair();
  private final RelativeEncoder sparkFlexEncoder = sparkFlexMotorPair.getRelativeEncoder();
  private final SparkClosedLoopController shooterClosedLoopController = motorComponents.getTestMotor().getClosedLoopController();


  private final CommandJoystick joystickDriver = new CommandJoystick(0);
  private final CommandJoystick joystickSupport = new CommandJoystick(1);


  private double voltsPairTesting = 0.0;
  private double voltsSingleTesting = 0.0;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Position: ", sparkFlexEncoder.getPosition());
    SmartDashboard.putNumber("Motor RPM", sparkFlexEncoder.getVelocity());

    SmartDashboard.putNumber("Turret Position", sparkMaxEncoder.getPosition());
    SmartDashboard.putNumber("Turret RPM", sparkMaxEncoder.getVelocity() );

    SmartDashboard.putNumber("VoltsSingle", voltsSingleTesting);
    SmartDashboard.putNumber("VoltsPair", voltsPairTesting);

//    SmartDashboard.setDefaultNumber("Target Position", 0);
//    SmartDashboard.setDefaultNumber("Target Velocity", 0);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // target is 78% of max speed 5800 rpm
    double requestDriver = attenuated( joystickDriver.getX(), 2, 0.78 );
    double requestSupport = attenuated( joystickSupport.getX(), 2, 0.2);
    sparkFlexMotorPair.accept( requestDriver );
    sparkMaxMotor.accept( requestSupport );
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    voltsSingleTesting = 0.0;
    voltsPairTesting = 0.0;
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

    // A Simple example of setting a
    boolean buttonpress = joystickDriver.trigger( ).getAsBoolean();

    // TESTING: target Velocity and Target Position for closed loop controllers
    // double targetVelocity = SmartDashboard.getNumber("Target Velocity", 0);
    double targetVelocity = (buttonpress) ? 5200 : 0; // if true tV = 5200 else 0
    shooterClosedLoopController.setSetpoint(targetVelocity, ControlType.kVelocity, ClosedLoopSlot.kSlot1);


    double targetPosition = SmartDashboard.getNumber("Target Position", 0);
    //double targetPosition = (buttonpress) ? 90 : 0; // if true tP = 90 else 0 (also assuming position is set to degrees)
    turretClosedLoopController.setSetpoint(targetPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0);


    // TESTING: With Advantage and SmartDashboard to track the velocity and voltage values, we can obtain the number of
    // volts required to break static friction and obtain kS by taking the value just before the velocity changes for the system.

//    voltsSingle += 0.01;
//    sparkMaxMotor.acceptVoltage( voltsSingle ); // kS turret = 0.24 Volts
//    voltsPair += 0.01;
//    sparkFlexMotorPair.acceptVoltage( voltsPair ); // ks Shooter = 0.10 Volts
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}


  private double attenuated(double value, int exponent, double scale){
    double result = scale * Math.pow( Math.abs(value), exponent);
    if (value < 0){ result *= -1; }

    return result;
  }
}


