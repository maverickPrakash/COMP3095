package ca.gbc.inventoryservice.boostrap;

import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import ca.gbc.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {

        if(inventoryRepository.findBySkuCode("sku_123456").isEmpty()){
            Inventory widgets =  Inventory.builder()
                    .skuCode("sku_123456")
            .quantity(3).build();

            inventoryRepository.save(widgets);
        }

        if(inventoryRepository.findBySkuCode("sku_1766").isEmpty()){
            Inventory widgets =  Inventory.builder()
                    .skuCode("sku_1766")
                    .quantity(5).build();

            inventoryRepository.save(widgets);
        }

        if(inventoryRepository.findBySkuCode("sku_55555").isEmpty()){
            Inventory widgets =  Inventory.builder()
                    .skuCode("sku_55555")
                    .quantity(0).build();

            inventoryRepository.save(widgets);
        }
    }
}
