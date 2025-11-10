package ru.khmelevskoy.api.controller;

import javax.servlet.http.HttpServletRequest;

public class Controller {
    public Long getUserId(HttpServletRequest httpServletRequest) {
        return (Long) httpServletRequest.getSession().getAttribute("userId");
    }
}