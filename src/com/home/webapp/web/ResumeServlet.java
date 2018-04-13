package com.home.webapp.web;

import com.home.webapp.Config;
import com.home.webapp.model.ContactType;
import com.home.webapp.model.Resume;
import com.home.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
        Map<ContactType, String> contactEntries = resume.getContacts();
        String contacts = "<p>";
        for (Map.Entry<ContactType, String> entry : contactEntries.entrySet()) {
            ContactType contactType = entry.getKey();
            String entryValue = entry.getValue();
            String contactTypeTitle = contactType.getTitle();

            switch (contactType) {
                case GITHUB:
                case LINKEDIN:
                case STACKOVERFLOW:
                case HOME_PAGE:
                    contacts += "<a href='" + entryValue + "'>" + contactTypeTitle + "</a><br/>\n";
                    break;
                case HOME_PHONE:
                case MOBILE:
                case PHONE:
                    contacts +=  contactTypeTitle+ ": " + entryValue + "<br/>\n";
                    break;
                case MAIL:
                    contacts += "Почта: <a href='mailto:" + entryValue + "'> " + entryValue + "</a><br/>\n";
                    break;
                case SKYPE:
                    contacts += "Skype: <a href='skype:" + entryValue + "'> " + entryValue + "</a><br/>\n";
                    break;
            }
        }
        contacts += "<p><hr>";

        String body = ""
                + "<html>"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                + "<title>Резюме " + fullName + "</title>"
                + "</head>"
                + "<body>"
                + "<h1>" + fullName + "</h1>"
                + contacts
                + "<p> To be continued…"
                + "</body>"
                + "</html>";


        response.getWriter().write(body);

    }
}
