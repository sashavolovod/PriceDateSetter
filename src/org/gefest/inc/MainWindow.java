/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

class MainWindow extends JFrame {

    private static final int DEFAULT_WIDTH = 648;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int HEADER_HEIGHT = 30;
    private static final int FONT_SIZE = 15;
    private Preferences prefs  = Preferences.userNodeForPackage(MainWindow.class).node("MainWindow");
    private static Logger logger = Logger.getLogger(MainWindow.class);
    private JTextField txtFilter = new JTextField();
    private SaveStateTable table;
    private TableRowSorter sorter;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    MainWindow(){
        super();
        createUi();
    }

    private void saveProperties() {
        prefs.putInt("left", getX());
        prefs.putInt("top", getY());
        prefs.putInt("width", getWidth());
        prefs.putInt("height", getHeight());
    }

    private void loadProperties()
    {
        int left = prefs.getInt("left", 0);
        int top = prefs.getInt("top", 0);
        int width = prefs.getInt("width", DEFAULT_WIDTH);
        int height = prefs.getInt("height", DEFAULT_HEIGHT);
        setBounds(left, top, width, height);
    }

    private void createUi() {
        setTitle("Программа установки даты согласования цены заказа");
        setSize(600,400);

        Font oldFont = txtFilter.getFont();
        Font newFont = new Font(oldFont.getName(), Font.PLAIN, FONT_SIZE);
        txtFilter.setFont(newFont);

        JLabel lbFilter = new JLabel("Фильтр: ");
        lbFilter.setFont(newFont);

        TableModel model = new TableModel(RestApiClient.getOrderListFromServer());
        sorter = new TableRowSorter(model);
        table = new SaveStateTable("PriceDateSetterTable",model);
        table.setRowSorter(sorter);

        TableColumn planDateColumn = table.getColumnModel().getColumn(TableModel.PLAN_DATE);
        planDateColumn.setCellRenderer(new DateFormatRenderer( format ));
        TableColumn priceDateColumn = table.getColumnModel().getColumn(TableModel.PRICE_DATE);
        priceDateColumn.setCellEditor(new DatePickerCellEditor());
        priceDateColumn.setCellRenderer(new DateFormatRenderer( format ));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(TableModel.PERCENTAGE).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(TableModel.FULL_ORDER_NUMBER).setCellRenderer(centerRenderer);

        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel(new MigLayout("","[][grow]" , "[][grow]"));
        panel.add(lbFilter);
        panel.add(txtFilter, "growx, pushx, wrap");
        panel.add(new ScrollTable(table, HEADER_HEIGHT), "span, grow");

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadProperties();

        txtFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilter();
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onWindowClosing();
            }
        });

        setVisible(true);

    }

    private void applyFilter() {
        String filterString = txtFilter.getText().trim();
        if(filterString.length()==0) {
            sorter.setRowFilter(null);
            return;
        }

        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry entry) {
                TableModel model = (TableModel) entry.getModel();
                OrderEntity order = model.getOrder((int) entry.getIdentifier());
                return order.toString().toLowerCase().contains(filterString.toLowerCase());
            }
        };

        sorter.setRowFilter(filter);
    }

    private void onWindowClosing() {
        TableCellEditor editor = table.getCellEditor();
        if (editor != null) {
            editor.stopCellEditing();
        }
        table.saveState();
        saveProperties();
        logger.info("----------END LOGGING------------");
    }
}
