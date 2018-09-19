package com.imars.core.service.musicianWealth.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.imars.core.domain.MusicianWealth;
import com.imars.core.service.musicianWealth.MusicianWealthClientService;

@Service
public class MusicianWealthClientServiceImpl implements MusicianWealthClientService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public MusicianWealth getWealth(String email, String serviceUrl) throws Exception {
		logger.trace("inside core getWealth :" + email);
		try {
			RestTemplate restTemplate = new RestTemplate();
	        MusicianWealth musicianWealth = restTemplate.getForObject(serviceUrl+"/get/?email={email}", MusicianWealth.class, email);
	        return musicianWealth;
		} catch (Exception e) {
			throw new Exception("wealth could not be taken: " + email);
		}
	}

	public void addAssets(String email, String asset, int quantity,
			String serviceUrl) throws Exception {
		logger.trace("inside core addAssets :" + email);
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForObject(serviceUrl+"/addAsset/?email={email}&asset={asset}&numOfAsset={quantity}", MusicianWealth.class, email,asset,quantity);
			logger.info("email:" + email + ", number of " + quantity  + " " + asset + " assets are added");
		} catch (Exception e) {
			throw new Exception("Asset could not be added");
		}
		
	}

}
