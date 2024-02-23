package net.olegueyan.monstroclasse.entity;

import javafx.scene.image.Image;
import net.olegueyan.monstroclasse.module.IsModule;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An account corresponds to a current connection regardless of admin or pupil
 */
public final class Account
{
    // ***************************************************************
    // Account - ATTRIBUTES
    // ***************************************************************

    // ------- name of account ------- //
    private final String name;
    // ------------------------------- //

    // ------- image of account ------- //
    private final Image image;
    // -------------------------------- //

    // ------- modules of account ------- //
    private final ArrayList<IsModule> modules;
    // ---------------------------------- //

    // ------- metadata of account ------- //
    private Object accountData;
    // ---------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Account - CONSTRUCTOR
    // ***************************************************************

    /**
     * @param name    name of the account
     * @param image   avatar of the account
     * @param modules account accessible modules
     */
    public Account(String name, Image image, ArrayList<IsModule> modules)
    {
        this.name = name;
        this.image = image;
        this.modules = modules;
        this.accountData = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Account - METHODS
    // ***************************************************************

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Account account = (Account) obj;

        return Objects.equals(name, account.name) &&
                Objects.equals(image, account.image) &&
                Objects.equals(modules, account.modules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, modules);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", modules=" + modules +
                ", data=" + accountData +
                '}';
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Account - GETTERS
    // ***************************************************************

    /**
     * Get the name of the account
     * @return String
     */
    public String name()
    {
        return this.name;
    }

    /**
     * Get image of the account
     * @return Image
     */
    public Image image()
    {
        return this.image;
    }

    /**
     * Get modules that account has access to
     * @return ArrayList<IsModule>
     */
    public ArrayList<IsModule> modules()
    {
        return this.modules;
    }

    /**
     * Get the metadata of the account
     * @param clazz class of the metadata
     * @return T
     * @param <T> type of the metadata
     */
    public <T> T getAccountData(Class<T> clazz)
    {
        return clazz.cast(this.accountData);
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Account - SETTER
    // ***************************************************************

    /**
     * Set metadata to the account
     * @param accountData metadata
     */
    public void setAccountData(Object accountData)
    {
        this.accountData = accountData;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}