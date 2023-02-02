package ms.product.server.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ms.product.server.entity.Product;
import ms.product.server.models.service.IProductService;

@RestController
public class ProductController {
	
	
	@GetMapping
	public List<Product> getProductList(){
		return  productService.findAllProducts().stream().map(p -> {
			p.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return p;
		}).collect(Collectors.toList());
	}
	
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) throws InterruptedException {
		Product producto = productService.findProductById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
				if(id.equals(10L)) {
					throw new IllegalStateException("Product not found! :O");
				}
				if(id.equals(7L)) {
					TimeUnit.SECONDS.sleep(5L);
				}
		return producto;
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product crear(@RequestBody Product product) {
		return productService.save(product);
	}	
	
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id){
		Product productDb = productService.findProductById(id);
		productDb.setSku(product.getSku());
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		productDb.setOrigin(product.getOrigin());
		return productService.save(productDb);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		productService.deleteById(id);
	}
	
	
	@Autowired
	private IProductService productService;
	@Autowired
	private Environment env;
	
}
