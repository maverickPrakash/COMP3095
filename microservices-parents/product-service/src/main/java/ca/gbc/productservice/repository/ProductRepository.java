package ca.gbc.productservice.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import ca.gbc.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
    @DeleteQuery
    void deleteById(String productId);
}
