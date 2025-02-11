package com.sportsshoes.repo;

import com.sportsshoes.bean.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCategory(String category);

    @Query("SELECT p FROM Purchase p WHERE p.date >= :from AND p.date <= :to")
    List<Purchase> findByDates(@Param("from") Date from, @Param("to") Date to);
}
