package com.example.playstationdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlayStationDemoApplication {

    private static final Logger LOGGER= LoggerFactory.getLogger(PlayStationDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlayStationDemoApplication.class, args);

        LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
    }

}
