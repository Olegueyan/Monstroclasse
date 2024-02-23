package net.olegueyan.monstroclasse.event;

import java.util.ArrayList;

/**
 * This class is a registerer of SqlEventListener
 */
public final class SqlEventHubAdapter
{
    // ------- make SqlEventHubAdapter a Singleton ------- //
    private static final SqlEventHubAdapter INSTANCE = new SqlEventHubAdapter();
    // --------------------------------------------------- //

    // ------- All registered EventListeners ------- //
    private final ArrayList<SqlEventListener> sqlEventListeners = new ArrayList<>();
    // --------------------------------------------- //

    /**
     * Call all "onSqlPerform" of all registered SqlEventListeners
     * @param entities entities affected
     * @param sqlEventType INSERT | UPDATE | DELETE
     */
    public void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {
        sqlEventListeners.forEach(sqlEventListener -> sqlEventListener.onSqlPerform(entities, sqlEventType));
    }

    /**
     * Add new SqlEventListener to handle
     * @param sqlEventListener listener
     */
    public void addSdlEventListener(SqlEventListener sqlEventListener)
    {
        this.sqlEventListeners.add(sqlEventListener);
    }

    /**
     * Singleton
     * @return SqlEventHubAdapter
     */
    public static SqlEventHubAdapter getInstance()
    {
        return INSTANCE;
    }
}