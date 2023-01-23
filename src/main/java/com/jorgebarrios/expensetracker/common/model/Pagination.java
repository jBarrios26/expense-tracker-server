package com.jorgebarrios.expensetracker.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class Pagination {
    private Integer currentPage;
    private Integer totalItems;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
    private Integer numOfPages;

    public Pagination(
            Integer currentPage,
            Integer totalItems,
            Boolean hasNextPage,
            Boolean hasPreviousPage,
            Integer numOfPages
                     ) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.hasNextPage = hasNextPage;
        this.numOfPages = numOfPages;
        this.hasPreviousPage = hasPreviousPage;
    }

    public static Pagination fromPage(
            Page page,
            int currentPage
                                     ) {
        return new Pagination(currentPage,
                              page.getSize(),
                              page.hasNext(),
                              page.hasPrevious(),
                              page.getTotalPages()
        );
    }
}
