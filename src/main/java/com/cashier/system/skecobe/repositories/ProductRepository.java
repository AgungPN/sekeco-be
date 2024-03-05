package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    Optional<Product> findByBarcode(String barcode);
//    @Query("SELECT p FROM product p LIMIT :size OFFSET :page")
//    List<Product> findAllPaginated(int page, int size);
}
