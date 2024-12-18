package com.sp.inventory.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(topics = "inventory-updates", groupId = "inventory-group")
    public void consumeInventoryUpdate(String message) {
        System.out.println("Received Inventory Update: " + message);
    }

    @KafkaListener(topics = "low-stock-alerts", groupId = "low-stock-group")
    public void consumeLowStockAlert(String message) {
        System.out.println("Received Low Stock Alert: " + message);
    }
}
