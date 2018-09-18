package shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shopping.exception.AssetNotAddedException;
import shopping.exception.NotEnoughMoneyException;
import shopping.exception.ShoppingServiceGenericException;
import shopping.service.ShoppingService;

@RestController
public class ShoppingController {
	
	@Autowired
	ShoppingService shoppingService;

	@RequestMapping("/shopping/buy")
	public void buy(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="asset", defaultValue="") String asset,
			@RequestParam(value="quantity", defaultValue="") int quantity) throws AssetNotAddedException, NotEnoughMoneyException, ShoppingServiceGenericException{
		shoppingService.buy(email, asset, quantity);
	}
}
