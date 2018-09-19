package shopping.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopping.AssetProperties;
import shopping.exception.AssetNotAddedException;
import shopping.exception.NotEnoughMoneyException;
import shopping.exception.ShoppingServiceGenericException;
import shopping.service.ShoppingService;

import com.imars.core.domain.Asset;
import com.imars.core.domain.MusicianWealth;
import com.imars.core.service.musicianWealth.MusicianWealthClientService;

@Service
public class ShoppingServiceImpl implements ShoppingService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MusicianWealthClientService musicianWealthClientService;
	
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
				long money = Long.parseLong(wealth.getAssetList().get("money"));
				if(money >= totalPrice){
					addAssets(email, assetToBuy.getName(), quantity);
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
			com.imars.core.domain.Asset myAsset = new com.imars.core.domain.Asset();
			myAsset.setName(nameValue[0]);
			myAsset.setValue(Integer.parseInt(nameValue[1]));
			myAsset.setCategory(category);
			AssetProperties.getSHOP().add(myAsset);
		}
	}
	
	private MusicianWealth getWealth(String email) throws ShoppingServiceGenericException{
		logger.trace("inside getWealth :" + email);
		try {
			return musicianWealthClientService.getWealth(email, assetProperties.getWealthServiceUrl());
		} catch (Exception e) {
			throw new ShoppingServiceGenericException("wealth could not be taken: " + email);
		}
	}
	
	private void addAssets(String email, String asset, int quantity) throws AssetNotAddedException{
		logger.trace("inside addAssets :" + email);
		try {
			musicianWealthClientService.addAssets(email, asset, quantity, assetProperties.getWealthServiceUrl());
		} catch (Exception e) {
			throw new AssetNotAddedException("Asset could not be added");
		}
	}
	

}
