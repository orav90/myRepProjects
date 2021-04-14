package com.gildedrose;

public abstract class ItemInt {

    protected Item item;
    public ItemInt(Item item){
        this.item = item;
    }

    abstract void updateItem();

}
