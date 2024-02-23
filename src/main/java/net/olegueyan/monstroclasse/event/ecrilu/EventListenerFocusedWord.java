package net.olegueyan.monstroclasse.event.ecrilu;

import net.olegueyan.monstroclasse.json.ecrilu.EcriluWordJson;

import java.util.ArrayList;

/**
 * EventListener that captures word creation | update | deletion.
 */
public interface EventListenerFocusedWord
{
    // ------- Called when a word is created ------- //
    default void onWordCreated(EcriluWordJson word)
    {
        // Not implemented by default
    }
    // --------------------------------------------- //

    // ------- Called when a word is updated ------- //
    default void onWordUpdated(EcriluWordJson oldWord, EcriluWordJson newWord)
    {
        // Not implemented by default
    }
    // --------------------------------------------- //

    // ------- Called when a word is deleted ------- //
    default void onWordDeleted(EcriluWordJson word)
    {
        // Not implemented by default
    }
    // --------------------------------------------- //

    // ------- All registered EventListener ------- //
    ArrayList<EventListenerFocusedWord> events = new ArrayList<>();
    // -------------------------------------------- //

    // ------- Call "onWordCreated" of all EventListener registered ------- //
    static void callWordCreated(EcriluWordJson word)
    {
        events.forEach(eventListenerFocusedWord -> eventListenerFocusedWord.onWordCreated(word));
    }
    // -------------------------------------------------------------------- //

    // ------- Call "onWordUpdated" of all EventListener registered ------- //
    static void callWordUpdated(EcriluWordJson oldWord, EcriluWordJson newWord)
    {
        events.forEach(eventListenerFocusedWord -> eventListenerFocusedWord.onWordUpdated(oldWord, newWord));
    }
    // -------------------------------------------------------------------- //

    // ------- Call "onWordDeleted" of all EventListener registered ------- //
    static void callWordDeleted(EcriluWordJson word)
    {
        events.forEach(eventListenerFocusedWord -> eventListenerFocusedWord.onWordDeleted(word));
    }
    // -------------------------------------------------------------------- //
}