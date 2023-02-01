package ms.product.server.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.product.server.entity.Product;
import ms.product.server.models.dao.IProductDao;

@Service
public class ProductServiceImpl implements IProductService{

	@Override
	public List<Product> findAllProducts() {
		return (List<Product>) productDao.findAll();
	}

	@Override
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}
	
	
	@Autowired
	private IProductDao productDao;

}
