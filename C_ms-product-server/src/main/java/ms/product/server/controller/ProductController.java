package ms.product.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public Product getProductById(@PathVariable Long id) {
		Product producto = productService.findProductById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
		/*
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		return producto;
	}
	
	@Autowired
	private IProductService productService;
	@Autowired
	private Environment env;
	
}
