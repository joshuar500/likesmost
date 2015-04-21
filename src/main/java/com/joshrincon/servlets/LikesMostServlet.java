package com.joshrincon.servlets;

import com.joshrincon.instautils.Constants;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.likes.LikesFeed;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "LikesMostServlet")
public class LikesMostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Instagram instagram = null;

        Object objInstagram = request.getSession().getAttribute(Constants.INSTAGRAM_OBJECT);

        if(objInstagram != null) {
            instagram = (Instagram) objInstagram;
        } else {
            String code = request.getParameter("code");

            InstagramService service = (InstagramService) request.getSession().getAttribute(Constants.INSTAGRAM_SERVICE);

            System.out.println("Code is " + code);

            Verifier verifier = new Verifier(code);

            Token accessToken = service.getAccessToken(null, verifier);

            instagram = new Instagram(accessToken);

            request.getSession().setAttribute(Constants.INSTAGRAM_OBJECT, instagram);
            /*System.out.println("objInstag is null");
            request.getRequestDispatcher("/").forward(request, response);*/
            /*return;*/
        }

        UserInfoData userInfoData = instagram.getCurrentUserInfo().getData();

        MediaFeed mediaFeed = instagram.getRecentMediaFeed(userInfoData.getId());
        List<MediaFeedData> mediaFeeds = mediaFeed.getData();

        LikesFeed likesFeed = null;
        List<User> users = null;

        Map<String, Integer> likerOccurances = new HashMap<String, Integer>();

        int i = 0;
        for (MediaFeedData mediaData : mediaFeeds) {
            System.out.println("media id : " + mediaData.getId());

            likesFeed = instagram.getUserLikes(mediaData.getId());
            users = likesFeed.getUserList();

            for(User user : users){
                if(likerOccurances.containsKey(user.getUserName())) {
                    likerOccurances.put(user.getUserName(), likerOccurances.get(user.getUserName())+ 1);
                    System.out.println(user.getUserName() + " : " + likerOccurances.get(user.getUserName()));
                } else {
                    likerOccurances.put(user.getUserName(), 1);
                }
                System.out.println(i + "  :  " + user.getUserName());
            }

            i += 1;
        }

        Object[] a = likerOccurances.entrySet().toArray();
        Arrays.sort(a, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o1).getValue()
                );
            }
        });

        for (Object e : a) {
            System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                    + ((Map.Entry<String, Integer>) e).getValue());
        }

        request.setAttribute("orderedList", a);

        request.getRequestDispatcher("/WEB-INF/likesmost.jsp").forward(request, response);
        return;
    }
}
