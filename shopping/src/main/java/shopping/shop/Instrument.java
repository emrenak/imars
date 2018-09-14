package shopping.shop;

public class Instrument{
	private String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Instrument [value=" + value + "]";
	}
	
}
