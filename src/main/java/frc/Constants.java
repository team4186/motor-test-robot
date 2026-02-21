package frc;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig;

public final class Constants{
    public static final class RobotConstants {
        public static final double NOMINAL_VOLTAGE = 12.0;
    }

    public static final class TurretConstants {
        // NEO 550
        public static final int MOTOR_CURRENT_LIMIT = 50;
        public static final double MOTOR_FREE_SPEED = 11000;
        public static final SparkBaseConfig.IdleMode IDLE_MODE = SparkBaseConfig.IdleMode.kBrake;
        public static final double GEAR_RATIO = 1.0/20.0;

        // PID
        public static final double TURRET_P = 0.0075;
        public static final double TURRET_I = 0.0;
        public static final double TURRET_D = 0.002;

        // FeedForward
        public static final double TURRET_KS = 0.185;
        public static final double TURRET_KV = RobotConstants.NOMINAL_VOLTAGE / TurretConstants.MOTOR_FREE_SPEED;

        public static final double POSITION_CONVERSION_FACTOR = (1/GEAR_RATIO) * 360; // Convert to degrees
        public static final double VELOCITY_CONVERSION_FACTOR = 1.0;
        public static final double MIN_OUTPUT = -0.75;
        public static final double MAX_OUTPUT = 0.75;
    }

    public static final class ShooterConstants {
        // NEO Vortex
        public static final SparkBaseConfig.IdleMode IDLE_MODE = SparkBaseConfig.IdleMode.kCoast;
        public static final int MOTOR_CURRENT_LIMIT = 80; // NEO VORTEX
        public static final double MOTOR_FREE_SPEED = 6784.0;

        // PID
        public static final double SHOOTER_P = 0.001;
        public static final double SHOOTER_I = 0.0;
        public static final double SHOOTER_D = 0.0;

        // FeedForward
        public static final double SHOOTER_KS = 0.10;
        public static final double SHOOTER_KV = RobotConstants.NOMINAL_VOLTAGE / ShooterConstants.MOTOR_FREE_SPEED;

        // CLOSED LOOP CONTROLLER
        public static final ControlType CONTROL_TYPE = ControlType.kVelocity;
        public static final double POSITION_CONVERSION_FACTOR = 1.0;
        public static final double VELOCITY_CONVERSION_FACTOR = 1.0;
        public static final double MIN_OUTPUT = -0.90;
        public static final double MAX_OUTPUT = 0.90;

        // Improving Velocity Based Control
        public static final int AVERAGE_DEPTH = 5; // 5 Sample Count
        public static final int MEASUREMENT_PERIOD = 1; // 1ms Moving Avg Window
    }
}
