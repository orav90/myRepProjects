package com.gildedrose;

public class AgedBrie extends ItemInt {
    public AgedBrie(Item item) {
        super(item);
        item.quality = Math.min(item.quality,50);
    }

    @Override
    void updateItem() {
        if(item.quality < 50){
            item.quality++;
        }
        item.sellIn--;
        if(item.quality < 50 && item.sellIn <= 0){
            item.quality++;
        }
    }
}
