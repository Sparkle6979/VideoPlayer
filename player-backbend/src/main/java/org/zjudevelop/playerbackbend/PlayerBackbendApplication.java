package org.zjudevelop.playerbackbend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.zjudevelop.playerbackbend.dao")
public class PlayerBackbendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerBackbendApplication.class, args);
    }

}
