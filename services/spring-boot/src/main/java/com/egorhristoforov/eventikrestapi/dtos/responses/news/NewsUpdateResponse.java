package com.egorhristoforov.eventikrestapi.dtos.responses.news;

import com.egorhristoforov.eventikrestapi.models.News;
import lombok.Getter;

@Getter
public class NewsUpdateResponse {
    private Long newsId;
    private String text;

    public NewsUpdateResponse(News news) {
        this.newsId = news.getId();
        this.text = news.getText();
    }
}
