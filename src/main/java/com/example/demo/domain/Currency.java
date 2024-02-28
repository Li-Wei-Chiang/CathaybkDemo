package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Currency")
public class Currency {
    // private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "貨幣代碼")
    @Column(name = "CODE")
    private String code;
    
	@Schema(description = "貨幣中文")
    @Column(name = "CHINESE_NAME")
	private String chinese_name;
    
	@Schema(description = "貨幣符號")
    @Column(name = "SYMBOL")
	private String symbol;
    
	@Schema(description = "貨幣英文")
    @Column(name = "DESCRIPTION")
	private String description;
    
	@Schema(description = "貨幣匯率")
    @Column(length=100000, scale=3, name = "RATE")
	private BigDecimal rate;
    
	@Schema(description = "更新時間")
    @Column(name = "UPDATE_TIME")
	private Date update_time;

    public Currency() {
    }
    
    public Currency(String code, String chinese_name, String symbol, String description, BigDecimal rate, Date update_time) {
    	this.code = code;
    	this.chinese_name = chinese_name;
    	this.symbol = symbol;
    	this.description = description;
    	this.rate = rate;
    	this.update_time = update_time;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChinese_name() {
		return chinese_name;
	}

	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
