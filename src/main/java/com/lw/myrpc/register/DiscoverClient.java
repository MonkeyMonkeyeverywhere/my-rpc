package com.lw.myrpc.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @ClassName : DiscoverClient
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 09:55
 */
@Slf4j
public class DiscoverClient {

    public void register(String serviceName) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            HttpPost httpPost = new HttpPost("http://localhost:18080/register");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("ip", InetAddress.getLocalHost().getHostAddress());
            jsonParam.put("serviceName", serviceName);

            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                log.info("register return code {}", response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Instance> getInstances(String serviceName) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            URI uri = new URIBuilder("http://localhost:18080/getInstances").setParameter("serviceName", serviceName).build();
            HttpGet httpGet = new HttpGet(uri);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    String content = EntityUtils.toString(response.getEntity(), "utf-8");
                    return JSON.parseArray(content, Instance.class);
                }
                log.info("register return code {}", response.getStatusLine().getStatusCode());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
