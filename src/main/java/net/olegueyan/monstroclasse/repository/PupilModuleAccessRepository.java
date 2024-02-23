package net.olegueyan.monstroclasse.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.entity.PupilModuleAccess;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY |-> PupilModuleAccessRepository | Pupil / Module
 */
public class PupilModuleAccessRepository
{
    private static Dao<PupilModuleAccess, Integer> pupilModuleAccessDao;

    static
    {
        try
        {
            ConnectionSource connectionSource = DatabaseService.getConnectionSource();
            pupilModuleAccessDao = DaoManager.createDao(connectionSource, PupilModuleAccess.class);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // PupilModuleAccessRepository - METHODS
    // ***************************************************************

    /**
     * Give access to the specified module to the specified pupil
     *
     * @param pupil  pupil
     * @param module module
     */
    public static void create(Pupil pupil, Module module)
    {
        try
        {
            PupilModuleAccess pupilModuleAccess = new PupilModuleAccess();
            pupilModuleAccess.setPupil(pupil);
            pupilModuleAccess.setModule(module);
            pupilModuleAccessDao.create(pupilModuleAccess);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all modules that pupil has access to
     *
     * @param pupil entity
     * @return List<Module>
     */
    public static List<Module> readModules(Pupil pupil)
    {
        List<Module> modules = new ArrayList<>();
        try
        {
            QueryBuilder<PupilModuleAccess, Integer> queryBuilder = pupilModuleAccessDao.queryBuilder();
            queryBuilder.where().eq("refPupil", pupil.getIdPupil());
            List<PupilModuleAccess> accesses = queryBuilder.query();
            for (PupilModuleAccess access : accesses)
            {
                modules.add(access.getModule());
            }
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
        return modules;
    }

    /**
     * Remove access to the specified module to the specified pupil
     *
     * @param pupil  pupil
     * @param module module
     */
    public static void delete(Pupil pupil, Module module)
    {
        try
        {
            DeleteBuilder<PupilModuleAccess, Integer> deleteBuilder = pupilModuleAccessDao.deleteBuilder();
            deleteBuilder.where()
                    .eq("refPupil", pupil.getIdPupil())
                    .and()
                    .eq("refModule", module.getIdModule());
            deleteBuilder.delete();
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