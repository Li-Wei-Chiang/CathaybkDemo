package com.example.demo.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	
    @Override
    List<Currency> findAll(Sort sort);
    
    @Query(value = "select * from Currency where code = ?1", nativeQuery = true)
    Currency findByCode(String code);
	
    @Modifying
    @Query(value = "update Currency c set c.rate = ?2, c.update_time = ?3 where c.code = ?1", nativeQuery = true)
    void updateRate(String code, BigDecimal rate, Date update_time);
    
    @Modifying
    @Query(value = "delete from Currency where code = ?1", nativeQuery = true)
    void deleteByCode(String code);
}
