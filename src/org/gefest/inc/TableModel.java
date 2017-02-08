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
    private List<String> columnNames = Arrays.asList("Номер заказа", "Заказчик", "Наименование работ", "Плановая дата изготовления", "Дата согласования цены");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static Logger logger = Logger.getLogger(TableModel.class);

    public TableModel(List<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderEntity order = orders.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return order.getFullOrderNumber();
            case 1:
                return order.getCustomer();
            case 2:
                return order.getCaption();
            case 3:
                return order.getPlanDate();
            case 4:
                return order.getPriceDate();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OrderEntity order = orders.get(rowIndex);
        if(columnIndex==4) {
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
            case 0:
            case 1:
            case 2:
            case 3:
                return false;
            case 4:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
                return String.class;
            case 3:
            case 4:
                return Date.class;
            default:
                return Object.class;

        }
    }

    public OrderEntity getOrder(int rowIndex) {
        return orders.get(rowIndex);
    }
}
