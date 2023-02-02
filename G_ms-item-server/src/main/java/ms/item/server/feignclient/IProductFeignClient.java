package ms.item.server.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ms.item.server.models.entity.Product;



@FeignClient(name="ms-products-server")
public interface IProductFeignClient {
	
	@GetMapping
	public List<Product> getProductList();
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id);
	
	@PostMapping("/create")
	public Product crear(@RequestBody Product product);
	
	@PutMapping("/edit/{id}")
	public Product edit(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id);

}
