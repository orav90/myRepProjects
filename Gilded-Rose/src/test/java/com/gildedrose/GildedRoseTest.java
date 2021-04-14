package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void ageBrieIncreaseQualityTest(){
        Item[] items = new Item[] { new Item(Constants.AGED_BRIE, -1, 46) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(48, app.items[0].quality);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void sulfurasMaxQualityTest(){
        Item[] items = new Item[] { new Item(Constants.SULFURAS, -1, 90) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    public void backstageIncreaseQualityTest(){
        Item[] items = new Item[] { new Item(Constants.BACKSTAGE_PASSES, 2, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(33, app.items[0].quality);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void unclassifiedIncreaseQualityTest(){
        Item[] items = new Item[] { new Item("any", 1, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(28, app.items[0].quality);
        app.updateQuality();
        assertEquals(26, app.items[0].quality);
    }

    @Test
    public void conjuredIncreaseQualityTest(){
        Item[] items = new Item[] { new Item(Constants.CONJURED, 1, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(26, app.items[0].quality);
        app.updateQuality();
        assertEquals(22, app.items[0].quality);
    }


}
