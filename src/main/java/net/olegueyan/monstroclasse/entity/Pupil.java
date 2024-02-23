package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

/**
 * ENTITY |-> Pupil
 */
@DatabaseTable(tableName = "Pupil")
public class Pupil
{
    // ***************************************************************
    // Pupil - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idPupil")
    private Integer idPupil;

    @DatabaseField(columnName = "namePupil")
    private String namePupil;

    @DatabaseField(foreign = true, columnName = "refAvatar", foreignAutoRefresh = true)
    private Avatar avatar;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Pupil - CONSTRUCTORS
    // ***************************************************************

    public Pupil()
    {
        // ORMLite needs a no-arg constructor
    }

    public Pupil(Integer idPupil)
    {
        this.idPupil = idPupil;
        this.namePupil = "";
        this.avatar = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Pupil - GETTERS
    // ***************************************************************

    public Integer getIdPupil()
    {
        return this.idPupil;
    }

    public String getName()
    {
        return this.namePupil;
    }

    public Avatar getAvatar()
    {
        return this.avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Pupil - SETTERS
    // ***************************************************************

    public void setName(String namePupil)
    {
        this.namePupil = namePupil;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Pupil - METHODS
    // ***************************************************************

    @Override
    public int hashCode()
    {
        return Objects.hash(idPupil, namePupil, avatar);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pupil pupil = (Pupil) obj;
        return Objects.equals(idPupil, pupil.idPupil) &&
                Objects.equals(namePupil, pupil.namePupil) &&
                Objects.equals(avatar, pupil.avatar);
    }

    @Override
    public String toString()
    {
        return "Pupil{" +
                "idPupil=" + idPupil +
                ", namePupil='" + namePupil + '\'' +
                ", avatar=" + avatar +
                '}';
    }

    // ***************************************************************
    // END
    // ***************************************************************
}