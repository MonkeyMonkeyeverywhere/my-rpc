package com.lw.myrpc.runner;

import com.lw.myrpc.register.DiscoverServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @ClassName : ServerRunner
 * @Description :
 * @Author : lw.tar.gz
 * @Date: 2020-06-10 16:47
 */
@Slf4j
@Service
public class ServerRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("======= DiscoverServer start =======");
        new DiscoverServer().start();
    }
}
