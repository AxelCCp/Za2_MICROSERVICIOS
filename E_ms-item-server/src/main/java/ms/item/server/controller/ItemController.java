package ms.item.server.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ms.item.server.models.entity.Item;
import ms.item.server.models.entity.Product;
import ms.item.server.models.service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;



@RestController
public class ItemController {

	@GetMapping
	public List<Item> getProductList(){
		return itemService.getProductItemList();
	}
	
																														//HERE i USE DE CIRCUIT BREAKER WITH PROGRAMATION.
	@GetMapping("/id/{id}/q/{q}")																						// THE "q" ID THE QUANTITY OF PRODUCTS FOR ITEM.
	public Item getItem(@PathVariable Long id, @PathVariable Integer q) {	
		return cbFactory.create("items")
		.run(()-> itemService.getItemById(id, q), e -> alternativeMethod1(id, q, e));		
	}
	
																														//HERE i USE DE CIRCUIT BREAKER WITH ANNOTATION.
	@CircuitBreaker(name="items",fallbackMethod="alternativeMethod1")
	@GetMapping("/idx/{id}/qx/{q}")	
	public Item getItem2(@PathVariable Long id, @PathVariable Integer q) {
		return itemService.getItemById(id, q);		
	}
	
	public Item alternativeMethod1(Long id, Integer quantity, Throwable e) {
		logger.info("CIRCUIT BREAKER using the alternativeMethod1 " + e.getMessage());
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
	
	
	@CircuitBreaker(name="itemsYml",fallbackMethod="alternativeMethod2")
	@TimeLimiter(name="itemsYml")
	@GetMapping("/idy/{id}/qy/{q}")	
	public CompletableFuture <Item> getItem3(@PathVariable Long id, @PathVariable Integer q) {
		return CompletableFuture.supplyAsync(() -> itemService.getItemById(id, q));		
	}
	
	public CompletableFuture <Item> alternativeMethod2(Long id, Integer quantity, Throwable e) {
		logger.info("CIRCUIT BREAKER using the alternativeMethod2 " + e.getMessage());
		Item item = new Item();
		Product product = new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setSku("xxxxxxxxxxx");
		product.setName("Unnamed. Record not found!");
		product.setPrice(0.00);
		product.setOrigin("Unknown origin");
		item.setProduct(product);
		return CompletableFuture.supplyAsync(()->item);
	}
																													
	
	
	
	@Autowired
	//@Qualifier("ItemServiceRestTemplateImpl")
	@Qualifier("ItemServiceFeignImpl")
	private IItemService itemService;
	
	@Autowired
	private CircuitBreakerFactory<?, ?> cbFactory; 
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	
}
