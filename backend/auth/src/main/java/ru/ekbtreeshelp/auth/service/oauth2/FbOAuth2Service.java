package ru.ekbtreeshelp.auth.service.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.auth.config.OAuth2Config;
import ru.ekbtreeshelp.auth.constants.AuthConstants;
import ru.ekbtreeshelp.auth.entity.Provider;
import ru.ekbtreeshelp.auth.entity.User;
import ru.ekbtreeshelp.auth.repository.RoleRepository;
import ru.ekbtreeshelp.auth.repository.UserRepository;
import ru.ekbtreeshelp.auth.service.TokensService;
import ru.ekbtreeshelp.auth.utils.CookieUtils;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class FbOAuth2Service implements OAuth2Service {

    private final UserRepository userRepository;
    private final TokensService tokensService;
    private final OAuth2Config oAuth2Config;
    private final FacebookConnectionFactory factory;
    private final RoleRepository roleRepository;

    @Override
    public String getOAuthUri() {

        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(getRedirectUri());
        params.setScope("email,public_profile");

        return factory.getOAuthOperations().buildAuthenticateUrl(params);
    }

    @Override
    public void handleOAuthFlow(String authorizationCode, HttpServletResponse response) {

        OAuth2Operations operations = factory.getOAuthOperations();
        AccessGrant accessGrant = operations.exchangeForAccess(authorizationCode, getRedirectUri(), null);

        Connection<Facebook> connection = factory.createConnection(accessGrant);
        Facebook facebook = connection.getApi();
        String[] fields = {"id", "email", "first_name", "last_name"};
        org.springframework.social.facebook.api.User userProfile =
                facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);

        User user = userRepository.findByFbId(Long.valueOf(userProfile.getId()));

        if (user == null) {
            user = createNewUser(userProfile);
        }

        CookieUtils.setTokenCookies(response, tokensService.getTokensByOAuth2(user));
    }

    private String getRedirectUri() {
        return oAuth2Config.getBaseUrl() + "/auth/oauth2/fb/callback";
    }

    private User createNewUser(org.springframework.social.facebook.api.User userProfile) {

        User user = new User();

        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());
        user.setRoles(roleRepository.findAllByNames(AuthConstants.DEFAULT_ROLE_NAMES));
        user.setEnabled(true);
        user.setFbId(Long.valueOf(userProfile.getId()));
        user.setProvider(Provider.fb);

        userRepository.save(user);

        return user;
    }
}
