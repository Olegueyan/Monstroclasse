package net.olegueyan.monstroclasse.motor;

import javafx.scene.Node;
import net.olegueyan.monstroclasse.integration.ControllerIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Consumer;

public record MotorPart<T extends Node, A extends Annotation>(Class<T> applicableTo, Class<A> annotator, Consumer<MotorConsumer<T, A>> apply)
{
    // ------- Logger of MotorPart ------- //
    private static final Logger LOGGER = LoggerFactory.getLogger(MotorPart.class);
    // ----------------------------------- //

    /**
     * Force the motorization of field
     * @param controllerIntegration Linked controller to MotorJavaFX
     * @param field Field to apply motorization
     * @param annotation Annotation to add some parameters to motorisation
     */
    public void forceApply(ControllerIntegration controllerIntegration, Field field, Annotation annotation)
    {
        try
        {
            field.setAccessible(true);
            LOGGER.debug("Motor configuration : " + field + " / " + annotation.annotationType().getName());
            this.apply.accept(new MotorConsumer<>(controllerIntegration, applicableTo.cast(field.get(controllerIntegration)), annotator.cast(annotation)));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}