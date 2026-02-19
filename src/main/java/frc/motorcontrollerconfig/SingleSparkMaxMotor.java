package frc.motorcontrollerconfig;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.SparkBaseConfig;
import edu.wpi.first.units.measure.Voltage;


public class SingleSparkMaxMotor {
    private final SparkMax motor;


    public SingleSparkMaxMotor(
            SparkMax motor,
            SparkBaseConfig baseConfig,
            double posConversionFactor,
            double velConversionFactor,
            boolean inverse
    ) {

        baseConfig.inverted(inverse);

        baseConfig.encoder
                .positionConversionFactor(posConversionFactor)
                .velocityConversionFactor(velConversionFactor);

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
                .kV(12.0 / 5200, ClosedLoopSlot.kSlot1); // Niminal * velocity


        motor.configure(
                baseConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters
        );

        this.motor = motor;
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
