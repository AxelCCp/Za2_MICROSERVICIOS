package ms.product.server.models.dao;

import org.springframework.data.repository.CrudRepository;

import ms.commons.server.models.entity.Product;


public interface IProductDao extends CrudRepository<Product, Long> {

}
