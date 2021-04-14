package com.gildedrose;

public class UnclassifiedItem extends ItemInt {
    public UnclassifiedItem(Item item) {
        super(item);
        item.quality = Math.min(item.quality,50);
    }

    @Override
    void updateItem() {

        if(item.quality > 0){
            item.quality--;
        }
        item.sellIn--;

        if(item.sellIn <= 0 && item.quality > 0){
            item.quality--;
        }
    }
}
