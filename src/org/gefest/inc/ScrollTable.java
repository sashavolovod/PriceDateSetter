/*
 * Created by Sasha Volovod on 01.03.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import javax.swing.*;
import java.awt.*;

/**
 * Created by udly on 24.08.16.
 */
public class ScrollTable  extends JScrollPane {

    public ScrollTable(JTable table, int headerHeight) {
        super(table);
        getViewport().setBackground(Color.WHITE);

        setColumnHeader(new JViewport() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = headerHeight;
                return d;
            }
        });
    }
}
