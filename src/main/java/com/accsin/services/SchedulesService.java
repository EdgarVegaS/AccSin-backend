package com.accsin.services;


import java.util.Date;
import java.util.List;

import com.accsin.entities.recoveryPasswordEntity;
import com.accsin.repositories.recoveryPasswordRepository;
import com.accsin.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulesService {
	
    @Autowired
    recoveryPasswordRepository recoveryPasswordRepository;

    @Scheduled(cron="0 0/5 * * * ?")
    public void checkValidationCodes() {
    	Date today = new Date();
    	Date beforeYesterday = DateTimeUtils.addDays(today, -2);
    	List<recoveryPasswordEntity> activatedCodes = recoveryPasswordRepository.findAllenabled(today, beforeYesterday);
    	for(recoveryPasswordEntity code : activatedCodes)
    	{
    		Date expirationDate = code.getExpiredAt();
    		if(expirationDate.before(today)) {
    			code.setEnable(false);
    			recoveryPasswordRepository.save(code);
    		}
    	}
    }
    
}
