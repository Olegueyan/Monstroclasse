package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ENTITY |-> GridRow
 */
@DatabaseTable(tableName = "GridRow")
public class GridRow
{
    // ***************************************************************
    // GridRow - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idGridRow")
    private Integer idGridRow;

    @DatabaseField(columnName = "axisX")
    private int axisX;

    @DatabaseField(foreign = true, columnName = "refAvatar", foreignAutoRefresh = true)
    private Avatar avatar;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridRow - CONSTRUCTORS
    // ***************************************************************

    public GridRow()
    {
        // ORMLite needs a no-arg constructor
    }

    public GridRow(Integer idGridRow)
    {
        this.idGridRow = idGridRow;
        this.axisX = 0;
        this.avatar = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridRow - GETTERS
    // ***************************************************************

    public Integer getIdGridRow()
    {
        return idGridRow;
    }

    public int getAxisX()
    {
        return this.axisX;
    }

    public Avatar getAvatar()
    {
        return this.avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridRow - SETTERS
    // ***************************************************************

    public void setAxisX(int axisX)
    {
        this.axisX = axisX;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}