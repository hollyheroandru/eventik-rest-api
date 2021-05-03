package com.egorhristoforov.eventikrestapi.dtos.responses.chat;

import com.egorhristoforov.eventikrestapi.models.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.util.Date;

@Getter
public class MessageResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;
    private Long id;
    private String text;
    private Long senderId;
    private String senderName;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.senderId = message.getUser().getId();
        this.senderName = message.getUser().getName();
        this.date = message.getCreatedDate();
    }
}
