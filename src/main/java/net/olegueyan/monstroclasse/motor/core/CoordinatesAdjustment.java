package net.olegueyan.monstroclasse.motor.core;

import net.olegueyan.monstroclasse.motor.Motor;
import net.olegueyan.monstroclasse.motor.MotorPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Work only on Type "Node" (all) \\

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Motor(priority = MotorPriority.HIGH)
public @interface CoordinatesAdjustment
{
    double xRef();
    double yRef();
}