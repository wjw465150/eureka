package org.wjw.cloud;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication implements EnvironmentAware {
	@Value("${logging.config}")
	private String loggingConfig;

	public static void main(String[] args) {
		final ConfigurableApplicationContext applicationContext = SpringApplication.run(EurekaApplication.class, args);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				applicationContext.close();
			}
		});
	}

	@Override
	public void setEnvironment(Environment env) {
		String[] dProfiles = env.getDefaultProfiles();
		String[] aProfiles = env.getActiveProfiles();

		System.out.println("Spring DefaultProfiles:" + java.util.Arrays.asList(dProfiles));
		System.out.println("Spring ActiveProfiles:" + java.util.Arrays.asList(aProfiles));
		if (aProfiles.length == 0) {
			System.out.println("Please set 'spring.profiles.active'");
			System.exit(-1);
		}

		// 加载Log4J配置文件
		try {
			DOMConfigurator.configure(org.springframework.util.ResourceUtils.getURL(loggingConfig));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Logger log = LoggerFactory.getLogger(this.getClass());

		log.info("当前ActiveProfiles:" + java.util.Arrays.asList(aProfiles));
	}
}
