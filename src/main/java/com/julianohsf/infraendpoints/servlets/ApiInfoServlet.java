package com.julianohsf.infraendpoints.servlets;

import com.julianohsf.infraendpoints.domains.apiinfo.ApiInfo;
import com.julianohsf.infraendpoints.services.ApiInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiInfoServlet extends HttpServlet {

    private ApiInfoService apiInfoService;

    public ApiInfoServlet(ApiInfoService apiInfoService) {
        this.apiInfoService = apiInfoService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApiInfo apiInfo = this.apiInfoService.getInfo();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        String responseBody = apiInfo.asJsonString();
        response.setContentLength(responseBody.length());
        response.getWriter().write(responseBody);
    }
}
