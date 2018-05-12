package com.zoop.infraendpoints.servlets;

import com.zoop.infraendpoints.domains.health.Health;
import com.zoop.infraendpoints.services.HealthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {

    private HealthService healthService;

    public HealthServlet(HealthService healthService) {
        this.healthService = healthService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Health health = this.healthService.check();
        response.setStatus(health.getHttpStatus());
        response.setContentType("application/json");
        String responseBody = health.asJsonString();
        response.setContentLength(responseBody.length());
        response.getWriter().write(responseBody);
    }
}
