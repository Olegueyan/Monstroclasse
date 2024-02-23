package net.olegueyan.monstroclasse.integration;

import net.olegueyan.monstroclasse.motor.Motor;
import net.olegueyan.monstroclasse.motor.MotorJavaFX;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Linked to a Motor
 */
public abstract class ControllerIntegration
{
    public abstract void loadController();

    public void loadIntegrations()
    {
        MotorJavaFX motorJavaFX = new MotorJavaFX(this);
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            for (Annotation annotation : field.getAnnotations())
            {
                if (annotation.annotationType().isAnnotationPresent(Motor.class))
                {
                    Motor motorAnnotation = annotation.annotationType().getAnnotation(Motor.class);
                    motorJavaFX.addToChain(motorAnnotation, field, annotation);
                }
            }
        }
        motorJavaFX.executeAndClean();
    }
}