package net.olegueyan.monstroclasse.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.*;
import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.repository.*;
import net.olegueyan.monstroclasse.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MonstroclasseDatabaseUtils
{
    public static void fusionDatabases(Connection receptor, Connection injector)
    {
        // ***** INJECT AVATAR PART ***** //
        MonstroclasseDatabaseUtils.tryCreateAvatar(receptor);
        MonstroclasseDatabaseUtils.tryFillAvatar(receptor, injector);
        // ***** INJECT AVATAR PART ***** //

        // ***** INJECT GRID_CELL PART ***** //
        MonstroclasseDatabaseUtils.tryCreateGridCell(receptor);
        MonstroclasseDatabaseUtils.tryFillGridCell(receptor, injector);
        // ***** INJECT GRID_CELL PART ***** //

        // ***** INJECT GRID_COL PART ***** //
        MonstroclasseDatabaseUtils.tryCreateGridCol(receptor);
        MonstroclasseDatabaseUtils.tryFillGridCol(receptor, injector);
        // ***** INJECT GRID_COL PART ***** //

        // ***** INJECT GRID_ROW PART ***** //
        MonstroclasseDatabaseUtils.tryCreateGridRow(receptor);
        MonstroclasseDatabaseUtils.tryFillGridRow(receptor, injector);
        // ***** INJECT GRID_ROW PART ***** //

        // ***** INJECT MODULE PART ***** //
        MonstroclasseDatabaseUtils.tryCreateModule(receptor);
        MonstroclasseDatabaseUtils.tryFillModule(receptor, injector);
        // ***** INJECT MODULE PART ***** //

        // ***** INJECT PUPIL PART ***** //
        MonstroclasseDatabaseUtils.tryCreatePupil(receptor);
        MonstroclasseDatabaseUtils.tryFillPupil(receptor, injector);
        // ***** INJECT PUPIL PART ***** //

        // ***** INJECT PUPIL_MODULE_ACCESS PART ***** //
        MonstroclasseDatabaseUtils.tryCreatePupilModuleAccess(receptor);
        MonstroclasseDatabaseUtils.tryFillPupilModuleAccess(receptor, injector);
        // ***** INJECT PUPIL_MODULE_ACCESS PART ***** //

        // ***** PUT CORRECT AVAILABILITY IN AVATAR ***** //
        try
        {
            MonstroclasseDatabaseUtils.updateAvatarAvailability();
        }
        catch (SQLException e)
        {
            Main.logger.error(e.getMessage());
        }
        // ***** PUT CORRECT AVAILABILITY IN AVATAR ***** //
    }

    private static void tryCreateAvatar(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'Avatar' (
                'idAvatar'	INTEGER,
                'data'	BLOB UNIQUE,
                'available'	INTEGER DEFAULT 1,
                PRIMARY KEY('idAvatar' AUTOINCREMENT)
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillAvatar(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'Avatar'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                int idAvatar = resultSet.getInt("idAvatar");
                byte[] data = resultSet.getBytes("data");
                int available = resultSet.getInt("available");

                Avatar avatar = new Avatar(idAvatar);
                avatar.setData(data);
                avatar.setAvailable(available);

                AvatarRepository.create(avatar);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreateGridCell(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'GridCell' (
                'idGridCell' INTEGER,
                'axisX'	INTEGER,
                'axisY'	INTEGER,
                'refAvatar'	INTEGER UNIQUE
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillGridCell(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'GridCell'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery();)
        {
            while (resultSet.next())
            {
                int idGridCell = resultSet.getInt("idGridCell");
                int axisX = resultSet.getInt("axisX");
                int axisY = resultSet.getInt("axisY");
                int refAvatar = resultSet.getInt("refAvatar");

                GridCell gridCell = new GridCell(idGridCell);
                gridCell.setAxisX(axisX);
                gridCell.setAxisY(axisY);
                gridCell.setAvatar(AvatarRepository.read(refAvatar));

                GridRepository.create(gridCell);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreateGridCol(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'GridCol' (
                'idGridCol' INTEGER,
                'axisY'	INTEGER UNIQUE,
                'refAvatar'	INTEGER UNIQUE
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillGridCol(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'GridCol'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                int idGridCol = resultSet.getInt("idGridCol");
                int axisY = resultSet.getInt("axisY");
                int refAvatar = resultSet.getInt("refAvatar");

                GridCol gridCol = new GridCol(idGridCol);
                gridCol.setAxisY(axisY);
                gridCol.setAvatar(AvatarRepository.read(refAvatar));

                GridRepository.create(gridCol);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreateGridRow(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'GridRow' (
                'idGridRow' INTEGER,
                'axisX'	INTEGER UNIQUE,
                'refAvatar'	INTEGER UNIQUE
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillGridRow(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'GridRow'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                int idGridRow = resultSet.getInt("idGridRow");
                int axisX = resultSet.getInt("axisX");
                int refAvatar = resultSet.getInt("refAvatar");

                GridRow gridRow = new GridRow(idGridRow);
                gridRow.setAxisX(axisX);
                gridRow.setAvatar(AvatarRepository.read(refAvatar));

                GridRepository.create(gridRow);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreateModule(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'Module' (
                'idModule'	INTEGER,
                'reference'	TEXT UNIQUE,
                PRIMARY KEY('idModule' AUTOINCREMENT)
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillModule(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'Module'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                int idModule = resultSet.getInt("idModule");
                String reference = resultSet.getString("reference");

                Module module = new Module(idModule);
                module.setReference(reference);

                ModuleRepository.create(module);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreatePupil(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'Pupil' (
                'idPupil'	INTEGER,
                'namePupil'	TEXT,
                'refAvatar'	INTEGER UNIQUE,
                PRIMARY KEY('idPupil' AUTOINCREMENT)
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillPupil(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'Pupil'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                //int idPupil = resultSet.getInt("idPupil");
                String namePupil = resultSet.getString("namePupil");
                int refAvatar = resultSet.getInt("refAvatar");

                Pupil pupil = new Pupil(/*idPupil*/);
                pupil.setName(namePupil);
                pupil.setAvatar(AvatarRepository.read(refAvatar));

                PupilRepository.create(pupil);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void tryCreatePupilModuleAccess(Connection connection)
    {
        String query = """ 
            CREATE TABLE IF NOT EXISTS 'PupilModuleAccess' (
                'refPupil'	INTEGER,
                'refModule'	INTEGER,
                CONSTRAINT "pupilModuleAccess" UNIQUE("refPupil","refModule"),
                CONSTRAINT fk_PupilModuleAccess_Pupil FOREIGN KEY (refPupil) REFERENCES Pupil(idPupil),
                CONSTRAINT fk_PupilModuleAccess_Module FOREIGN KEY (refModule) REFERENCES Module(idModule)
            );
        """;
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }
    }

    private static void tryFillPupilModuleAccess(Connection receptor, Connection injector)
    {
        String selectQuery = "SELECT * FROM 'PupilModuleAccess'";

        try (PreparedStatement selectStatement = injector.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery())
        {
            while (resultSet.next())
            {
                int refPupil = resultSet.getInt("refPupil");
                int refModule = resultSet.getInt("refModule");

                Pupil pupil = PupilRepository.read(refPupil);
                Module module = ModuleRepository.read(refModule);

                PupilModuleAccessRepository.create(pupil, module);
            }
        }
        catch (SQLException ignored) { /* Don't overload the console */ }
    }

    private static void updateAvatarAvailability() throws SQLException
    {
        for (Avatar avatar : AvatarRepository.readAll())
        {
            boolean isUsed = isAvatarUsed(avatar);
            avatar.setAvailable(isUsed ? 0 : 1);
            AvatarRepository.update(avatar);
        }
    }

    private static boolean isAvatarUsed(Avatar avatar) throws SQLException
    {
        ConnectionSource connectionSource = DatabaseService.getConnectionSource();

        Dao<Pupil, Integer> pupilDao = DaoManager.createDao(connectionSource, Pupil.class);
        QueryBuilder<Pupil, Integer> pupilQueryBuilder = pupilDao.queryBuilder();
        pupilQueryBuilder.where().eq("refAvatar", avatar.getIdAvatar());
        if (pupilQueryBuilder.queryForFirst() != null) return true;

        Dao<GridCol, Integer> gridColDao = DaoManager.createDao(connectionSource, GridCol.class);
        QueryBuilder<GridCol, Integer> gridColQueryBuilder = gridColDao.queryBuilder();
        gridColQueryBuilder.where().eq("refAvatar", avatar.getIdAvatar());
        if (gridColQueryBuilder.queryForFirst() != null) return true;

        Dao<GridRow, Integer> gridRowDao = DaoManager.createDao(connectionSource, GridRow.class);
        QueryBuilder<GridRow, Integer> gridRowQueryBuilder = gridRowDao.queryBuilder();
        gridRowQueryBuilder.where().eq("refAvatar", avatar.getIdAvatar());
        if (gridRowQueryBuilder.queryForFirst() != null) return true;

        return false;
    }
}