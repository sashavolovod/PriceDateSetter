/*
 * Created by Sasha Volovod on 01.03.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

public class ColumnInfo {
    private int columnWidth;
    private String columnName;

    public ColumnInfo() {}

    public ColumnInfo(String columnName, int columnWidth) {
        this.columnWidth = columnWidth;
        this.columnName = columnName;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}