package net.olegueyan.monstroclasse.motor;

import javafx.scene.Node;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public record MotorConsumer<T extends Node, A extends Annotation>(ControllerIntegration controllerIntegration, T field, A annotation)
{
    /**
     * Get a field from a class thanks to its name and cast him to a specified type
     * @param fieldName name of the field inside the class
     * @return F cast field
     */
    public <F> F getFromAndForceCast(Class<F> clazz, String fieldName)
    {
        try
        {
            return clazz.cast(controllerIntegration.getClass().getField(fieldName).get(controllerIntegration));
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
}