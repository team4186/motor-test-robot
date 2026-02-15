package frc.motorcontrollerconfig;

import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.Constants;


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

    public SparkBaseConfig getDefaultSparkMaxConfig()
    {
        return DefaultSparkMaxConfig;
    }


    public SparkBaseConfig getDefaultSparkFlexConfig() { return DefaultSparkFlexConfig; }


    public final SparkBaseConfig DefaultSparkMaxConfig = new SparkMaxConfig()
            .smartCurrentLimit(50) // NEO 550
            .idleMode(SparkBaseConfig.IdleMode.kCoast);

    public final SparkBaseConfig DefaultSparkFlexConfig = new SparkFlexConfig()
            .smartCurrentLimit(80) // NEO VORTEX
            .idleMode(SparkBaseConfig.IdleMode.kCoast);
    // .idleMode(SparkBaseConfig.IdleMode.kCoast);
}
