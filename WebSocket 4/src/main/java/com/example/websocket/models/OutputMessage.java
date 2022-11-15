package com.example.websocket.models;

//класс сообщения, которое отображается на фронте
public class OutputMessage {

    private final String from;
    private final String message;
    private final String time;

    public OutputMessage(String from, String message, String time) {
        this.from = from;
        this.message = message;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
