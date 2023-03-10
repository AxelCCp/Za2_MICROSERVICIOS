package ms.item.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ms.item.server.models.entity.Item;
import ms.item.server.models.entity.Product;
import ms.item.server.models.service.IItemService;



@RestController
public class ItemController {

	
	@GetMapping
	public List<Item> getProductList(){
		return ItemService.getProductItemList();
	}
	
	@HystrixCommand(fallbackMethod="alternativeMethod2")
	@GetMapping("/id/{id}/q/{q}")// LA "q" ES DE CANTIDAD DE PRODUCTOS POR ITEM.
	public Item getItem(@PathVariable Long id, @PathVariable Integer q) {
		return ItemService.getItemById(id, q);	
	}
	
	public Item alternativeMethod2(Long id, Integer quantity) {
		Item item = new Item();
		Product product = new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setSku("xxxxxxxxxxx");
		product.setName("Unnamed. Record not found!");
		product.setPrice(0.00);
		product.setOrigin("Unknown origin");
		item.setProduct(product);
		return item;
	}
	
	
	
	@Autowired
	//@Qualifier("ItemServiceRestTemplateImpl")
	@Qualifier("ItemServiceFeignImpl")
	private IItemService ItemService;
	
	
}
