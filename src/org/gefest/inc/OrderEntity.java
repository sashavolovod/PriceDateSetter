/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.gefest.inc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderEntity {

    private int    orderNumber;
    private String fullOrderNumber;
    private String caption;
    private Date   planDate;
    private Date   priceDate;
    private String customer;

    public OrderEntity() { }

    OrderEntity(int orderNumber, String fullOrderNumber, String customer, String caption, Date planDate, Date priceDate) {
        this.orderNumber = orderNumber;
        this.fullOrderNumber = fullOrderNumber;
        this.customer = customer;
        this.caption = caption;
        this.planDate = planDate;
        this.priceDate = priceDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getFullOrderNumber() {    return fullOrderNumber;   }

    public void setFullOrderNumber(String fullOrderNumber) {
        this.fullOrderNumber = fullOrderNumber;
    }

    public String getCustomer() { return customer; }

    public void setCustomer(String customer) { this.customer = customer; }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String planDateStr = planDate == null ? "" : dateFormat.format(planDate);
        String priceDateStr = priceDate == null ? "" : dateFormat.format(priceDate);
        return orderNumber + " " + fullOrderNumber + " " + caption + " " +
                planDateStr + " " + priceDateStr + " " +    customer;
    }
}

