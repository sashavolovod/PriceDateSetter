/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.gefest.inc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderEntityFactory {
    public static List<OrderEntity> getOrderList() {
        List<OrderEntity> list = new ArrayList<>();
        Date date = new Date();
        list.add(new OrderEntity(false, 70000, "208-70000", "заказчик 1", "1221-454544", date, null, 50));
        list.add(new OrderEntity(false, 70001, "233-70001", "заказчик 2", "1221-321456", date, null, 99));
        list.add(new OrderEntity(false, 70002, "233-70002", "заказчик 3", "4235-741456", date, null, 01));
        list.add(new OrderEntity(false, 70003, "233-70003", "заказчик 4", "1324-567891", date, null, 12));
        return list;
    }
}
