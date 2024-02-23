package net.olegueyan.monstroclasse.service;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Service for the database
 */
public final class DatabaseService
{
    // ***************************************************************
    // DatabaseService - Attributes # STATIC
    // ***************************************************************

    // Path of the Database (Root/data/monstroclasse.db)
    private static final String databaseUrl = "jdbc:sqlite:" + DataService.pathOf("monstroclasse.db");

    // ConnectionSource for ORMLite
    private static ConnectionSource connectionSource;

    // ***************************************************************
    // DatabaseService - Methods # STATIC
    // ***************************************************************

    /**
     * Initialize the connection to the database
     */
    public static void initialization() {
        try
        {
            // Create the connection source
            connectionSource = new JdbcConnectionSource(databaseUrl);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get the connection source of the database
     *
     * @return ConnectionSource
     */
    public static ConnectionSource getConnectionSource()
    {
        return connectionSource;
    }

    // ***************************************************************
    // DatabaseService - Methods # STATIC
    // ***************************************************************
}