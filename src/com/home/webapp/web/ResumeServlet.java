package com.home.webapp.web;

import com.home.webapp.Config;
import com.home.webapp.model.Resume;
import com.home.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private static Config config = Config.getInstance();
    private static String url = config.getDbUrl();
    private static String user = config.getDbUser();
    private static String password = config.getDbPassword();
    private SqlStorage storage = new SqlStorage(url, user, password);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        Resume resume = storage.get(uuid);

        String fullName = resume.getFullName();
        String body = ""
                + "<html>"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                + "<title>Резюме " + fullName + "</title>"
                + "</head>"
                + "<body>"
                + "<h1>" + fullName + "</h1>"
                + "<p> To be continued…"
                + "</body>"
                + "</html>";


        response.getWriter().write(body);

    }
}
