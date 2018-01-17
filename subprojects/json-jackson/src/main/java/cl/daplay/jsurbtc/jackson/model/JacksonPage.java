package cl.daplay.jsurbtc.jackson.model;

import cl.daplay.jsurbtc.model.Page;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class JacksonPage implements Page {

    private final int totalPages;
    private final int totalCount;
    private final int currentPage;

    @JsonCreator
    public JacksonPage(@JsonProperty("total_pages") final int totalPages,
                       @JsonProperty("total_count") final int totalCount,
                       @JsonProperty("current_page") final int currentPage) {
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

        final JacksonPage that = (JacksonPage) o;

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
