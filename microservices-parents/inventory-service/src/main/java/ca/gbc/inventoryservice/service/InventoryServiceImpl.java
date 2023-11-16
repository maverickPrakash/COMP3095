package ca.gbc.inventoryservice.service;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.dto.InventoryResponse;
import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<InventoryRequest> requests) {

        List<Inventory> availableInventory = inventoryRepository.findAllByInventoryRequests(requests);
//        if(true)
//        throw new RuntimeException("hello "+ requests.stream().map(InventoryRequest::getSkuCode).toList().toString());
        return requests.stream().map(request -> {

            boolean isInStock = availableInventory.stream()
                    .anyMatch(inventory -> inventory.getSkuCode().equals(request.getSkuCode())
                            && inventory.getQuantity() >= request.getQuantity());

            if(isInStock){
                return InventoryResponse.builder()
                        .skuCode(request.getSkuCode())
                        .sufficientStock(true)
                        .build();
            }else {
                return InventoryResponse.builder()
                        .skuCode(request.getSkuCode())
                        .sufficientStock(false)
                        .build();
            }

        }).toList();
    }
}
