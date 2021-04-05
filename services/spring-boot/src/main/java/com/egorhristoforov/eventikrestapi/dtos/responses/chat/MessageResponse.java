package com.egorhristoforov.eventikrestapi.dtos.responses.chat;

import com.egorhristoforov.eventikrestapi.models.Messages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.util.Date;

@Getter
public class MessageResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;

    private String text;
    private Long senderId;
    private String senderName;

    public MessageResponse(Messages messages) {
        this.text = messages.getText();
        this.senderId = messages.getUser().getId();
        this.senderName = messages.getUser().getName();
        this.date = messages.getLastModifiedDate();
    }
}
