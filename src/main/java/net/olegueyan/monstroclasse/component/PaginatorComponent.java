package net.olegueyan.monstroclasse.component;

import net.olegueyan.monstroclasse.function.BiFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class PaginatorComponent<T>
{
    // ***************************************************************
    // PaginatorComponent - ATTRIBUTES
    // ***************************************************************

    // ------- Original pagination ------- //
    private final ArrayList<T> originalList;

    // ------- Events to handle ------- //
    private final PaginatorListener<T> paginatorListener;
    // -------------------------------- //

    // ------- Current pagination (can be filtered) ------- //
    private ArrayList<T> pagination;
    // ---------------------------------------------------- //

    // ------- How many elements are in one page ------- //
    private final int perPage;
    // ------------------------------------------------- //

    // ------- Current selected page ------- //
    private int currentPage;
    // ------------------------------------- //

    // ------- Cursor that represent an index of an element inside pagination ------- //
    private int cursor;
    // ------------------------------------------------------------------------------ //

    // ------- Operation to execute to create a comparison between two elements ------- //
    private Comparator<T> sortMethod = null;
    // -------------------------------------------------------------------------------- //

    // ------- Current filter ------- //
    private String currentFilter = "";
    // ------------------------------ //

    // ------- Operation to execute to include or not an element inside filtered pagination ------- //
    private BiFunction<T, String, Boolean> currentFilterFunction = null;
    // -------------------------------------------------------------------------------------------- //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PaginatorComponent - CONSTRUCTOR
    // ***************************************************************

    /**
     * Constructor that creates a PaginatorComponent
     * @param pagination complete list of elements
     * @param perPage number of elements on each page
     */
    public PaginatorComponent(ArrayList<T> pagination, int perPage, PaginatorListener<T> paginatorListener)
    {
        if (paginatorListener == null)
        {
            throw new RuntimeException("A PaginatorComponent must have a listener to work !");
        }

        this.paginatorListener = paginatorListener;

        this.originalList = pagination;
        this.pagination = pagination;
        this.perPage = perPage;
        this.currentPage = 1;
        this.cursor = 0;

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PaginatorComponent - METHODS
    // ***************************************************************

    public void setSortMethod(Comparator<T> comparator)
    {
        this.sortMethod = comparator;
    }

    public void nextPage()
    {
        this.currentPage++;
        this.cursor = Math.min(cursor + perPage, pagination.size());

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    public void previousPage()
    {
        currentPage--;
        this.cursor = Math.max(0, cursor - perPage);

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    public void setPage(int page)
    {
        if (page >= 1 && page <= getTotalPages())
        {
            this.currentPage = page;
            this.cursor = (page - 1) * perPage;

            this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
        }
    }

    public void firstPage()
    {
        this.currentPage = 1;
        this.cursor = 0;

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    public void lastPage()
    {
        this.currentPage = this.getTotalPages();
        int remainder = pagination.size() % perPage;
        this.cursor = (remainder == 0) ? pagination.size() - perPage : pagination.size() - remainder;

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    private ArrayList<T> getPageAtIndex(int pageIndex)
    {
        if (pageIndex >= 1 && pageIndex <= getTotalPages())
        {
            int startIndex = (pageIndex - 1) * perPage;
            int endIndex = Math.min(startIndex + perPage, this.pagination.size());

            return new ArrayList<>(this.pagination.subList(startIndex, endIndex));
        }
        else
        {
            return this.pagination;
        }
    }

    public int currentPage()
    {
        return this.currentPage;
    }

    public int getTotalPages()
    {
        return Math.max(1, (int) Math.ceil((double) pagination.size() / perPage));
    }

    public boolean hasNext()
    {
        return this.cursor + this.perPage < this.pagination.size();
    }

    public boolean hasPrevious()
    {
        return this.currentPage > 1;
    }

    public boolean inFilterMode()
    {
        return !this.currentFilter.isEmpty();
    }

    public void filter(String filter, BiFunction<T, String, Boolean> filterFunction)
    {
        this.pagination = new ArrayList<>(this.originalList
                .stream()
                .filter(t -> filterFunction.apply(t, filter))
                .toList());
        this.currentFilter = filter;
        this.currentFilterFunction = filterFunction;
    }

    public void removeFilter()
    {
        this.pagination = this.originalList;
        this.currentFilter = "";
    }

    public String getCurrentFilter()
    {
        return this.currentFilter;
    }

    public void extract(T toExtract)
    {
        this.originalList.remove(toExtract);
        this.processModificationPaginationContent();
    }

    public void extract(Predicate<T> predicate)
    {
        this.originalList.removeIf(predicate);
        this.processModificationPaginationContent();
    }

    public void insert(T toInsert)
    {
        this.originalList.add(toInsert);
        this.processModificationPaginationContent();
    }

    private void processModificationPaginationContent()
    {
        if (!this.currentFilter.isEmpty() && this.currentFilterFunction != null)
        {
            this.filter(this.currentFilter, this.currentFilterFunction);
        }

        if (this.sortMethod != null)
        {
            this.originalList.sort(this.sortMethod);
            this.pagination.sort(this.sortMethod);
        }

        this.paginatorListener.onPageChange(this.currentPage, this.getPageAtIndex(this.currentPage), this.inFilterMode(), this.hasPrevious(), this.hasNext());
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // PaginatorNode - PaginatorListener<T>
    // ***************************************************************

    public interface PaginatorListener<T>
    {
        /**
         * Called when the current page change
         * @param index index of the page
         * @param page content of the page
         * @param filter if the page was filtered
         */
        void onPageChange(int index, ArrayList<T> page, boolean filter, boolean hasPrevious, boolean hasNext);
    }

    // ***************************************************************
    // END
    // ***************************************************************
}