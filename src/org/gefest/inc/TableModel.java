package org.gefest.inc;
/*
 * Created by Sasha Volovod on 03.02.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class TableModel extends AbstractTableModel {
    List<OrderEntity> orders;
    private List<String> columnNames = Arrays.asList("Номер заказа", "Заказчик", "Наименование работ", "Плановая дата изготовления", "Дата согласования цены");

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        switch (columnIndex) {
            case 0:
                return order.getFullOrderNumber();
            case 1:
                return order.getCustomer();
            case 2:
                return order.getCaption();
            case 3:
                return order.getPlanDate() == null ? null : dateFormat.format(order.getPlanDate());
            case 4:
                return order.getPriceDate() == null ? null : dateFormat.format(order.getPriceDate());
            default:
                return null;
        }
    }

    public OrderEntity getOrder(int rowIndex) {
        return orders.get(rowIndex);
    }
}
