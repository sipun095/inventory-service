package com.sp.inventory.controller;

import com.sp.inventory.entity.BookStock;
import com.sp.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/update-stock/{bookId}")
    public ResponseEntity<?> updateStockManually(
            @PathVariable Long bookId, @RequestParam Integer quantity) {
        inventoryService.updateStockManually(bookId, quantity);
        return ResponseEntity.ok("Stock updated successfully!");
    }

    @GetMapping("/stock/{bookId}")
    public ResponseEntity<BookStock> getStock(@PathVariable Long bookId) {
        return ResponseEntity.ok(inventoryService.getStockByBookId(bookId));
    }
}
