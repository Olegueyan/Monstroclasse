package net.olegueyan.monstroclasse.event.ecrilu;

import net.olegueyan.monstroclasse.json.ecrilu.EcriluExerciseJson;

import java.util.ArrayList;

/**
 * EventListener that captures exercise creation | update | suppression
 */
public interface EventListenerFocusedExercise
{
    // ------- Called when an exercise is created ------- //
    default void onExerciseCreated(EcriluExerciseJson exercise)
    {
        // Not implemented by default
    }
    // -------------------------------------------------- //

    // ------- Called when an exercise is updated ------- //
    default void onExerciseUpdated(EcriluExerciseJson oldExercise, EcriluExerciseJson newExercise)
    {
        // Not implemented by default
    }
    // -------------------------------------------------- //

    // ------- Called when an exercise is deleted ------- //
    default void onExerciseDeleted(EcriluExerciseJson exercise)
    {
        // Not implemented by default
    }
    // -------------------------------------------------- //

    // ------- All registered EventListener ------- //
    ArrayList<EventListenerFocusedExercise> events = new ArrayList<>();
    // -------------------------------------------- //

    // ------- Call "onExerciseCreated" of all EventListener registered ------- //
    static void callExerciseCreated(EcriluExerciseJson exercise)
    {
        events.forEach(eventListenerFocusedExercise -> eventListenerFocusedExercise.onExerciseCreated(exercise));
    }
    // ------------------------------------------------------------------------ //

    // ------- Call "onExerciseUpdated" of all EventListener registered ------- //
    static void callExerciseUpdated(EcriluExerciseJson oldExercise, EcriluExerciseJson newExercise)
    {
        events.forEach(eventListenerFocusedExercise -> eventListenerFocusedExercise.onExerciseUpdated(oldExercise, newExercise));
    }
    // ------------------------------------------------------------------------ //

    // ------- Call "onExerciseDeleted" of all EventListener registered ------- //
    static void callExerciseDeleted(EcriluExerciseJson exercise)
    {
        events.forEach(eventListenerFocusedExercise -> eventListenerFocusedExercise.onExerciseDeleted(exercise));
    }
    // ------------------------------------------------------------------------ //
}