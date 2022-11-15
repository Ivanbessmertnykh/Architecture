package com.example.websocket.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*Аннотируем класс конфигурацией (т.е. говорим программе, что данный класс содержит 1 или более бинов (классы,
// с которыми работает спринг), обрабатывающиеся контейнером спринга для генераций компонент). Про контейнер
// советую отдельно нагуглить, если будут жетско душить по определениям
// + позволяем этому классу работать с брокером сообщений
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    //регистрирует конечную точку - через нее будем перекидывать сообщения
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //для большей эластичности добавляем эндпоинты как с SockJS, так и без
        //SockJS понадобится для взаимодействия фронта с бэком
        registry.addEndpoint("/webs");
        registry.addEndpoint("/webs").withSockJS();
    }

    //Настройка брокера сообщений
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //разрешаем брокеру сообщений в памяти передавать сообщения клиенту по пунктам назначения с префиксом /topic
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/application");
    }
}
