package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ENTITY |-> GridCell
 */
@DatabaseTable(tableName = "GridCell")
public class GridCell
{
    // ***************************************************************
    // GridCell - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idGridCell")
    private Integer idGridCell;

    @DatabaseField(columnName = "axisX")
    private int axisX;

    @DatabaseField(columnName = "axisY")
    private int axisY;

    @DatabaseField(foreign = true, columnName = "refAvatar", foreignAutoRefresh = true)
    private Avatar avatar;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridCell - CONSTRUCTORS
    // ***************************************************************

    public GridCell()
    {
        // ORMLite needs a no-arg constructor
    }

    public GridCell(Integer idGridCell)
    {
        this.idGridCell = idGridCell;
        this.axisX = 0;
        this.axisY = 0;
        this.avatar = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // GridCell - GETTERS
    // ***************************************************************

    public Integer getIdGridCell()
    {
        return idGridCell;
    }

    public int getAxisX()
    {
        return this.axisX;
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
    // GridCell - SETTERS
    // ***************************************************************

    public void setAxisX(int axisX)
    {
        this.axisX = axisX;
    }

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