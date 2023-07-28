package project.main.webstore.domain.item.stub;

import project.main.webstore.domain.item.entity.Item;

public class ItemStub {

    public Item createItem(Long itemId){
        return new Item(itemId);
    }
}
