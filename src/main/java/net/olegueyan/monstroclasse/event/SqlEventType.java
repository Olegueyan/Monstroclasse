package net.olegueyan.monstroclasse.event;

/**
 * All kinds of SQL event -> INSERT | DELETE | UPDATE
 * Doesn't manage POST / BEFORE *
 */
public enum SqlEventType
{
    INSERT,
    DELETE,
    UPDATE
}