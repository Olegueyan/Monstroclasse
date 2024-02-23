package net.olegueyan.monstroclasse.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Avatar;
import net.olegueyan.monstroclasse.event.SqlEventHubAdapter;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY |-> AvatarRepository | Avatar
 */
public final class AvatarRepository
{
    private static Dao<Avatar, Integer> avatarDao;

    static
    {
        try
        {
            ConnectionSource connectionSource = DatabaseService.getConnectionSource();
            avatarDao = DaoManager.createDao(connectionSource, Avatar.class);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // AvatarRepository - METHODS
    // ***************************************************************

    /**
     * Create an avatar into the database
     *
     * @param avatar entity
     */
    public static void create(Avatar avatar)
    {
        try
        {
            avatarDao.create(avatar);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{avatar}, SqlEventType.INSERT);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Read an avatar from the database thanks to id
     *
     * @param idAvatar id of the avatar
     * @return Avatar
     */
    public static Avatar read(int idAvatar)
    {
        try
        {
            return avatarDao.queryForId(idAvatar);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Update the specified avatar into the database
     *
     * @param avatar entity
     */
    public static void update(Avatar avatar)
    {
        try
        {
            Avatar oldAvatar = avatarDao.queryForId(avatar.getIdAvatar());
            avatarDao.update(avatar);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{oldAvatar, avatar}, SqlEventType.UPDATE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Remove an avatar from the database
     *
     * @param avatar entity
     */
    public static void delete(Avatar avatar)
    {
        try
        {
            avatarDao.delete(avatar);
            SqlEventHubAdapter.getInstance().onSqlPerform(new Object[]{avatar}, SqlEventType.DELETE);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all avatars available | "available" = 1
     *
     * @return List<Avatar>
     */
    public static List<Avatar> readAvailable()
    {
        try
        {
            return avatarDao.queryForEq("available", 1);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get all avatars
     *
     * @return List<Avatar>
     */
    public static List<Avatar> readAll()
    {
        try
        {
            return avatarDao.queryForAll();
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