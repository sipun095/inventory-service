package com.sp.inventory.service.impl;

import com.sp.inventory.entity.BookStock;
import com.sp.inventory.repository.BookStockRepository;
import com.sp.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String INVENTORY_TOPIC = "inventory-updates";
    private static final String LOW_STOCK_ALERT_TOPIC = "low-stock-alerts";



    @Override
    public void updateStockManually(Long bookId, Integer quantity) {
        BookStock bookStock = bookStockRepository.findByBookId(bookId);
        if (bookStock == null) {
            throw new IllegalArgumentException("Book with ID " + bookId + " not found");
        }
        bookStock.setStockQuantity(bookStock.getStockQuantity() - quantity);
        bookStock.setLastUpdated(LocalDateTime.now());
        bookStockRepository.save(bookStock);

        // Kafka event for stock update
        kafkaTemplate.send(INVENTORY_TOPIC, "Stock updated for Book ID: " + bookId);

        // Check for low stock alert
        if (bookStock.getStockQuantity() <= bookStock.getLowStockThreshold()) {
            kafkaTemplate.send(LOW_STOCK_ALERT_TOPIC, "Low stock alert for Book ID: " + bookId);
        }

    }

    @Override
    public BookStock getStockByBookId(Long bookId) {
        return bookStockRepository.findByBookId(bookId);
    }
}
