package com.chen.brand.job;

import com.chen.brand.sys.SysData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner{

    @Autowired
    private SysData sysData;

    public void run(String... args) throws Exception{
        System.out.println("spring boot start -- load data");
        sysData.init();
    }
}
