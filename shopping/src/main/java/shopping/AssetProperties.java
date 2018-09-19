package shopping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.imars.core.domain.Asset;

@Component
@ConfigurationProperties("asset")
public class AssetProperties {
	private static List<Asset> SHOP = new ArrayList<Asset>();
	
	private String wealthServiceUrl;
	private List<String> instruments = new ArrayList<String>();
	private List<String> estates = new ArrayList<String>();
	private List<String> cars = new ArrayList<String>(); 
	public List<String> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<String> instruments) {
		this.instruments = instruments;
	}

	public List<String> getEstates() {
		return estates;
	}

	public void setEstates(List<String> estates) {
		this.estates = estates;
	}

	public List<String> getCars() {
		return cars;
	}

	public void setCars(List<String> cars) {
		this.cars = cars;
	}
	
	public static List<Asset> getSHOP() {
		return SHOP;
	}
	
	public String getWealthServiceUrl() {
		return wealthServiceUrl;
	}

	public void setWealthServiceUrl(String wealthServiceUrl) {
		this.wealthServiceUrl = wealthServiceUrl;
	}

	@Override
	public String toString() {
		return "AssetProperties [instruments=" + instruments + ", estates="
				+ estates + ", cars=" + cars + "]";
	}
	
	
	
}
