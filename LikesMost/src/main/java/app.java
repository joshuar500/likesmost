import jlinsta.Constants;
import jlinsta.InstagramUtils;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.likes.LikesFeed;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import static spark.Spark.*;

public class app {
    public static void main(String[] args) {

        get("/", (req, res) -> {

            return "this is the home page";

        });

        get("/auth", (req, res) -> {

            req.session(true);

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

            req.session().attribute(Constants.INSTAGRAM_SERVICE, service);

            System.out.println(req.session().attributes());

            System.out.println(req.session().isNew());

            return authorizationUrl;

        });

        get("/registered", (req, res) -> {

            String code = req.queryParams("code");

            InstagramService service = (InstagramService) req.session().attribute(Constants.INSTAGRAM_SERVICE);

            Verifier verifier = new Verifier(code);

            Token accessToken = service.getAccessToken(null, verifier);
            Instagram instagram = new Instagram(accessToken);

            req.session().attribute(Constants.INSTAGRAM_OBJECT, instagram);

            System.out.println(req.session().attributes());

            return "Hello: " + req.queryParams("code") + "  :  ";

        });

        get("/profile", (req, res) -> {

            Object objInstagram = req.session().attribute(Constants.INSTAGRAM_OBJECT);

            Instagram instagram = null;

            if(objInstagram != null) {
                instagram = (Instagram) objInstagram;
            } else {
                res.redirect("/auth");
                halt();
            }

            UserInfoData userInfoData = instagram.getCurrentUserInfo().getData();

            System.out.println("Username : " + userInfoData.getUsername());


            return "hello again";

        });

        get("/likesmost", (req, res) -> {

            Object objInstagram = req.session().attribute(Constants.INSTAGRAM_OBJECT);

            Instagram instagram = null;

            if(objInstagram != null) {
                instagram = (Instagram) objInstagram;
            } else {
                res.redirect("/auth");
                halt();
            }

            UserInfoData userInfoData = null;
            if (instagram != null) {
                try {
                    userInfoData = instagram.getCurrentUserInfo().getData();
                } catch (InstagramException e) {
                    e.printStackTrace();
                }
            }

            MediaFeed mediaFeed = null;
            try {
                mediaFeed = instagram.getRecentMediaFeed(userInfoData.getId());
            } catch (InstagramException e) {
                e.printStackTrace();
            }
            List<MediaFeedData> mediaFeeds = mediaFeed.getData();

            LikesFeed likesFeed = null;
            List<User> users = null;

            Map<String, Integer> likerOccurances = new HashMap<String, Integer>();

            int i = 0;
            for (MediaFeedData mediaData : mediaFeeds) {
                System.out.println("media id : " + mediaData.getId());

                try {
                    likesFeed = instagram.getUserLikes(mediaData.getId());
                } catch (InstagramException e) {
                    e.printStackTrace();
                }
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
                @Override
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                            ((Map.Entry<String, Integer>) o1).getValue()
                    );
                }
            });

            Map<String, Integer> listMap = new HashMap<String, Integer>();

            for (Object e : a) {
                System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                + ((Map.Entry<String, Integer>) e).getValue());

                listMap.put(((Map.Entry<String, Integer>)e).getKey().toString(),
                        ((Map.Entry<String, Integer>) e).getValue());
            }

            //Set<Map.Entry<String, Integer>> entrySet = listMap.entrySet();

            return new ModelAndView(listMap, "likesmost.ftl");

        }, new FreeMarkerEngine());

    }
}