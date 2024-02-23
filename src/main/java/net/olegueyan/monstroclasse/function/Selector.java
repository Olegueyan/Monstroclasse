package net.olegueyan.monstroclasse.function;

import java.util.List;
import java.util.function.Consumer;

/**
 * Selector | It has a cursor that moves on a list
 * @param <T> type of the list
 */
public class Selector<T>
{
    // ***************************************************************
    // Selector<T> - ATTRIBUTES
    // ***************************************************************

    // ------- Position on the list ------ //
    private int cursor;
    // ----------------------------------- //

    // ------- List on which to make the application selection system ------ //
    private final List<T> selected;
    // --------------------------------------------------------------------- //

    // ------- Action to do when the cursor changes or the list changes ------ //
    private Consumer<Selector<T>> onChange;
    // ----------------------------------------------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Selector<T> - CONSTRUCTOR
    // ***************************************************************

    public Selector(List<T> selected)
    {
        this.cursor = 0;
        this.selected = selected;
        this.onChange = null;
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // Selector<T> - METHODS
    // ***************************************************************

    /**
     * Move the cursor to the previous element if possible and return the selected element
     * @return T
     */
    public T previous()
    {
        this.cursor--;
        if (this.cursor < 0) this.cursor = 0;
        if (this.onChange != null) this.onChange.accept(this);
        return this.selected.get(this.cursor);
    }

    /**
     * Move the cursor to the next element if possible and return the selected element
     * @return T
     */
    public T next()
    {
        this.cursor++;
        if (this.cursor >= this.selected.size()) this.cursor = this.selected.size() - 1;
        if (this.onChange != null) this.onChange.accept(this);
        return this.selected.get(this.cursor);
    }

    /**
     * Get the current selected element
     * @return T
     */
    public T current()
    {
        return this.selected.get(this.cursor);
    }

    /**
     * Remove and get the current selected element
     * @return T
     */
    public T retrieve()
    {
        T t = this.selected.remove(this.cursor);
        if (this.onChange != null) this.onChange.accept(this);
        return t;
    }

    /**
     * Return true if the selector has a previous element else false
     * @return boolean
     */
    public boolean hasPrevious()
    {
        return this.cursor > 0;
    }

    /**
     * Return true if the selector has a next element else false
     * @return boolean
     */
    public boolean hasNext()
    {
        return this.cursor < this.selected.size() - 1;
    }

    /**
     * Return true if the selector is emptily else false
     * @return boolean
     */
    public boolean empty()
    {
        return this.selected.isEmpty();
    }

    /**
     * Reset the cursor | Set 0
     */
    public void reset()
    {
        this.cursor = 0;
        if (this.onChange != null) this.onChange.accept(this);
    }

    /**
     * Add an element to the end of the list
     * @param t element to add
     */
    public void push(T t)
    {
        this.selected.add(0, t);
        if (this.onChange != null) this.onChange.accept(this);
    }

    /**
     * Remove the first element of the list
     */
    public void pull()
    {
        this.selected.remove(0);
        if (this.onChange != null) this.onChange.accept(this);
    }

    /**
     * Set the consumer which represents action to do when the cursor changes or the list changes
     * @param onChange action to operate on Selector<T>
     */
    public void setOnChange(Consumer<Selector<T>> onChange)
    {
        this.onChange = onChange;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}