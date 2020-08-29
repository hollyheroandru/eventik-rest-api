package com.egorhristoforov.eventikrestapi.dtos.responses.user;

public class UserCredentialsResponse {
    private String access;
    private String refresh;
    private Long id;

    public UserCredentialsResponse(String access, String refresh, Long id) {
        this.access = access;
        this.refresh = refresh;
        this.id = id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
