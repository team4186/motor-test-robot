package frc.motorcontrollerconfig;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;

public class SparkFlexMotorPair {

    private final SparkFlex motor;

    public SparkFlexMotorPair(SparkFlex motorLeader, SparkFlex motorFollower, SparkBaseConfig baseConfig, boolean inverse) {
        this.motor = motorLeader;

        baseConfig.inverted(inverse);

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

//    public SparkFlex getMotor() {
//        return motor;
//    }

    public RelativeEncoder getRelativeEncoder() {
        return this.motor.getEncoder();
    }

    public void accept(double value) {
        this.motor.set(value);
    }

    public void stop() {
        this.motor.stopMotor();
    }
}
