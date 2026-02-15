package frc.motorcontrollerconfig;

import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants;


// MotorConfigs Singleton for Subsystem Motors (Swerve Subsystem not included)
public final class DefaultMotorConfigs {
    private static DefaultMotorConfigs instance = null;


    private DefaultMotorConfigs() { }


    public static DefaultMotorConfigs getInstance() {
        if (instance == null) {
            instance = new DefaultMotorConfigs();
        }
        return instance;
    }

    public SparkMaxConfig getDefaultSparkMaxConfig()
    {
        return DefaultSparkMaxConfig;
    }


    public SparkFlexConfig getDefaultSparkFlexConfig() { return DefaultSparkFlexConfig; }


    public final SparkMaxConfig DefaultSparkMaxConfig = (SparkMaxConfig) new SparkMaxConfig()
            .smartCurrentLimit(50)
            .idleMode(SparkBaseConfig.IdleMode.kCoast);

    public final SparkFlexConfig DefaultSparkFlexConfig = (SparkFlexConfig) new SparkFlexConfig()
            .smartCurrentLimit(50)
            .idleMode(SparkBaseConfig.IdleMode.kBrake);
    // .idleMode(SparkBaseConfig.IdleMode.kCoast);
}
