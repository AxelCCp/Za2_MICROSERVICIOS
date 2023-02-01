package ms.item.server.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ms.item.server.models.entity.Product;



@FeignClient(name="ms-products-server", url="localhost:8001")
public interface IProductFeignClient {
	
	@GetMapping
	public List<Product> getProductList();
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id);

}
