package shopping.service;

import shopping.exception.AssetNotAddedException;
import shopping.exception.NotEnoughMoneyException;
import shopping.exception.ShoppingServiceGenericException;

public interface ShoppingService {
	
	public void buy(String email, String asset, int quantity) throws AssetNotAddedException, NotEnoughMoneyException, ShoppingServiceGenericException;
}
