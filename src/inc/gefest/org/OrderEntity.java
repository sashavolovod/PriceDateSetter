/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package inc.gefest.org;

import java.util.Date;

public class OrderEntity {

    private int    orderNumber;
    private String fullOrderNumber;
    private String caption;
    private Date   planDate;
    private Date   priceDate;

    public OrderEntity() { }

    public OrderEntity(int orderNumber, String fullOrderNumber, String caption, Date planDate, Date priceDate) {
        this.orderNumber = orderNumber;
        this.fullOrderNumber = fullOrderNumber;
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

    public String getFullOrderNumber() {
        return fullOrderNumber;
    }

    public void setFullOrderNumber(String fullOrderNumber) {
        this.fullOrderNumber = fullOrderNumber;
    }

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
}
