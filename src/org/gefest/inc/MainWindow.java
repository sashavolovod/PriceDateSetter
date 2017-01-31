/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.gefest.inc;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

class MainWindow extends JFrame {

    private static final int DEFAULT_WIDTH = 648;
    private static final int DEFAULT_HEIGHT = 480;

    private Preferences prefs  = Preferences.userNodeForPackage(MainWindow.class).node("MainWindow");

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
        setTitle("Программа установки даты оценки заказа");
        setSize(600,400);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadProperties();
        setVisible(true);
    }
}
