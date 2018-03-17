package io.zipcoder;

public class Entry {
    Double price;
    Integer occurence;

    public Double getPrice() {
        return price;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public Entry(Double price, Integer occurence){
        this.price = price;
        this.occurence = occurence;
    }
}
