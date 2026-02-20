package frc;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig;

public final class Constants{
    public static final class RobotConstants {
        public static final double NOMINAL_VOLTAGE = 12.0;
    }

    public static final class TurretConstants {
        // PID
        public static final double TURRET_P = 0.0;
        public static final double TURRET_I = 0.0;
        public static final double TURRET_D = 0.0;

        // FeedForward
        public static final double TURRET_KS = 0.24;
        public static final double TURRET_KV = 0.0;
        public static final ControlType CLOSED_LOOP_CONTROL_MODE = ControlType.kPosition;
        public static final int MOTOR_CURRENT_LIMIT = 50; // NEO 550
        public static final double POSITION_CONVERSION_FACTOR = 18.0; //  (1/Gear_Ratio) * 360 --- (1.0 / 20.0) * 360.0
        public static final double VELOCITY_CONVERSION_FACTOR = 1.0;
        public static final double MOTOR_FREE_SPEED = 11000;
        public static final double MIN_OUTPUT = -0.5;
        public static final double MAX_OUTPUT = 0.5;
        public static final SparkBaseConfig.IdleMode IDLE_MODE = SparkBaseConfig.IdleMode.kBrake;
    }

    public static final class ShooterConstants {
        // PID
        public static final double SHOOTER_P = 0.0;
        public static final double SHOOTER_I = 0.0;
        public static final double SHOOTER_D = 0.0;

        // FeedForward
        public static final double SHOOTER_KS = 0.10;
        public static final double SHOOTER_KV = 0.0;

        // CLOSED LOOP CONTROLLER
        public static final ControlType CONTROL_TYPE = ControlType.kVelocity;
        public static final int MOTOR_CURRENT_LIMIT = 80; // NEO VORTEX
        public static final double MOTOR_FREE_SPEED = 6784.0;
        public static final double POSITION_CONVERSION_FACTOR = 1.0;
        public static final double VELOCITY_CONVERSION_FACTOR = 1.0;
        public static final double MIN_OUTPUT = -1.0;
        public static final double MAX_OUTPUT = 1.0;
        public static final SparkBaseConfig.IdleMode IDLE_MODE = SparkBaseConfig.IdleMode.kCoast;
    }
}
