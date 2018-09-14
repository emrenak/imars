package shopping;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import shopping.shop.Asset;

@ConfigurationProperties("asset")
public class AssetProperties {
	/*TODO , read nested configuration from yml file*/
	private Map<String,Asset> assets;

	public Map<String, Asset> getAssets() {
		return assets;
	}

	public void setAssets(Map<String, Asset> assets) {
		this.assets = assets;
	}	
}
