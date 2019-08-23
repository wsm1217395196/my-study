package com.study;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagerServer
public class TxlcnTmStart {

    public static void main(String[] args) {
        SpringApplication.run(TxlcnTmStart.class, args);
    }
}
