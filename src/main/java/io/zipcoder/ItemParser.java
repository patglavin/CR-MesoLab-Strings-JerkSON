package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    private int itemCount;
    private int errorCount;

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        this.itemCount = response.size();
        return response;
    }

    public int getItemCount() {
        return itemCount;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
        //Pattern name = Pattern.compile("naMe(\\w*);");
        Pattern name = Pattern.compile("(?i)name:(\\w*)[;|^%*!@]price:(\\d*\\.*\\d*)[;|^%*!@]type:(\\w*)[;|^%*!@]expiration:(\\d*\\/\\d*\\/\\d*)");
        Matcher matcher = name.matcher(rawItem);
        String itemName = "";
        String itemPrice = "";
        String itemType = "";
        String itemExpiration = "";
        while (matcher.find()) {
            try {
                itemName = matcher.group(1);
                if (itemName == "") errorCount++;
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
                if (itemType == "") errorCount++;
            } catch (Exception e){
                throw new ItemParseException();
            }
            try {
                itemExpiration = matcher.group(4);
                if (itemExpiration == "") errorCount++;
            } catch (Exception e){
                throw new ItemParseException();
            }
        }
        return new Item(itemName.toLowerCase(), Double.parseDouble(itemPrice), itemType.toLowerCase(), itemExpiration);
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }



}
