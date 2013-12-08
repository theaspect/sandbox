package com.blazer.repl.servlet;

import com.blazer.repl.service.Dao;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
@Singleton
public class RestServlet extends HttpServlet {
    private final Provider<Dao> daoProvider;

    @Inject
    public RestServlet(Provider<Dao> daoProvider) {
        this.daoProvider = daoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao dao = daoProvider.get();
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(Dao.toGson(dao.findAll()));
    }
}
