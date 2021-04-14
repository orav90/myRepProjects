package com.gildedrose;

public class ItemFactory {

    public ItemInt generate(Item item){

        ItemInt generatedItem = null;
        switch(item.name){
            case Constants.CONJURED:{
                generatedItem = new Conjured(item);
                break;
            }
            case Constants.SULFURAS:{
                generatedItem = new Sulfuras(item);
                break;
            }
            case Constants.BACKSTAGE_PASSES:{
                generatedItem = new BackstagePasses(item);
                break;
            }
            case Constants.AGED_BRIE:{
                generatedItem = new AgedBrie(item);
                break;
            }
            default:{
                generatedItem = new UnclassifiedItem(item);
                break;
            }
        }
        return generatedItem;
    }
}
