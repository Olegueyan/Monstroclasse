package net.olegueyan.monstroclasse.motor.core;

import net.olegueyan.monstroclasse.motor.Motor;
import net.olegueyan.monstroclasse.motor.MotorPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Work only on Type "Pane" with "Pane" with "Label" \\
// Label inside a Pane inside a Pane

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Motor(priority = MotorPriority.VERY_LOW)
public @interface StylizedButtonBuild
{
    String backgroundHex();
    String foregroundHex();
    double widthRef();
    double heightRef();
    double fontRef();
}