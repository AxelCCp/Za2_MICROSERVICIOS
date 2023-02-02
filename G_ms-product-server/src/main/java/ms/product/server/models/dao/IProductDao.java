package ms.product.server.models.dao;

import org.springframework.data.repository.CrudRepository;

import ms.product.server.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
