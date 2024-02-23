package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ENTITY |-> Avatar
 */
@DatabaseTable(tableName = "Avatar")
public class Avatar
{
    // ***************************************************************
    // Avatar - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idAvatar")
    private Integer idAvatar;

    @DatabaseField(columnName = "data", dataType = DataType.BYTE_ARRAY)
    private byte[] data;

    @DatabaseField(columnName = "available")
    private Integer available;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Avatar - CONSTRUCTORS
    // ***************************************************************

    public Avatar()
    {
        // ORMLite needs a no-arg constructor
    }

    public Avatar(Integer idAvatar)
    {
        this.idAvatar = idAvatar;
        this.data = new byte[0];
        this.available = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Avatar - GETTERS
    // ***************************************************************

    public Integer getIdAvatar()
    {
        return this.idAvatar;
    }

    public byte[] getData()
    {
        return this.data;
    }

    public Integer getAvailable()
    {
        return this.available;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Avatar - SETTERS
    // ***************************************************************

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public void setAvailable(Integer available)
    {
        this.available = available;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}