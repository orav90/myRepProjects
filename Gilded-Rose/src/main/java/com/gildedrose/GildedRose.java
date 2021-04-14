package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        ItemFactory factory = new ItemFactory();
        for(Item item : items){
            factory.generate(item).updateItem();
        }
    }
}