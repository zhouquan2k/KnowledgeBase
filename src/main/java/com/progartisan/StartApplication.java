package com.progartisan;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.progartisan.component.data.impl.EntityMetaRegistrar;
import com.progartisan.component.data.impl.MetadataProviderImpl;
import com.progartisan.component.spi.MetadataProvider;
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
@SpringBootApplication(scanBasePackages = { "com.progartisan" })
public class StartApplication {

    public static void main(String[] args) {
        initLogger();

        new EntityMetaRegistrar().initClasses("com.progartisan");

        SpringApplication.run(StartApplication.class, args);
    }

    private static void initLogger() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.springframework").setLevel(Level.DEBUG);
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{dd/HH:mm:ss.SSS} %-5level[%15.-15logger{0}-%-10.-10thread] - %msg %n");
        encoder.start();

        // 创建一个新的ConsoleAppender
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();
    }

    // TODO move to common
    @Bean
    @DependsOn("_Context")
    Object Init0() {
        return new Object();
    }

    //init以前：创建表
    @Bean
    @DependsOn({ "Init0", "startUpHandlerImpl", "MetadataProvider" }) // "taskExecutor"
    Object Init() {
        return new Object();
    }

    //init2 以后的是可以后期初始化的，不被依赖，如loadData
    @Bean
    @DependsOn({ "Init", "userMapper" })
    Object Init2() {
        return new Object();
    }

    @Bean("MetadataProvider")
    MetadataProvider metadataProvider() {
        return new MetadataProviderImpl();
    }
}

