package com.sp.inventory.repository;

import com.sp.inventory.entity.BookStock;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStockRepository {
    BookStock findByBookId(Long bookId);

    void save(BookStock bookStock);
}
