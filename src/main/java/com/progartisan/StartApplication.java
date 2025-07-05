package com.progartisan;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import io.leanddd.component.data.impl.EntityMetaRegistrar;
import io.leanddd.component.data.impl.MetadataProviderImpl;
import io.leanddd.component.framework.MetadataProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(order = 0)
@SpringBootApplication(scanBasePackages = { "io.leanddd", "com.progartisan" })
public class StartApplication {

    public static void main(String[] args) {
        EntityMetaRegistrar.init("io.leanddd", "com.progartisan");
        SpringApplication.run(StartApplication.class, args);
    }
}

