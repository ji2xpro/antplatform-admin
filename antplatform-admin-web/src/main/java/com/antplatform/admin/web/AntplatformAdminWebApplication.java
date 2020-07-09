package com.antplatform.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@ComponentScan({"com.antplatform.admin.core","com.antplatform.admin.web"})
@ComponentScan("com.antplatform.admin")
@MapperScan("com.antplatform.admin.biz.mapper")
//@MapperScan(basePackages = {"com.antplatform.admin.biz.mapper","com.antplatform.admin.service"})
public class AntplatformAdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntplatformAdminWebApplication.class, args);
	}

}
