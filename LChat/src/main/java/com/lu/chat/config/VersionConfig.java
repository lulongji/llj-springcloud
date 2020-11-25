package com.lu.chat.config;

import com.lulongji.base.LVersion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Description: init system.
 * @Author: lu
 * @Date: Created in 15:53 2018/11/17
 */

@Component
@Order(100)
public class VersionConfig implements CommandLineRunner, EnvironmentAware {

    @Override
    public void run(String... strings) {
        String version = VersionConfig.class.getPackage().getImplementationVersion();
        new LVersion(version);
    }

    @Override
    public void setEnvironment(Environment environment) {

    }
}
