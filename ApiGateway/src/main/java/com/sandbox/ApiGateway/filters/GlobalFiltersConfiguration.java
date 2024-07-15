package com.sandbox.ApiGateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GlobalFiltersConfiguration {

    @Bean
    @Order(1)
    public GlobalFilter secondPreFilter(){

        return (exchange, chain) -> {
            //prefilter code
            log.info("Second global prefilter is being executed");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //postfilter code
                log.info("Second global postfilter is being executed");
            }));
        };
    }
    @Bean
    @Order(0)
    public GlobalFilter thirdPreFilter(){

        return (exchange, chain) -> {
            //prefilter code
            log.info("Third global prefilter is being executed");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //postfilter code
                log.info("Third global postfilter is being executed");
            }));
        };
    }

}
