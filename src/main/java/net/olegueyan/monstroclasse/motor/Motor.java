package net.olegueyan.monstroclasse.motor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation annotated with motor means that is can be used on JavaFX fields to perform something
 * Need to be coded inside MotorJavaFX
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Motor
{
    MotorPriority priority();
}