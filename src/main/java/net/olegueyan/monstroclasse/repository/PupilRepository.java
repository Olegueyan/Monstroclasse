package net.olegueyan.monstroclasse.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.SqlEventHubAdapter;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY |-> PupilRepository | Pupil
 */
public final class PupilRepository
{
    private static Dao<Pupil, Integer> pupilDao;

    static
    {
        try
        {
            ConnectionSource connectionSource = DatabaseService.getConnectionSource();
            pupilDao = DaoManager.createDao(connectionSource, Pupil.class);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // PupilRepository - METHODS
    // ***************************************************************

    /**
     * Create a pupil into the database
     *
     * @param pupil entity
     */
    public static void create(Pupil pupil)
    {
        try
        {
            pupilDao.create(pupil);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{pupil}, SqlEventType.INSERT);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Read a pupil from the database thanks to id
     *
     * @param idPupil id of the pupil
     */
    public static Pupil read(int idPupil)
    {
        try
        {
            return pupilDao.queryForId(idPupil);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Update the specified pupil into the database
     *
     * @param pupil entity
     */
    public static void update(Pupil pupil)
    {
        try
        {
            Pupil oldPupil = pupilDao.queryForId(pupil.getIdPupil());
            pupilDao.update(pupil);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{oldPupil, pupil}, SqlEventType.UPDATE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Remove a pupil from the database
     *
     * @param pupil entity
     */
    public static void delete(Pupil pupil)
    {
        try
        {
            pupilDao.delete(pupil);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{pupil}, SqlEventType.DELETE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all registered pupil
     *
     * @return ArrayList<Pupil>
     */
    public static List<Pupil> readAll()
    {
        try
        {
            return pupilDao.queryForAll();
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    // ***************************************************************
    // END
    // ***************************************************************
}