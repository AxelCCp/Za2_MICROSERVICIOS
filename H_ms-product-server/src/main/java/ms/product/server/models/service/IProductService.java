package ms.product.server.models.service;

import java.util.List;

import ms.product.server.entity.Product;

public interface IProductService {

	
	public List<Product> findAllProducts();
	public Product findProductById(Long id);
	public Product save(Product product);
	public void deleteById(Long id);
	
	
}
