package com.blazer.repl.servlet;

import com.blazer.repl.service.GroovyService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
@Singleton
public class ReplServlet extends HttpServlet {
    private final GroovyService groovyService;

    @Inject
    public ReplServlet(GroovyService groovyService) {
        this.groovyService = groovyService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(new Gson().toJson(groovyService.executeScript(req.getParameter("code"))));
    }
}
