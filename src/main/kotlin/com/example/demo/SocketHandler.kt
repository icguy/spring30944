package com.example.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.AbstractWebSocketHandler


@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
	override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
		registry.addHandler(myHandler(), "/handler")
	}

	@Bean
	fun myHandler(): WebSocketHandler {
		return SocketHandler(LoggerFactory.getLogger("socket"))
	}
}
class SocketHandler(
	private val logger: Logger
) : AbstractWebSocketHandler() {
	override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
		logger.info("afterConnectionClosed 1, isOpen=${session.isOpen}")
		try {
			session.sendMessage(TextMessage("hello"))
		}
		catch(ex: Exception) {
			logger.error("failed to send message", ex)
		}
		logger.info("afterConnectionClosed 2, isOpen=${session.isOpen}")
	}
}
