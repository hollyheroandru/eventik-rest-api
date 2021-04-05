package com.egorhristoforov.eventikrestapi.dtos.responses.news;

import com.egorhristoforov.eventikrestapi.models.News;
import lombok.Getter;

@Getter
public class NewsCreateResponse {
    private Long newsId;
    private String maintenance;

    public NewsCreateResponse(News news) {
        this.newsId = news.getId();
        this.maintenance = news.getMaintenance();
    }
}
