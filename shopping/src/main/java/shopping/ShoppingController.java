package shopping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingController {

	@RequestMapping("/shopping/buy")
	public void buy(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="asset", defaultValue="") String asset,
			@RequestParam(value="quantity", defaultValue="") int quantity){
	}
}
