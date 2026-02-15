package frc.motorcontrollerconfig;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

public class Components
{
    private static Components instance = null;
    private final TestMotor exampleMotor = new TestMotor();

    private Components() //private constructor forces only 1 object of the class will ever exist (SINGLETON CLASS)
    {
        //EMPTY
    }

    public Components getInstance(){
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }

    private static final class TestMotor
    {
        private final SingleSparkMaxMotor testMotorSingleMotor = new SingleSparkMaxMotor(
                new SparkMax(1, SparkLowLevel.MotorType.kBrushless),
                DefaultMotorConfigs.getInstance().getDefaultConfig(),
                false //Option to invert the motor (t/f)
        );
    }

    public SingleSparkMaxMotor getTestMotor(){
        return exampleMotor.testMotorSingleMotor;
    }
}