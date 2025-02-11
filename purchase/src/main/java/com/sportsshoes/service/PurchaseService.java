package com.sportsshoes.service;

import com.sportsshoes.bean.Purchase;
import com.sportsshoes.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getPurchasesByCategory(String category) {
        return purchaseRepository.findByCategory(category);
    }

    public String order(Purchase purchase) {
        purchaseRepository.save(purchase);
        return "Order placed successfully";
    }

    public List<Purchase> getPurchasesByDates(Date from, Date to) {
        return purchaseRepository.findByDates(from, to);
    }
}
