package ms.item.server.models.service;

import java.util.List;

import ms.commons.server.models.entity.Product;
import ms.item.server.models.entity.Item;

public interface IItemService {

	
	public List<Item> getProductItemList();

	public Item getItemById(Long id, Integer quantity);
	
	public Product crear(Product product);
	
	public Product edit(Product product, Long id);
	

	public void deleteById(Long id);
	
}
