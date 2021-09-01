package com.springboot.exchange.msbootcoin.config;

import com.springboot.exchange.msbootcoin.handler.ExchangeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ExchangeHandler exchangeHandler){
        return route(GET("/api/exchange"), exchangeHandler::findAll).
                andRoute(PUT("/api/exchange/{id}"), exchangeHandler::takeExchangeBC).
                andRoute(POST("/api/exchange"), exchangeHandler::createExchangeBC);
    }
}
