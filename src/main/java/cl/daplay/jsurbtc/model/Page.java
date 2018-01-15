package cl.daplay.jsurbtc.model;

public final class Page {

    private final int totalPages;
    private final int totalCount;
    private final int currentPage;

    public Page(final int totalPages,
                final int totalCount,
                final int currentPage) {
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Page that = (Page) o;

        if (totalPages != that.totalPages) return false;
        if (totalCount != that.totalCount) return false;
        return currentPage == that.currentPage;
    }

    @Override
    public int hashCode() {
        int result = totalPages;
        result = 31 * result + totalCount;
        result = 31 * result + currentPage;
        return result;
    }

}
