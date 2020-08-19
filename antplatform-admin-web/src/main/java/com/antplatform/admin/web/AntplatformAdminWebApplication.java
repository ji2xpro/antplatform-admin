package com.antplatform.admin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
//@ComponentScan({"com.antplatform.admin.biz","com.antplatform.admin.web"})
@ComponentScan("com.antplatform.admin")
@MapperScan("com.antplatform.admin.biz.mapper")
//@MapperScan(basePackages = {"com.antplatform.admin.biz.mapper","com.antplatform.admin.service"})
@Slf4j
public class AntplatformAdminWebApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(AntplatformAdminWebApplication.class, args);
//	}

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application=SpringApplication.run(AntplatformAdminWebApplication.class, args);
		Environment env = application.getEnvironment();
		if (env.getProperty("server.servlet.context-path") != null){
			log.info("\n----------------------------------------------------------\n\t" +
							"Application '{}' is running! Access URLs:\n\t" +
							"Local: \t\thttp://localhost:{}{}\n\t" +
							"External: \thttp://{}:{}{}\n\t"+
							"Doc: \t\thttp://{}:{}{}/doc.html\n\t"+
							"Druid: \t\thttp://{}:{}{}/druid/index.html\n"+
							"----------------------------------------------------------",
					env.getProperty("spring.application.name"),
					env.getProperty("server.port"),
					env.getProperty("server.servlet.context-path"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"),
					env.getProperty("server.servlet.context-path"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"),
					env.getProperty("server.servlet.context-path"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"),
					env.getProperty("server.servlet.context-path"));
		}else {
			log.info("\n----------------------------------------------------------\n\t" +
							"Application '{}' is running! Access URLs:\n\t" +
							"Local: \t\thttp://localhost:{}\n\t" +
							"External: \thttp://{}:{}\n\t"+
							"Doc: \t\thttp://{}:{}/doc.html\n"+
							"Druid: \t\thttp://{}:{}/druid/index.html\n"+
							"----------------------------------------------------------",
					env.getProperty("spring.application.name"),
					env.getProperty("server.port"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"),
					InetAddress.getLocalHost().getHostAddress(),
					env.getProperty("server.port"));
		}
	}

}
