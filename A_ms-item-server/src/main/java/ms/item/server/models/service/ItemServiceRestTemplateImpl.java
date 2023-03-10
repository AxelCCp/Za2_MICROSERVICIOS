package ms.item.server.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ms.item.server.models.entity.Item;
import ms.item.server.models.entity.Product;


@Service("ItemServiceRestTemplateImpl")
public class ItemServiceRestTemplateImpl implements IItemService {

	@Override
	public List<Item> getProductItemList() {
		List<Product>products = Arrays.asList(RestTemplateClient.getForObject("http://localhost:8001", Product[].class));
		log.info("Connection with Rest Template, Microservice ms-product-server and ms-item-server");
		return products.stream().map(p-> new Item(p,1)).collect(Collectors.toList());
	}

	@Override
	public Item getItemById(Long id, Integer quantity) {
		Map <String,String> pathVariables = new HashMap <String,String>();
		pathVariables.put("id", id.toString());
		Product product = RestTemplateClient.getForObject("http://localhost:8001/{id}", Product.class, pathVariables);
		log.info("Connection with Rest Template, Microservice ms-product-server and ms-item-server");
		return new Item(product, quantity);
	}
	
	
	@Autowired
	private RestTemplate RestTemplateClient;
	private Logger log = LoggerFactory.getLogger(ItemServiceRestTemplateImpl.class);
	
}
