package net.olegueyan.monstroclasse.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.GridCell;
import net.olegueyan.monstroclasse.entity.GridCol;
import net.olegueyan.monstroclasse.entity.GridRow;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY |-> GridRepository | GridRow / GridCol / GridCell
 */
public class GridRepository
{
    private static Dao<GridRow, Integer> gridRowDao;
    private static Dao<GridCol, Integer> gridColDao;
    private static Dao<GridCell, Integer> gridCellDao;

    static
    {
        try
        {
            ConnectionSource connectionSource = DatabaseService.getConnectionSource();
            gridRowDao = DaoManager.createDao(connectionSource, GridRow.class);
            gridColDao = DaoManager.createDao(connectionSource, GridCol.class);
            gridCellDao = DaoManager.createDao(connectionSource, GridCell.class);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    // ***************************************************************
    // GridRepository - METHODS
    // ***************************************************************

    public static void create(GridRow gridRow)
    {
        try
        {
            gridRowDao.create(gridRow);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all GridRow of the database
     * @return List<GridRow>
     */
    public static List<GridRow> readAllGridRow()
    {
        try
        {
            return gridRowDao.queryForAll();
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void create(GridCol gridCol)
    {
        try
        {
            gridColDao.create(gridCol);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all GridCol of the database
     * @return List<GridCol>
     */
    public static List<GridCol> readAllGridCol()
    {
        try
        {
            return gridColDao.queryForAll();
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void create(GridCell gridCell)
    {
        try
        {
            gridCellDao.create(gridCell);
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
    }

    /**
     * Get all GridCell of the database
     *
     * @return List<GridCell>
     */
    public static List<GridCell> readAllGridCell()
    {
        try
        {
            return gridCellDao.queryForAll();
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