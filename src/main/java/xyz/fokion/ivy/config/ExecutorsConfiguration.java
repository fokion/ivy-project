package xyz.fokion.ivy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorsConfiguration {

    @Bean
    public ExecutorService executorService(@Value("${executor.pool.size:0}") Integer poolSize) {
        if (poolSize == null || poolSize < 1) {
            poolSize = Runtime.getRuntime().availableProcessors() - 1;
        }
        return Executors.newFixedThreadPool(poolSize);
    }
}
