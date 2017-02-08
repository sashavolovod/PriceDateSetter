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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

class MainWindow extends JFrame {

    private static final int DEFAULT_WIDTH = 648;
    private static final int DEFAULT_HEIGHT = 480;
    private Preferences prefs  = Preferences.userNodeForPackage(MainWindow.class).node("MainWindow");
    private static Logger logger = Logger.getLogger(MainWindow.class);
    private JTextField txtFilter = new JTextField();
    private JTable table;
    private TableRowSorter sorter;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    MainWindow(){
        super();
        createUi();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveProperties();
            }
        });
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

        TableModel model = new TableModel(RestApiClient.getOrderListFromServer());
        sorter = new TableRowSorter(model);
        table = new JTable(model);
        table.setRowSorter(sorter);

        //table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        TableColumn planDateColumn = table.getColumnModel().getColumn(3);
        planDateColumn.setCellRenderer(new DateFormatRenderer( format ));
        TableColumn priceDateColumn = table.getColumnModel().getColumn(4);
        priceDateColumn.setCellEditor(new DatePickerCellEditor());
        priceDateColumn.setCellRenderer(new DateFormatRenderer( format ));

        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel panel = new JPanel(new MigLayout("","[][grow]" , "[][grow]"));
        panel.add(new JLabel("Фильтр: "));
        panel.add(txtFilter, "growx, pushx, wrap");
        panel.add(scroll, "span, grow");

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
                TableCellEditor editor = table.getCellEditor();
                if (editor != null) {
                    editor.stopCellEditing();
                }

                logger.info("----------END LOGGING------------");
            }
        });

        setVisible(true);
    }

    private void applyFilter() {
        String filterString = txtFilter.getText().trim();
        if(filterString.length()==0) {
            System.out.println("clear filter");
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
}
