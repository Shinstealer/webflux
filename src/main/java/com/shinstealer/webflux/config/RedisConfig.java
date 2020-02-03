package com.shinstealer.webflux.config;

import redis.embedded.RedisServer;


public class RedisConfig {


	private int port;

	private RedisServer redisServer;

	
	public void redisServer() {
		redisServer = new RedisServer(port);
		redisServer.start();
	}


	public void stopRedis() {
		if (redisServer != null) {
			redisServer.stop();
		}
	}

}
