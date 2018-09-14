package shopping.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import shopping.AssetProperties;
import shopping.service.ShoppingService;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	AssetProperties assetProperties;
	
	public ShoppingServiceImpl(AssetProperties assetProperties) {
		super();
		this.assetProperties = assetProperties;
	}


	public void buy(String email, String asset, int quantity) {
		// Here, consider a catalog for the items that are purchasable
	}
	
	@PostConstruct
	public void initShop(){
		logger.info(assetProperties.toString());
	}

}
