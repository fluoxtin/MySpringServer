package com.example.springprojectdemo;

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class SpringProjectDemoApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application =
            SpringApplication.run(SpringProjectDemoApplication.class, args);
        System.out.println(
                "Spring boot"
        );

        Environment environment = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        if (StringUtils.isNullOrEmpty(path)) {
            path = "";
        }
        log.info("\n------------------" +
                "Application is running ! Access URLs : \n\t" +
                "Local : \t\thttp://localhos:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path +"\n");
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        log.info("current pid : " + jvmName.split("@")[0]);
    }

}
