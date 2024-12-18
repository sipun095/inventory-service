package com.sp.inventory.service;

import com.sp.inventory.entity.BookStock;

public interface InventoryService {
    void updateStockManually(Long bookId, Integer quantity);

    BookStock getStockByBookId(Long bookId);
}
