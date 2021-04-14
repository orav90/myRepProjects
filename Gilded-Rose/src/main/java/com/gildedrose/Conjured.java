package com.gildedrose;

public class Conjured extends ItemInt {

    public Conjured(Item item) {
        super(item);
        item.quality = Math.min(item.quality,50);
    }

    @Override
    void updateItem() {
        if(item.quality < 50){
            item.quality -=2;
        }

        item.sellIn--;
        if(item.sellIn <=0 && item.quality > 0){
            item.quality -=2;
        }

        if(item.quality < 0){
            item.quality = 0;
        }


    }
}
