package com.example.websocket.controllers;

import com.example.websocket.models.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

//Эта аннотация говорит, что класс выступает в качестве обработчика веб-запросов
@Controller
public class ChatController {

    //Вот он пункт назначения, о котором шла речь в конфигурации
    @MessageMapping("/webs")
    @SendTo("/topic/messages") //Пункт назначения, куда отправляется сообщение
    public OutputMessage send(Message message)  {

        //Мы берем из сообщения, которое мы отправили, данные о пользователе и содержимом и создаем новый класс,
        //отвечающий за вывод сообщения (+ добавляем время с помощью паттерна)
        return new OutputMessage(
                message.getFrom(),
                message.getMessage(),
                new SimpleDateFormat("HH:mm").format(new Date()));
    }
}
