package ms.item.server.models.service;

import java.util.List;

import ms.item.server.models.entity.Item;


public interface IItemService {

	
	public List<Item> getProductItemList();

	public Item getItemById(Long id, Integer quantity);
	
}
