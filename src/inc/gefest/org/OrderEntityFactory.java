/**
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package inc.gefest.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderEntityFactory {

    List<OrderEntity> getOrderList() {
        List<OrderEntity> list = new ArrayList<OrderEntity>();
        Date date = new Date();
        list.add(new OrderEntity(70000, "208-70000","1221-454544", date,null));
        list.add(new OrderEntity(70001, "233-70001","1221-321456", date,null));
        list.add(new OrderEntity(70002, "233-70002","4235-741456", date,null));
        list.add(new OrderEntity(70003, "233-70003","1324-567891", date,null));

        return list;
    }
}
