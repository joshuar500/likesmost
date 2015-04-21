package com.joshrincon.servlets;

import com.joshrincon.instautils.Constants;
import com.joshrincon.instautils.InstagramUtils;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.oauth.InstagramService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "AuthServlet")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If we already have an instagram object, then redirect to likesmost.jsp
        HttpSession session = request.getSession();

        Object objInstagram = session.getAttribute(Constants.INSTAGRAM_OBJECT);
        if (objInstagram != null) {
            response.sendRedirect("/likesmost/");
            return;
        }

        Properties properties = InstagramUtils.getConfigProperties();
        String clientId = properties.getProperty(Constants.CLIENT_ID);
        String clientSecret = properties.getProperty(Constants.CLIENT_SECRET);
        String callbackUrl = properties.getProperty(Constants.REDIRECT_URI);
        InstagramService service = new InstagramAuthService()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .callback(callbackUrl)
                .build();
        String authorizationUrl = service.getAuthorizationUrl(null);
        session.setAttribute(Constants.INSTAGRAM_SERVICE, service);
        session.setAttribute("authorizationUrl", authorizationUrl);

        System.out.println("Auth Url: " + authorizationUrl);

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        return;
    }
}
