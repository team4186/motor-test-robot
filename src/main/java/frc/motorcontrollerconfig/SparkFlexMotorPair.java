package frc.motorcontrollerconfig;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.units.measure.Voltage;
import frc.Constants.ShooterConstants;
import frc.Constants.TurretConstants;


public class SparkFlexMotorPair {


    private final SparkFlex motor;


    public SparkFlexMotorPair(
            SparkFlex motorLeader,
            SparkFlex motorFollower,
            SparkBaseConfig baseConfig,
            double posConversionFactor,
            double velConversionFactor,
            boolean inverse
    ) {
        this.motor = motorLeader;

        baseConfig.inverted(inverse);

        // Using Velocity
        baseConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                // Set PID values for position control. We don't need to pass a closed loop
                // slot, as it will default to slot 0.
                .p(0.0)
                .i(0.0)
                .d(0.0)
                .outputRange(-1, 1)
                // Set PID values for velocity control in slot 1
                .p(0.0001, ClosedLoopSlot.kSlot1)
                .i(0, ClosedLoopSlot.kSlot1)
                .d(0, ClosedLoopSlot.kSlot1)
                .outputRange(-1, 1, ClosedLoopSlot.kSlot1)
                .feedForward
                // kV is now in Volts, so we multiply by the nominal voltage (12V)
                .kV(12.0 / 5200, ClosedLoopSlot.kSlot1); // Nominal * desired velocity

        baseConfig.encoder
                .positionConversionFactor(posConversionFactor)
                .velocityConversionFactor(velConversionFactor);


        motorLeader.configure(
                baseConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters
        );

        SparkBaseConfig followerConfig = new SparkFlexConfig();

        followerConfig
                .apply(baseConfig)
                .follow(motorLeader, !inverse);

        motorFollower.configure(
                followerConfig,
                SparkBase.ResetMode.kNoResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);
    }


    public RelativeEncoder getRelativeEncoder() {
        return this.motor.getEncoder();
    }


    public SparkClosedLoopController getClosedLoopController(){
        return this.motor.getClosedLoopController();
    }


    public void accept(double value) {
        this.motor.set(value);
    }


    public void acceptVoltage(double volts) { this.motor.setVoltage(volts); }


    public void stop() {
        this.motor.stopMotor();
    }
}
