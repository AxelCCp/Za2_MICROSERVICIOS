package ms.item.server.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ms.commons.server.models.entity.Product;
import ms.item.server.models.entity.Item;


@Service("ItemServiceRestTemplateImpl")
public class ItemServiceRestTemplateImpl implements IItemService {

	@Override
	public List<Item> getProductItemList() {
		List<Product>products = Arrays.asList(restTemplateClient.getForObject("http://ms-products-server", Product[].class));
		log.info("Connection with Rest Template, Microservice ms-product-server and ms-item-server");
		return products.stream().map(p-> new Item(p,1)).collect(Collectors.toList());
	}

	@Override
	public Item getItemById(Long id, Integer quantity) {
		Map <String,String> pathVariables = new HashMap <String,String>();
		pathVariables.put("id", id.toString());
		Product product = restTemplateClient.getForObject("http://ms-products-server/{id}", Product.class, pathVariables);
		log.info("Connection with Rest Template, Microservice ms-product-server and ms-item-server");
		return new Item(product, quantity);
	}
	
	
	@Override
	public Product crear(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = restTemplateClient.exchange("http://ms-products-server/create", HttpMethod.POST, body, Product.class);
		Product productResponse = response.getBody();
		log.info("Connection with RestTemplate Client, Microservice ms-product-server and ms-item-server. Creating a new product.");
		return productResponse;
	}

	@Override
	public Product edit(Product product, Long id) {
		Map <String,String> pathVariables = new HashMap <String,String>();
		pathVariables.put("id", id.toString());
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = restTemplateClient.exchange("http://ms-products-server/edit/{id}", HttpMethod.PUT, body, Product.class, pathVariables);
		Product productResponse = response.getBody();
		log.info("Connection with RestTemplate Client, Microservice ms-product-server and ms-item-server. Updating an existing product.");
		return productResponse;
	}

	@Override
	public void deleteById(Long id) {
		Map <String,String> pathVariables = new HashMap <String,String>();
		pathVariables.put("id", id.toString());
		restTemplateClient.delete("http://ms-products-server/delete/{id}", pathVariables);
		log.info("Connection with RestTemplate Client, Microservice ms-product-server and ms-item-server. Deleting a product from the database.");
	}

	
	@Autowired
	private RestTemplate restTemplateClient;
	private Logger log = LoggerFactory.getLogger(ItemServiceRestTemplateImpl.class);
	
	
}
