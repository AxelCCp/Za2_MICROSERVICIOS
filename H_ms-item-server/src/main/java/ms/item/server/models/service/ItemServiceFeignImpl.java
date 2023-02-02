package ms.item.server.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.item.server.feignclient.IProductFeignClient;
import ms.item.server.models.entity.Item;
import ms.item.server.models.entity.Product;

@Service("ItemServiceFeignImpl")
public class ItemServiceFeignImpl implements IItemService {

	@Override
	public List<Item> getProductItemList() {
		log.info("Connection with Feign Client, Microservice ms-product-server and ms-item-server");
		return feingClient.getProductList().stream().map(p-> new Item(p,1)).collect(Collectors.toList());
	}

	@Override
	public Item getItemById(Long id, Integer quantity) {
		Product product = feingClient.getProductById(id);
		Item item = new Item(product, quantity);
		log.info("Connection with Feign Client, Microservice ms-product-server and ms-item-server");
		return  item;
	}

	@Override
	public Product crear(Product product) {
		log.info("Connection with Feign Client, Microservice ms-product-server and ms-item-server. Creating a new product.");
		return feingClient.crear(product);
	}

	@Override
	public Product edit(Product product, Long id) {
		log.info("Connection with Feign Client, Microservice ms-product-server and ms-item-server. Updating an existing product.");
		return feingClient.edit(product, id);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Connection with Feign Client, Microservice ms-product-server and ms-item-server. Deleting a product from the database.");
		feingClient.deleteById(id);
	}
	
	@Autowired
	private IProductFeignClient feingClient;
	private Logger log = LoggerFactory.getLogger(ItemServiceRestTemplateImpl.class);
	
	
	
}
