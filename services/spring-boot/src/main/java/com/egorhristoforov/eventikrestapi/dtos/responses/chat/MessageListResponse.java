package com.egorhristoforov.eventikrestapi.dtos.responses.chat;

import com.egorhristoforov.eventikrestapi.models.Message;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MessageListResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;

    private String text;
    private Long senderId;
    private String senderName;

    public MessageListResponse(Message message) {
        this.text = message.getText();
        this.senderId = message.getUser().getId();
        this.senderName = message.getUser().getName();
        this.date = message.getCreatedDate();
    }
}
