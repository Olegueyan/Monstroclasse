package net.olegueyan.monstroclasse.component;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The PaneHandlerComponent is a component that allows to manipulate pane that constitutes the core of a window
 */
public class PaneHandlerComponent
{
    // ***************************************************************
    // PaneHandlerComponent - ATTRIBUTES
    // ***************************************************************

    // ------- Storage of pane (Key / Pane) ------- //
    private final Map<String, Pane> panes;
    // -------------------------------------------- //

    // ------- Current key of pane showed ------- //
    private String key = null;
    // ------------------------------------------ //

    // ------- Current pane showed ------- //
    private Pane current = null;
    // ----------------------------------- //

    // ------- Action to operation when showed pane changed ------- //
    private BiConsumer<String, Pane> onChange = null;
    // ------------------------------------------------------------ //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PaneHandlerComponent - CONSTRUCTOR
    // ***************************************************************

    public PaneHandlerComponent()
    {
        /*
            Initialisation of the storage
        */
        this.panes = new HashMap<>();
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PaneHandlerComponent - METHODS
    // ***************************************************************

    /**
     * Add a pane to the component
     * @param name name of the key
     * @param pane pane linked to the key
     */
    public void addPane(String name, Pane pane)
    {
        this.panes.put(name, pane);
    }

    /**
     * Remove a pane of the component
     * @param name name of the key
     */
    public void removePane(String name)
    {
        this.panes.remove(name);
    }

    /**
     * Set visibility to false for all panes
     */
    public void hideAll()
    {
        this.panes.forEach((s, pane) -> pane.setVisible(false));
    }

    public void setOnChange(BiConsumer<String, Pane> onChange)
    {
        this.onChange = onChange;
    }

    /**
     * Show specified pane
     * @param name name of the key
     */
    public void show(String name)
    {
        if (!this.panes.containsKey(name))
        {
            throw new NullPointerException(name + " pane doesn't exist !");
        }
        if (this.current != null) this.current.setVisible(false);
        this.current = this.panes.get(name);
        this.key = name;

        if (this.onChange != null)
        {
            this.onChange.accept(this.key, this.current);
        }

        this.current.setVisible(true);
    }

    /**
     * Get the current pane
     * @return Pane
     */
    public Pane current()
    {
        if (this.current == null)
        {
            if (this.panes.isEmpty())
            {
                throw new RuntimeException("The Panel Handler is empty !");
            }
            else
            {
                throw new RuntimeException("The Panel Handler has not been initialized !");
            }
        }
        return this.current;
    }

    /**
     * Get the key of the current pane
     * @return String
     */
    public String getKey()
    {
        if (this.key == null)
        {
            if (this.panes.isEmpty())
            {
                throw new RuntimeException("The Panel Handler is empty !");
            }
            else
            {
                throw new RuntimeException("The Panel Handler has not been initialized !");
            }
        }
        return this.key;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}