package com.lw.myrpc.register;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscoverClientTest {

    @Test
    public void testClient() {
        DiscoverClient discoverClient = new DiscoverClient();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            discoverClient.register("service" + i);
            assertEquals("127.0.0.1", discoverClient.getInstances("service" + i).get(0).getIp());
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}