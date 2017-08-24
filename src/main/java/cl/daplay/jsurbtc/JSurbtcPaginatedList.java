package cl.daplay.jsurbtc;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;
import java.util.function.IntFunction;

import static java.lang.Math.max;
import static java.lang.String.format;

@ThreadSafe
final class JSurbtcPaginatedList<T> extends AbstractList<T> implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    static int indexInPage(final int index, final int pageSize) {
        final int page = pageForIndex(index, pageSize);
        final int offset = pageSize * page;

        return index - offset;
    }

    static int pageForIndex(int index, int pageSize) {
        return index / pageSize;
    }

    static boolean isValidIndex(int index, int totalCount) {
        return index >= 0 && index < totalCount;
    }

    private final int pageSize;
    private final int totalCount;
    private final int totalPages;
    private final Object[] pages;
    private final IntFunction<List<T>> getPage;

    public JSurbtcPaginatedList(final List<T> firstPage,
                                int totalCount,
                                int totalPages,
                                final IntFunction<List<T>> getPage) {
        this.pages = new Object[max(1, totalPages)];
        this.pages[0] = firstPage;

        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageSize = firstPage.size();

        this.getPage = getPage;
    }

    @Override
    public T get(final int index) {
        if (!isValidIndex(index, totalCount)) {
            throw new IllegalArgumentException(format("get(index=%d), illegal index", index));
        }

        final int pageForIndex = pageForIndex(index, pageSize);
        final List<T> page = getPage(pageForIndex);

        final int indexInPage = indexInPage(index, pageSize);

        return page.get(indexInPage);
    }

    @Override
    public int size() {
        return totalCount;
    }

    @SuppressWarnings("unchecked")
    private List<T> getPage(int pageForIndex) {
        if (!isValidIndex(pageForIndex, totalPages)) {
            throw new IllegalArgumentException(format("getPage(pageForIndex=%d), illegal pageForIndex", pageForIndex));
        }

        synchronized (pages) {
            if (pages[pageForIndex] == null) {
                pages[pageForIndex] = getPage.apply(pageForIndex);
            }
        }

        return (List<T>) pages[pageForIndex];
    }

}
