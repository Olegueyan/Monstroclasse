package net.olegueyan.monstroclasse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

/**
 * ENTITY |-> Module
 */
@DatabaseTable(tableName = "Module")
public class Module
{
    // ***************************************************************
    // Module - ATTRIBUTES
    // ***************************************************************

    @DatabaseField(generatedId = true, columnName = "idModule")
    private Integer idModule;

    @DatabaseField(columnName = "reference")
    private String reference;

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Module - CONSTRUCTORS
    // ***************************************************************

    public Module()
    {
        // ORMLite needs a no-arg constructor
    }

    public Module(Integer idModule)
    {
        this.idModule = idModule;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Module - GETTERS
    // ***************************************************************

    public Integer getIdModule()
    {
        return this.idModule;
    }

    public String getReference()
    {
        return this.reference;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Module - SETTERS
    // ***************************************************************

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Module - METHODS
    // ***************************************************************

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Module module = (Module) obj;
        return Objects.equals(idModule, module.idModule) &&
                Objects.equals(reference, module.reference);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idModule, reference);
    }

    // ***************************************************************
    // END
    // ***************************************************************
}