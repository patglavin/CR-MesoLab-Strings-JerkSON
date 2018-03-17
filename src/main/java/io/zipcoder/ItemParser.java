package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    private int itemCount;
    private int errorCount;
    private HashMap<String, HashMap<Double, Integer>> listMap = new HashMap<String, HashMap<Double, Integer>>();

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        this.itemCount = response.size();
        return response;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getErrorCount(){
        return errorCount;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
        Pattern name = Pattern.compile("(?i)name:(\\w*)[;|^%*!@]price:(\\d*\\.*\\d*)[;|^%*!@]type:(\\w*)[;|^%*!@]expiration:(\\d*\\/\\d*\\/\\d*)");
        Matcher matcher = name.matcher(rawItem);
        String itemName = "";
        String itemPrice = "";
        String itemType = "";
        String itemExpiration = "";
        while (matcher.find()) {
            try {
                itemName = matcher.group(1);
                itemName = itemName.replace("0", "o");
                if (itemName.equals("")) errorCount++;
            } catch (Exception e){
                throw new ItemParseException();
            }
            if (matcher.group(2).equals("")) {
                errorCount++;
                itemPrice = "0.0";
            } else {
                itemPrice = matcher.group(2);
            }
            try {
                itemType = matcher.group(3);
                if (itemType.equals("")) errorCount++;
            } catch (Exception e){
                throw new ItemParseException();
            }
            try {
                itemExpiration = matcher.group(4);
                if (itemExpiration.equals("")) errorCount++;
            } catch (Exception e){
                throw new ItemParseException();
            }
        }
        Item tempItem = new Item(itemName.toLowerCase(), Double.parseDouble(itemPrice), itemType.toLowerCase(), itemExpiration);
        buildList(tempItem);
        return tempItem;
    }

    //pretty sure i don't need this??
    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    private void buildList(Item item){
        if (item.getName().equals("")) return;
        if (!listMap.containsKey(item.getName())){
            listMap.put(item.getName(), new HashMap<Double, Integer>());
            listMap.get(item.getName()).put(item.getPrice(), 1);
        } else {
            if (!listMap.get(item.getName()).containsKey(item.getPrice())) {
                listMap.get(item.getName()).put(item.getPrice(), 1);
            } else {
                Integer occurence = listMap.get(item.getName()).get(item.getPrice());
                listMap.get(item.getName()).put(item.getPrice(), occurence + 1);
            }
        }
    }

    private int total(HashMap<Double, Integer> map){
        int total = 0;
        for (Integer integer:map.values()) {
            total += integer;
        }
        return total;
    }

    public void listifier(){
        System.out.println(listMap.toString());
        for (String name:listMap.keySet()) {
            System.out.println("\n\nName: " + name + " Seen: " + total(listMap.get(name)) + "\n================================");
            for (Map.Entry<Double, Integer> entry:listMap.get(name).entrySet()) {
                System.out.println("Price: " + entry.getKey() + " Seen: " + entry.getValue() + "\n-----------------------------");
            }
        }
        System.out.println("\n\n ERRORS: " + getErrorCount());
    }



}
