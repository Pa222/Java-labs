package com.example.lab1;

import lombok.Data;

@Data
public class Filters {
    private String searchBox;

    private int priceFrom;
    private int priceTo;

    private boolean rating18;

    private String sort;

    public Filters(){
        this.searchBox = "";
        this.priceFrom = 0;
        this.priceTo = 100;
        this.rating18 = true;
        this.sort = "priceAsc";
    }
}
