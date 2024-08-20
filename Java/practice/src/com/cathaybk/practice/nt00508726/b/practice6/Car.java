package com.cathaybk.practice.nt00508726.b.practice6;

import java.math.BigDecimal;

public class Car{
    private String manufacturer;
    private String type;
    private BigDecimal min_price;
    private BigDecimal price;

    public Car(String manufacturer, String type, BigDecimal min_price, BigDecimal price) {
        this.manufacturer = manufacturer;
        this.type = type;
        this.min_price = min_price;
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMin_price() {
        return min_price;
    }

    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
