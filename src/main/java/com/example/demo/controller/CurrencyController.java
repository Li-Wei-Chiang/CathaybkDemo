package com.example.demo.controller;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.Currency;
import com.example.demo.service.CurrencyService;
import com.example.demo.translator.Translator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


//@RestController
@Controller
@RequestMapping("/api")
public class CurrencyController {
	private static final Logger logger = Logger.getLogger("CurrencyController");
	private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	private static final String GET_CURRENCY_ENDPOINT_URL = "http://localhost:8080/api/currency";
	private static final String CREATE_CURRENCY_ENDPOINT_URL = "http://localhost:8080/api/currency";
	private static final String UPDATE_CURRENCY_ENDPOINT_URL = "http://localhost:8080/api/currency/{code}";
	
	static {
		init();
	} 
	private static void init() {
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);

        FileHandler fh;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
        	fh = new FileHandler("app-"+ format.format(Calendar.getInstance().getTime()) + ".log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	
	@Autowired
	private CurrencyService currencyService;
	
	
    @GetMapping("/currencylist")
    public String showCurrencyList(Model model) {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<Currency[]> responseEntity = restTemplate.getForEntity(GET_CURRENCY_ENDPOINT_URL, Currency[].class);
    	Currency[] currencys = responseEntity.getBody();
    	model.addAttribute("currencys",currencys);

        return "CurrencyList";
    }

	@Operation(summary = "取得所有貨幣資訊") 
    @ResponseBody
    @GetMapping("/currency")
    public List<Currency> findAll() {
    	Sort sort = Sort.by(Sort.Direction.ASC, "code");
        return currencyService.findAll(sort);
    }
    
	@Operation(summary = "新增貨幣資訊")
    @ResponseBody
    @PostMapping("/currency")
    public Currency saveCurrency(@RequestBody Currency currency) {
        return currencyService.save(new Currency(currency.getCode(), currency.getChinese_name(),currency.getSymbol(),
        										 currency.getDescription(),currency.getRate(),currency.getUpdate_time()));
    }

	
	@Operation(summary = "更新貨幣匯率")
    @ResponseBody
    @PutMapping("/currency/{code}")
    public Currency updateRate(@Parameter(description = "貨幣代碼", example = "USD") @PathVariable String code, @RequestBody Currency currency) {
        Currency _currency = currencyService.findByCode(code);
        _currency.setRate(currency.getRate());
        _currency.setUpdate_time(currency.getUpdate_time());
            
        return currencyService.save(_currency);
    }
    
	@Operation(summary = "刪除貨幣資訊")
    @ResponseBody
    @DeleteMapping("/currency/{code}")
    public ResponseEntity<String> deleteByCode(@Parameter(description = "貨幣代碼", example = "USD") @PathVariable String code) {
    	currencyService.deleteByCode(code);
    	return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
	
	@Operation(summary = "取得COINDESK DATA")
    @ResponseBody
    @GetMapping("/coindesk")
    public List<Currency> coindeskInfo() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(COINDESK_API_URL, String.class);
        logger.info("Response status: " + responseEntity.getStatusCode() + ".\nResponse Body: " + responseEntity.getBody());
        
        String jsonString = responseEntity.getBody();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);


        JSONObject time = (JSONObject) jsonObject.get("time");
		Object updatedtime = time.get("updatedISO");
        
		JSONObject bpi = (JSONObject) jsonObject.get("bpi");
		

	    for (Object keyStr : bpi.keySet()) {
	    	JSONObject data = (JSONObject) bpi.get(keyStr);
			
	    	Currency _c = new Currency();
			_c.setCode((String) keyStr);
			
			_c.setChinese_name(Translator.translate("en","zh-TW",(String) keyStr));
			
			String _symbol = java.util.Currency.getInstance((String) keyStr).getSymbol();
			_c.setSymbol(_symbol);
			
			_c.setDescription((String) data.get("description"));
			
			BigDecimal _rate = BigDecimal.valueOf(((Double) data.get("rate_float")).doubleValue());
			_c.setRate(_rate);
			
			SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Date _date = isoFormat.parse(((String) updatedtime).replaceAll("\\+0([0-9]){1}\\:00", "+0$100"));
			_c.setUpdate_time(_date);
		    
			if (currencyService.findByCode((String) keyStr) == null) {
			    restTemplate.postForObject(CREATE_CURRENCY_ENDPOINT_URL, _c, Currency.class);
			} else {
			    Map<String, String> params = new HashMap<String, String>();
				params.put("code", (String) keyStr);
			    restTemplate.put(UPDATE_CURRENCY_ENDPOINT_URL, _c, params);
			}

	    }
	    
	    return this.findAll();
    }
}
