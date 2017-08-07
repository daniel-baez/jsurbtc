package cl.daplay.jsurbtc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaginationDTO {

    @JsonProperty("total_pages")
    private final int totalPages;
    @JsonProperty("total_count")
    private final int totalCount;
    @JsonProperty("current_page")
    private final int currentPage;

    @JsonCreator
    public PaginationDTO(@JsonProperty("total_pages") final int totalPages,
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
}
