package com.shinstealer.webflux;

import java.time.Duration;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Handler {
	
	public RouterFunction<ServerResponse> routes(){
		return RouterFunctions.route().GET("/sse", this::sse).build();
		
	}
	
	public Mono<ServerResponse> sse(ServerRequest request){
		return ServerResponse.ok()
                .body(Flux.interval(Duration.ofMillis(1000)).take(10)
                        .map(l -> ServerSentEvent.builder(l).event("sse").build()), ServerSentEvent.class);
		
	}

}
