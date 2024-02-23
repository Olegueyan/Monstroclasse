package net.olegueyan.monstroclasse.event.ecrilu;

import net.olegueyan.monstroclasse.json.ecrilu.EcriluSequenceJson;

import java.util.ArrayList;

/**
 * EventListener that captures sequence creation | update | suppression
 */
public interface EventListenerFocusedSequence
{
    // ------- Called when a sequence is created ------- //
    default void onSequenceCreated(EcriluSequenceJson sequence)
    {
        // Not implemented by default
    }
    // ------------------------------------------------- //

    // ------- Called when a sequence is updated ------- //
    default void onSequenceUpdated(EcriluSequenceJson oldSequence, EcriluSequenceJson newSequence)
    {
        // Not implemented by default
    }
    // ------------------------------------------------- //

    // ------- Called when a sequence is deleted ------- //
    default void onSequenceDeleted(EcriluSequenceJson sequence)
    {
        // Not implemented by default
    }
    // ------------------------------------------------- //

    // ------- All registered EventListener ------- //
    ArrayList<EventListenerFocusedSequence> events = new ArrayList<>();
    // -------------------------------------------- //

    // ------- Call "onSequenceCreated" of all EventListener registered ------- //
    static void callSequenceCreated(EcriluSequenceJson sequence)
    {
        events.forEach(eventListenerFocusedSequence -> eventListenerFocusedSequence.onSequenceCreated(sequence));
    }
    // ------------------------------------------------------------------------ //

    // ------- Call "onSequenceUpdated" of all EventListener registered ------- //
    static void callSequenceUpdated(EcriluSequenceJson oldSequence, EcriluSequenceJson newSequence)
    {
        events.forEach(eventListenerFocusedSequence -> eventListenerFocusedSequence.onSequenceUpdated(oldSequence, newSequence));
    }
    // ------------------------------------------------------------------------ //

    // ------- Call "onSequenceDeleted" of all EventListener registered ------- //
    static void callSequenceDeleted(EcriluSequenceJson sequence)
    {
        events.forEach(eventListenerFocusedSequence -> eventListenerFocusedSequence.onSequenceDeleted(sequence));
    }
    // ------------------------------------------------------------------------ //
}