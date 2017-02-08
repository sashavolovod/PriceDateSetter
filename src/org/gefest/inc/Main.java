/*
 * Created by Sasha Volovod on 30.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);

        logger.info("----------START LOGGING------------");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            logger.error("setLookAndFeel error", e);
        }

        javax.swing.SwingUtilities.invokeLater(MainWindow::new);

    }
}