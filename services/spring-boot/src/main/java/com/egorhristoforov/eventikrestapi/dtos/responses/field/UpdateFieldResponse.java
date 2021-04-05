package com.egorhristoforov.eventikrestapi.dtos.responses.field;

import com.egorhristoforov.eventikrestapi.models.Field;
import lombok.Getter;

@Getter
public class UpdateFieldResponse {
    private Long id;
    private String title;
    private String placeholder;
    private String type;

    public UpdateFieldResponse(Field field) {
        this.id = field.getId();
        this.title = field.getTitle();
        this.placeholder = field.getPlaceholder();
        this.type = field.getType().getType();
    }
}
