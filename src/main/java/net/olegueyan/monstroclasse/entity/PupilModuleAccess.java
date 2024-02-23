package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "PupilModuleAccess")
public class PupilModuleAccess
{
    // ***************************************************************
    // PupilModuleAccess - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(columnName = "refPupil", foreign = true, foreignAutoRefresh = true)
    private Pupil pupil;

    @DatabaseField(columnName = "refModule", foreign = true, foreignAutoRefresh = true)
    private Module module;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PupilModuleAccess - CONSTRUCTORS
    // ***************************************************************

    public PupilModuleAccess()
    {
        // ORMLite needs a no-arg constructor
    }

    public PupilModuleAccess(Pupil pupil, Module module)
    {
        this.pupil = pupil;
        this.module = module;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PupilModuleAccess - GETTERS
    // ***************************************************************

    public Pupil getPupil()
    {
        return pupil;
    }

    public Module getModule()
    {
        return module;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PupilModuleAccess - SETTERS
    // ***************************************************************

    public void setPupil(Pupil pupil)
    {
        this.pupil = pupil;
    }

    public void setModule(Module module)
    {
        this.module = module;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}