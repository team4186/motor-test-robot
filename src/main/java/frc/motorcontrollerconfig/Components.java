package frc.motorcontrollerconfig;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Components
{
    private static Components instance = null;
    private final TestMotor exampleMotor = new TestMotor();
    private final TestFlexMotorPair exampleMotorPair = new TestFlexMotorPair();

    private Components() //private constructor forces only 1 object of the class will ever exist (SINGLETON CLASS)
    {
        //EMPTY
    }

    public static Components getInstance(){
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }

    private static final class TestMotor
    {
        private final SingleSparkMaxMotor testMotorSingleMotor = new SingleSparkMaxMotor(
                new SparkMax(41, SparkLowLevel.MotorType.kBrushless),
                DefaultMotorConfigs.getInstance().getDefaultSparkMaxConfig(),
                1.0,
                1.0,
                false //Option to invert the motor (t/f)
        );
    }

    private static final class TestFlexMotorPair {
        private final SparkFlexMotorPair motorPair = new SparkFlexMotorPair(
                new SparkFlex(21, SparkLowLevel.MotorType.kBrushless),
                new SparkFlex(22, SparkLowLevel.MotorType.kBrushless),
                DefaultMotorConfigs.getInstance().getDefaultSparkFlexConfig(),
                1.0,
                1.0,
                false
        );
    }

    public SingleSparkMaxMotor getTestMotor(){
        return exampleMotor.testMotorSingleMotor;
    }

    public SparkFlexMotorPair getTestFlexMotorPair(){
        return exampleMotorPair.motorPair;
    }
}