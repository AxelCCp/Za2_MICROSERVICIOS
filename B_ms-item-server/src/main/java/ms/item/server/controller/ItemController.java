package ms.item.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ms.item.server.models.entity.Item;
import ms.item.server.models.service.IItemService;



@RestController
public class ItemController {

	
	@GetMapping
	public List<Item> getProductList(){
		return ItemService.getProductItemList();
	}
	
	
	@GetMapping("/id/{id}/q/{q}")// LA "q" ES DE CANTIDAD DE PRODUCTOS POR ITEM.
	public Item getItem(@PathVariable Long id, @PathVariable Integer q) {
		return ItemService.getItemById(id, q);	
	}
	
	
	@Autowired
	//@Qualifier("ItemServiceRestTemplateImpl")
	@Qualifier("ItemServiceFeignImpl")
	private IItemService ItemService;
	
	
}
