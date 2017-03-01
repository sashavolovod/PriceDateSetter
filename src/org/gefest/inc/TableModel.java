/*
 * Created by Sasha Volovod on 03.02.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TableModel extends AbstractTableModel {
    List<OrderEntity> orders;

    public static final int STATUS = 0;
    public static final int FULL_ORDER_NUMBER = 1;
    public static final int CUSTOMER = 2;
    public static final int CAPTION = 3;
    public static final int PLAN_DATE = 4;
    public static final int PERCENTAGE = 5;
    public static final int PRICE_DATE = 6;

    private List<String> columnNames = Arrays.asList("Срочно", "Номер заказа", "Заказчик", "Наименование работ", "Плановая дата изготовления", "Процент выполнения","Дата согласования цены");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static Logger logger = Logger.getLogger(TableModel.class);

    private Icon bulletRedIcon;

    public TableModel(List<OrderEntity> orders) {
        this.orders = orders;
        bulletRedIcon = createImageIcon("/images/bullet-red.png", "срочный");
    }

    protected Icon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            logger.error("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderEntity order = orders.get(rowIndex);

        switch (columnIndex) {
            case STATUS:
                if(order.getStatus()) {
                    return bulletRedIcon;
                }
                else
                    return null;
            case FULL_ORDER_NUMBER:
                return order.getFullOrderNumber();
            case CUSTOMER:
                return order.getCustomer();
            case CAPTION:
                return order.getCaption();
            case PLAN_DATE:
                return order.getPlanDate();
            case PRICE_DATE:
                return order.getPriceDate();
            case PERCENTAGE:
                return order.getPercentage();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OrderEntity order = orders.get(rowIndex);
        if(columnIndex==PRICE_DATE) {
            Date oldDate = order.getPriceDate();
            if(!(oldDate==null && aValue==null)) {
                Date newDate = (Date) aValue;
                if(newDate==null || !newDate.equals(oldDate)) {
                    order.setPriceDate(newDate);
                    logger.info("Для заказа " + order.getOrderNumber() + " установлена дата " + (aValue == null ? "нет" : dateFormat.format((Date) aValue)));
                    if(RestApiClient.postDate(order)) {
                        logger.info("Изменения сохранены в базе данных.");
                    } else {
                        logger.error("Ошибка при сохранении изменений базе данных.");
                        JOptionPane.showMessageDialog(null, "Ошибка при сохранении изменений базе данных");
                    }
                }
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case PRICE_DATE:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case STATUS:
                return Icon.class;
            case FULL_ORDER_NUMBER:
            case CUSTOMER:
            case CAPTION:
                return String.class;
            case PLAN_DATE:
            case PRICE_DATE:
                return Date.class;
            case PERCENTAGE:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    public OrderEntity getOrder(int rowIndex) {
        return orders.get(rowIndex);
    }
}
