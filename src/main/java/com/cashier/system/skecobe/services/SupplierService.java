package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Supplier;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.SupplierRepository;
import com.cashier.system.skecobe.requests.supplier.CreateSupplierRequest;
import com.cashier.system.skecobe.requests.supplier.UpdateSupplierRequest;
import com.cashier.system.skecobe.responses.SupplierResponse;
import com.cashier.system.skecobe.responses.TourResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierService {
    private SupplierRepository supplierRepository;
    private ValidationService validationService;

    public List<SupplierResponse> getListNoPaginate() {
        return supplierRepository.findAll().stream().map(SupplierResponse::convertToResponse).toList();
    }

    public Page<SupplierResponse> getList(Pageable pageable) {
        var suppliers = supplierRepository.findAll(pageable);
        return suppliers.map(SupplierResponse::convertToResponse);
    }

    public Page<SupplierResponse> getList(String search, Pageable pageable) {
        var suppliers = supplierRepository.findByNameContainingOrPhoneContainingOrderByName(search, search, pageable);
        return suppliers.map(SupplierResponse::convertToResponse);
    }

    public SupplierResponse getOneById(Long supplierId) {
        return SupplierResponse.convertToResponse(findById(supplierId));
    }

    public Supplier findById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier"));
    }

    public SupplierResponse save(CreateSupplierRequest createSupplierRequest) {
        validationService.validate(createSupplierRequest);

        Supplier supplier = Supplier.builder()
                .name(createSupplierRequest.getName())
                .address(createSupplierRequest.getAddress())
                .phone(createSupplierRequest.getPhone())
                .build();

        supplierRepository.save(supplier);

        return SupplierResponse.convertToResponse(supplier);
    }

    public SupplierResponse update(Long id, UpdateSupplierRequest supplierRequest) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier"));

        validationService.validate(supplierRequest);

        supplier.setName(supplierRequest.getName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setPhone(supplierRequest.getPhone());
        supplierRepository.save(supplier);

        return SupplierResponse.convertToResponse(supplier);
    }

    public void deleteById(Long supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
