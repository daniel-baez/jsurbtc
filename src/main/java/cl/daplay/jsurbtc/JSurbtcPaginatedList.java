package cl.daplay.jsurbtc;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

final class JSurbtcPaginatedList<T> extends AbstractList<T> implements Serializable {

    @FunctionalInterface
    public interface GetPage<T> {

        List<T> getPage(final int index) throws Exception;

    }

    private static final long serialVersionUID = 2017_08_06;

    private static JSurbtcPaginatedList<?> EMPTY = new JSurbtcPaginatedList<>(emptyList(), __ -> emptyList(), 0, 0);

    static <K> JSurbtcPaginatedList<K> empty() {
        return (JSurbtcPaginatedList<K>) EMPTY;
    }

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
    private final GetPage<T> nextPage;

    public JSurbtcPaginatedList(final List<T> firstPage,
                                final GetPage<T> nextPage,
                                int totalPages,
                                int totalCount) {
        this.pages = new Object[max(1, totalPages)];
        this.pages[0] = firstPage;

        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageSize = firstPage.size();

        this.nextPage = nextPage;
    }

    @Override
    public T get(final int index) {
        if (!isValidIndex(index, totalCount)) {
            throw new IllegalArgumentException(format("get(index=%d), illegal index", index));
        }

        final int pageForIndex = pageForIndex(index, pageSize);
        final List<T> page;

        try {
            page = getPage(pageForIndex);
        } catch (Exception e) {
            throw new JSurbtcPaginatedListException(e);
        }

        final int indexInPage = indexInPage(index, pageSize);

        return page.get(indexInPage);
    }

    @Override
    public int size() {
        return totalCount;
    }

    @SuppressWarnings("unchecked")
    private List<T> getPage(int pageForIndex) throws Exception {
        if (!isValidIndex(pageForIndex, totalPages)) {
            throw new IllegalArgumentException(format("nextPage(pageForIndex=%d), illegal pageForIndex", pageForIndex));
        }

        synchronized (pages) {
            if (pages[pageForIndex] == null) {
                pages[pageForIndex] = nextPage.getPage(pageForIndex);
            }
        }

        return (List<T>) pages[pageForIndex];
    }

}
