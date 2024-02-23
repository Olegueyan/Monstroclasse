package net.olegueyan.monstroclasse.event;

public interface SqlEventListener
{
    // ------- Event called when SQL Operation is performed ------- //
    default void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {

    }
    // ------------------------------------------------------------ //
}