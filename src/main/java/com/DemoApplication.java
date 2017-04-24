package com;

import com.customer.scheduling.CustomerStatusScheduling;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class DemoApplication extends SpringBootServletInitializer implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Autowired
    private CustomerStatusScheduling customerStatusScheduling;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("------start reSetCustomerOnFirstRun -----");
        customerStatusScheduling.reSetCustomerOnFirstRun();
        System.out.println("------end reSetCustomerOnFirstRun -----");
    }
}
