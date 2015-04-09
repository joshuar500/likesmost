import jlinsta.Constants;
import jlinsta.InstagramUtils;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.oauth.InstagramService;

import java.util.Properties;

public class InstaAuth {

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


}
