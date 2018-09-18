package shopping.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;





import org.springframework.web.client.RestTemplate;

import com.imars.core.domain.MusicianWealth;

import shopping.AssetProperties;
import shopping.exception.AssetNotAddedException;
import shopping.exception.NotEnoughMoneyException;
import shopping.exception.ShoppingServiceGenericException;
import shopping.service.ShoppingService;
import shopping.shop.Asset;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	AssetProperties assetProperties;
	
	public ShoppingServiceImpl(AssetProperties assetProperties) {
		super();
		this.assetProperties = assetProperties;
	}


	public void buy(String email, String asset, int quantity) throws AssetNotAddedException, NotEnoughMoneyException, ShoppingServiceGenericException {
		logger.trace("inside buy :" + email);
		List<Asset> assetList = AssetProperties.getSHOP();
		for (Asset assetToBuy : assetList) {
			if(assetToBuy.getName().equals(asset)){
				int value = assetToBuy.getValue();
				int totalPrice = value * quantity;
				MusicianWealth wealth = getWealth(email);
				if(wealth.getMoney() >= totalPrice){
					//TODO when detailed information could be kept in wealth service, change the method invocation below, instead of category send the asset itself
					addAssets(email, assetToBuy.getCategory(), quantity);
					break;
				}else{
					throw new NotEnoughMoneyException("not enough money");
				}
			}
		}
	}
	
	@PostConstruct
	public void initShop(){
		addAssets(assetProperties.getInstruments(), com.imars.core.utils.ImarsEnums.Asset.INSTRUMENTS.getValue());
		addAssets(assetProperties.getEstates(), com.imars.core.utils.ImarsEnums.Asset.HOUSE.getValue());
		addAssets(assetProperties.getCars(), com.imars.core.utils.ImarsEnums.Asset.CAR.getValue());
		logger.info(assetProperties.toString());
	}
	
	public void addAssets(List<String> assetList, String category){
		for (String asset : assetList) {
			String[] nameValue = asset.split("\\.");
			shopping.shop.Asset myAsset = new shopping.shop.Asset();
			myAsset.setName(nameValue[0]);
			myAsset.setValue(Integer.parseInt(nameValue[1]));
			myAsset.setCategory(category);
			AssetProperties.getSHOP().add(myAsset);
		}
	}
	
	//TODO consider implementing generic services for consuming rest services
	private MusicianWealth getWealth(String email) throws ShoppingServiceGenericException{
		logger.trace("inside getWealth :" + email);
		try {
			RestTemplate restTemplate = new RestTemplate();
	        MusicianWealth musicianWealth = restTemplate.getForObject(assetProperties.getWealthServiceUrl()+"/get/?email={email}", MusicianWealth.class, email);
	        return musicianWealth;
		} catch (Exception e) {
			throw new ShoppingServiceGenericException("wealth could not be taken: " + email);
		}
	}
	
	private void addAssets(String email, String asset, int quantity) throws AssetNotAddedException{
		logger.trace("inside addAssets :" + email);
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForObject(assetProperties.getWealthServiceUrl()+"/addAsset/?email={email}&asset={asset}&numOfAsset={quantity}", MusicianWealth.class, email,asset,quantity);
			logger.info("email:" + email + ", number of " + quantity  + " " + asset + " assets are added");
		} catch (Exception e) {
			throw new AssetNotAddedException("Asset could not be added");
		}
	}
	

}
