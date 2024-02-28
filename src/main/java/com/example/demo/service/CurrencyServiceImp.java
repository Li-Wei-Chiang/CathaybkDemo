package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Currency;
import com.example.demo.repository.CurrencyRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CurrencyServiceImp implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;


	@Override
	public List<Currency> findAll(Sort sort) {
		return this.currencyRepository.findAll(sort);
	}
	
    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }
	
	@Override
	public Currency save(Currency currency) {
		return currencyRepository.save(currency);
	}


	@Override
	public void updateRate(String code, BigDecimal rate, Date update_time) {
		currencyRepository.updateRate(code, rate, update_time);
	}


	@Override
	public void deleteByCode(String code) {
		currencyRepository.deleteByCode(code);
	}

}
