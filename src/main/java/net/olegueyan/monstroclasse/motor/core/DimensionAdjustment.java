package net.olegueyan.monstroclasse.motor.core;

import net.olegueyan.monstroclasse.motor.Motor;
import net.olegueyan.monstroclasse.motor.MotorPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Motor(priority = MotorPriority.VERY_HIGH)
public @interface DimensionAdjustment
{
    double wRef();
    double hRef();
    FixingType fixing() default FixingType.BOTH;
}