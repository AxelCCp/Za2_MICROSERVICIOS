package ms.item.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ms.item.server.models.entity.Item;
import ms.item.server.models.entity.Product;
import ms.item.server.models.service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RefreshScope
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
		
	
	//with this method you can read the properties configuration files that are on Github.
	@GetMapping("/get-config")
	public ResponseEntity<?>getConfiguration(@Value("${server.port}")String port){
		logger.info(text);
		Map<String,String>json = new HashMap<>();
		json.put("text", text);
		json.put("port", port);
		
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("user.name", env.getProperty("configuration.user.name"));
			json.put("user.email", env.getProperty("configuration.user.email"));
		}
		return new ResponseEntity<Map<String,String>>(json,HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product crear(@RequestBody Product product) {
		return itemService.crear(product);
	}
	
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product editar(@RequestBody Product product, @PathVariable Long id) {
		return itemService.edit(product, id);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.deleteById(id);
	}
	
	
	@Autowired //@Qualifier("ItemServiceFeignImpl")
	@Qualifier("ItemServiceRestTemplateImpl")
	private IItemService itemService;
	
	@Autowired
	private CircuitBreakerFactory<?, ?> cbFactory; 
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Value("${configuration.text}") 
	private String text;
	
	@Autowired
	private Environment env;
	
	
}
