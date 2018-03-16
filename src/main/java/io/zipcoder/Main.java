package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        ItemParser itemParser = new ItemParser();
        ArrayList<String> delimitedItems = itemParser.parseRawDataIntoStringArray(output);
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (String item:delimitedItems) {
            Item newItem = (itemParser.parseStringIntoItem(item));
            System.out.println(newItem);
            itemList.add(newItem);
        }
        System.out.println(output);
        // TODO: parse the data in output into items, and display to console.
    }
}
