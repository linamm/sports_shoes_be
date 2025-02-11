package com.sportsshoes.controller;

import com.sportsshoes.bean.Purchase;
import com.sportsshoes.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {
    @Autowired
    private PurchaseService service;

    @PostMapping("/order")
    public String order(@RequestBody Purchase purchase) {
        return service.order(purchase);
    }

    @GetMapping("/reports/{category}")
    public List<Purchase> getReportsByCategory(@PathVariable String category) {
        return service.getPurchasesByCategory(category);
    }

    @GetMapping("/reports/dates")
    public List<Purchase> getPurchasesByDates(@RequestParam("from")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                              @RequestParam("to")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return service.getPurchasesByDates(from, to);
    }
}