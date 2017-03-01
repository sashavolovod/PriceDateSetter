/*
 * Created by Sasha Volovod on 08.02.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.Format;

    public class DateFormatRenderer extends DefaultTableCellRenderer {
    private Format format;

    public DateFormatRenderer(Format format) {
        this.format = format;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setValue(Object value)
    {
        try {
            if (value != null)
                value = format.format(value);
        }
        catch(IllegalArgumentException e) {}

        super.setValue(value);
    }
}
