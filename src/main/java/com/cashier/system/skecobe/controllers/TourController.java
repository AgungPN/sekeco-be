package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.tour.CreateTourRequest;
import com.cashier.system.skecobe.requests.tour.UpdateTourRequest;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.responses.TourResponse;
import com.cashier.system.skecobe.services.TourService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tours")
@AllArgsConstructor
public class TourController {

    private TourService tourService;

    @GetMapping
    public Page<TourResponse> getList(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault() Pageable pageable) {
        return search == null || search.isEmpty()
                ? tourService.getList(pageable)
                : tourService.getList(search, pageable);
    }

    @GetMapping("/{tourId}")
    public ResponseEntity<Object> getOne(@PathVariable Long tourId) {
        var tourResponse = tourService.findById(tourId);

        return ResponseHandler.responseWithoutMessage(tourResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateTourRequest tourRequest) {
        var tourResponse = tourService.save(tourRequest);
        return ResponseHandler.generateResponse(
                "Tour created", tourResponse, HttpStatus.CREATED
        );
    }

    @PutMapping("/{tourId}")
    public ResponseEntity<Object> update(
            @PathVariable Long tourId, @RequestBody UpdateTourRequest tourRequest) {
        var tourResponse = tourService.update(tourId, tourRequest);

        return ResponseHandler.generateResponse(
                "Tour updated", tourResponse, HttpStatus.OK
        );
    }

    @DeleteMapping("/{tourId}")
    public ResponseEntity<Object> delete(@PathVariable Long tourId) {
        tourService.deleteById(tourId);

        return ResponseHandler.successResponse(
                "Tour deleted", HttpStatus.OK
        );
    }
}
