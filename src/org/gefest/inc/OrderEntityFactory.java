/*
 * Created by Sasha Volovod on 31.01.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */
package org.gefest.inc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderEntityFactory {

    private static Logger logger = Logger.getLogger(OrderEntityFactory.class);

    public static List<OrderEntity> getOrderList() {
        List<OrderEntity> list = new ArrayList<>();
        Date date = new Date();
        list.add(new OrderEntity(70000, "208-70000","заказчик 1","1221-454544", date,null));
        list.add(new OrderEntity(70001, "233-70001","заказчик 2","1221-321456", date,null));
        list.add(new OrderEntity(70002, "233-70002","заказчик 3","4235-741456", date,null));
        list.add(new OrderEntity(70003, "233-70003","заказчик 4","1324-567891", date,null));

        return list;
    }

    public static List<OrderEntity> getOrderListFromServer() {
        final String url = "http://tool/api/pricedate.php";
        List<OrderEntity> list = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String json = getUrl(url);
            list = gson.fromJson(json, new TypeToken<ArrayList<OrderEntity>>(){}.getType());

            logger.info("Получено с сервера " + list.size() + " записей");

        } catch (IOException e) {
            logger.error("get json from server error", e);
        }
        return list;
    }

    private static String getUrl(String uri) throws IOException {

        Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);

        HttpGet req = new HttpGet(uri);
        BufferedReader rd;
        StringBuffer result = new StringBuffer();
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req) ) {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"windows-1251"));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}
