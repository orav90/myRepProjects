package com.gildedrose;

public class Sulfuras extends ItemInt {

    public Sulfuras(Item item) {
        super(item);
        item.quality = Math.min(item.quality,80);
    }

    @Override
    void updateItem() {

    }
}
