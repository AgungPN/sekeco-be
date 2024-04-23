package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.entities.Purchase;
import com.cashier.system.skecobe.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarcode(String barcode);

    List<Product> findByBarcodeIn(List<String> barcodes);

    Page<Product> findByNameContainingOrBrandContainingOrBarcodeContainingOrderByName(String name, String brand, String barcode, Pageable pageable);

    Page<Product> findByDeletedAtIsNullAndNameContainingOrBrandContainingOrBarcodeContainingOrderByName(String name, String brand, String barcode, Pageable pageable);

    Page<Product> findByDeletedAtIsNull(Pageable pageable);

    List<Product> findByDeletedAtIsNull();

    Page<Product> findByProductIdIn(Pageable pageable, List<Long> productIds);
}
