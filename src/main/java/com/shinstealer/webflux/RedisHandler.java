package com.shinstealer.webflux;

import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.shinstealer.webflux.entity.Message;

import reactor.core.publisher.Mono;

public class RedisHandler {

	private final ReactiveRedisTemplate<String, Message> template;

	public RedisHandler(ReactiveRedisTemplate<String, Message> template) {

		this.template = template;
	}

	public RouterFunction route() {
		return RouterFunctions.route().GET("/redis/sse", this::sse).POST("/redis/post", this::post).build();
	}

	public Mono<ServerResponse> post(ServerRequest request) {
		return request.bodyToMono(Message.class).flatMap(m -> template.convertAndSend("messages", m)
				.flatMap(l -> ServerResponse.ok().body(Mono.just("done"), String.class)));
	}

	public Mono<ServerResponse> sse(ServerRequest request) {
		return ServerResponse.ok()
				.body(template.listenToChannel("messages").map(ReactiveSubscription.Message::getMessage)
						.map(o -> ServerSentEvent.builder(o).event("messages").build()), ServerSentEvent.class);
	}

}
