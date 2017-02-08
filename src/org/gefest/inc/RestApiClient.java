/*
 * Created by Sasha Volovod on 08.02.17.
 * Copyright (c) 2017 Sasha Volovod. All rights reserved.
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 */

package org.gefest.inc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RestApiClient {
    private static Logger logger = Logger.getLogger(RestApiClient.class);
    private static final String url = "http://tool/api/pricedate.php";

    public static boolean postDate(OrderEntity order){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String orderId = String.valueOf(order.getOrderNumber());
        String priceDate = (order.getPriceDate() == null ? "null" : format.format(order.getPriceDate()));

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("OrderID", orderId));
        urlParameters.add(new BasicNameValuePair("PriceDate", priceDate));

        HttpResponse response;
        BufferedReader rd;
        StringBuffer result = new StringBuffer();
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            response = client.execute(post);

            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"windows-1251"));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (result.toString().equals("ok")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<OrderEntity> getOrderListFromServer() {
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
