package com.quintrix.jepsen.erik.fifth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@AutoConfiguration
@ComponentScan("com.quintrix.jepsen.erik.fifth")
@EntityScan("com.quintrix.jepsen.erik.fifth")
@EnableJpaRepositories
public class FifthApplication {

  public static void main(String[] args) {
    SpringApplication.run(FifthApplication.class, args);
  }

}
