package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.example.demo.domain.Currency;


public interface CurrencyService {
    List<Currency> findAll(Sort sort);
    
    Currency findByCode(String code);
    
    Currency save(Currency currency);
    
    void updateRate(String code, BigDecimal rate, Date update_time);
    
    void deleteByCode(String code);

}
