package com.egorhristoforov.eventikrestapi.dtos.responses.news;

import com.egorhristoforov.eventikrestapi.models.News;
import lombok.Getter;

@Getter
public class NewsListResponse {
    private Long newsId;
    private String maintenance;

    public NewsListResponse(News news) {
        this.newsId = news.getId();
        this.maintenance = news.getMaintenance();
    }
}
