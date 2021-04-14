package com.gildedrose;

public class BackstagePasses extends ItemInt {
    public BackstagePasses(Item item) {
        super(item);
        item.quality = Math.min(item.quality,50);
    }

    @Override
    void updateItem() {
        if(item.quality < 50){
            item.quality++;
            if(item.quality < 50 && item.sellIn < 11)
                item.quality++;
            if(item.quality < 50 && item.sellIn < 6)
                item.quality++;
        }

        item.sellIn--;
        if(item.sellIn <= 0){
            item.quality = 0;
        }
    }
}
