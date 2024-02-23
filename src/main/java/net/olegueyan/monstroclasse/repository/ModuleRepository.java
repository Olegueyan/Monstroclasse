package net.olegueyan.monstroclasse.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.event.SqlEventHubAdapter;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.SQLException;

/**
 * REPOSITORY |-> ModuleRepository | Module
 */
public final class ModuleRepository
{
    private static Dao<Module, Integer> moduleDao;

    static
    {
        try
        {
            ConnectionSource connectionSource = DatabaseService.getConnectionSource();
            moduleDao = DaoManager.createDao(connectionSource, Module.class);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // ModuleRepository - METHODS
    // ***************************************************************

    /**
     * Create a module into the database
     *
     * @param module entity
     */
    public static void create(Module module)
    {
        try
        {
            moduleDao.create(module);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{module}, SqlEventType.INSERT);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Read a module from the database thanks to id
     *
     * @param idModule id of the module
     */
    public static Module read(int idModule)
    {
        try
        {
            return moduleDao.queryForId(idModule);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Read a module from the database thanks to reference
     *
     * @param reference reference of the module
     */
    public static Module read(String reference)
    {
        try
        {
            return moduleDao.queryBuilder().where().eq("reference", reference).queryForFirst();
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Update the specified module into the database
     *
     * @param module entity
     */
    public static void update(Module module)
    {
        try
        {
            Module oldModule = moduleDao.queryForId(module.getIdModule());
            moduleDao.update(module);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{oldModule, module}, SqlEventType.UPDATE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Remove a module from the database
     *
     * @param module entity
     */
    public static void delete(Module module)
    {
        try
        {
            moduleDao.delete(module);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{module}, SqlEventType.DELETE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // END
    // ***************************************************************
}