package com.example.demo.schedule;


import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ScheduleTask {
	private static final String GET_COINDESK_ENDPOINT_URL = "http://localhost:8080/api/coindesk";
	
    @Scheduled(cron = "0 0/5 * * * ?")
    public void TaskCoindesk() {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(GET_COINDESK_ENDPOINT_URL, String.class);
    	System.out.println("TaskCoindesk Status: " + responseEntity.getStatusCode());
    }
	
}
