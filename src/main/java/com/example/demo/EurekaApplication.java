package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication implements EnvironmentAware {
	private static Logger log = LoggerFactory.getLogger(EurekaApplication.class);
    private Environment env;	

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

	@Override
	public void setEnvironment(Environment environment) {
        this.env = environment;
        String[] dProfiles = env.getDefaultProfiles();
        String[] aProfiles = env.getActiveProfiles();

        System.out.println("Spring DefaultProfiles:" + java.util.Arrays.asList(dProfiles));
        System.out.println("Spring ActiveProfiles:" + java.util.Arrays.asList(aProfiles));
        if (aProfiles.length == 0) {
          System.out.println("Please set 'spring.profiles.active'");
          System.exit(-1);
        }
        
        log.debug("当前ActiveProfiles:"+env.getActiveProfiles().toString()); 
	}
}
