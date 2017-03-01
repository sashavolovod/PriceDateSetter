/*
 * Created by Sasha Volovod on 01.03.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

public class SaveStateTable extends JTable
{
    private static final int ROW_HEIGHT = 22;
    private static final int FONT_SIZE = 15;

    private final Gson gson = new Gson();
    private Preferences prefs;

    public SaveStateTable(String tableName, TableModel dm) {
        super(dm);
        prefs = Preferences.userNodeForPackage(this.getClass()).node(tableName);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setFillsViewportHeight(true);
        setRowSelectionAllowed(true);

        Font oldFont = getFont();
        Font newFont = new Font(oldFont.getName(), Font.PLAIN, FONT_SIZE);
        setFont(newFont);
        setRowHeight(ROW_HEIGHT);
        getTableHeader().setFont(newFont);

        restoreState();
    }

    public boolean editCellAt(int row, int column, EventObject e) {
        boolean result = super.editCellAt(row, column, e);
        selectAll(e);
        return result;
    }

    private void selectAll(EventObject e) {
        final Component editor = getEditorComponent();

        if (editor == null
                || !(editor instanceof JTextComponent))
            return;

        if (e == null) {
            ((JTextComponent) editor).selectAll();
            return;
        }

        //  Typing in the cell was used to activate the editor
        if (e instanceof KeyEvent) {
            ((JTextComponent) editor).selectAll();
            return;
        }

        //  F2 was used to activate the editor
        if (e instanceof ActionEvent) {
            ((JTextComponent) editor).selectAll();
            return;
        }

        if (e instanceof MouseEvent) {
            SwingUtilities.invokeLater(() -> ((JTextComponent) editor).selectAll());
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component returnComp = super.prepareRenderer(renderer, row, column);
        Color alternateColor = new Color(254, 255, 233);
        Color whiteColor = Color.WHITE;
        if (!returnComp.getBackground().equals(getSelectionBackground())) {
            Color bg = (row % 2 == 1 ? alternateColor : whiteColor);
            returnComp.setBackground(bg);
            bg = null;
        }
        return returnComp;
    }

    public void saveState() {
        List<ColumnInfo> columns = new ArrayList<>();
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn c = columnModel.getColumn(i);
            columns.add(new ColumnInfo(c.getIdentifier().toString(), c.getWidth()));
        }

        String json = gson.toJson(columns);
        prefs.put("columns", json);
    }

    public void restoreState() {
        String json = prefs.get("columns", "");
        if ("".equals(json)) {
            return;
        }
        List<ColumnInfo> columns = gson.fromJson(json, new TypeToken<ArrayList<ColumnInfo>>(){}.getType());
        TableColumnModel model = getColumnModel();
        for (int newIndex = 0; newIndex < columns.size(); newIndex++)
        {
            try {
                ColumnInfo colInfo = columns.get(newIndex);
                int index = model.getColumnIndex(colInfo.getColumnName());
                model.moveColumn(index, newIndex);
                getColumn(colInfo.getColumnName()).setPreferredWidth(colInfo.getColumnWidth());
            }
            catch(IllegalArgumentException e) {}
        }
    }
}