package sample.service.dto;

import java.util.List;

public record PageResult<T>(List<T> items, int currentPage, int totalPages, long totalCount) {
    public boolean hasPrevious() {
        return currentPage > 1;
    }

    public boolean hasNext() {
        return currentPage < totalPages;
    }
}