package org.gefest.inc;
/*
 * Created by Sasha Volovod on 07.02.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Date;

public class DatePickerCellEditor extends AbstractCellEditor implements TableCellEditor {

    DatePicker dpPriceDate = new DatePicker();

    @Override
    public Object getCellEditorValue() {
        return dpPriceDate.getDate();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        try {
              dpPriceDate.setDate((Date) value);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dpPriceDate;
    }
}
