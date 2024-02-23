package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ENTITY |-> GridCol
 */
@DatabaseTable(tableName = "GridCol")
public class GridCol
{
    // ***************************************************************
    // GridCol - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idGridCol")
    private Integer idGridCol;

    @DatabaseField(columnName = "axisY")
    private int axisY;

    @DatabaseField(foreign = true, columnName = "refAvatar", foreignAutoRefresh = true)
    private Avatar avatar;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridCol - CONSTRUCTORS
    // ***************************************************************

    public GridCol()
    {
        // ORMLite needs a no-arg constructor
    }

    public GridCol(Integer idGridCol)
    {
        this.idGridCol = idGridCol;
        this.axisY = 0;
        this.avatar = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridCol - GETTERS
    // ***************************************************************

    public Integer getIdGridCol()
    {
        return idGridCol;
    }

    public int getAxisY()
    {
        return this.axisY;
    }

    public Avatar getAvatar()
    {
        return this.avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridCol - SETTERS
    // ***************************************************************

    public void setAxisY(int axisY)
    {
        this.axisY = axisY;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}