package ru.ekbtreeshelp.auth.config;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import ru.ekbtreeshelp.auth.entity.Provider;
import ru.ekbtreeshelp.auth.service.oauth2.FbOAuth2Service;
import ru.ekbtreeshelp.auth.service.oauth2.OAuth2Service;
import ru.ekbtreeshelp.auth.service.oauth2.VkOAuth2Service;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Config {
    private String vkAppId;
    private String vkSecretKey;
    private String fbAppId;
    private String fbSecretKey;
    private String baseUrl;

    @Bean
    public FacebookConnectionFactory facebookConnectionFactory() {
        return new FacebookConnectionFactory(fbAppId, fbSecretKey);
    }

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    Map<Provider, OAuth2Service> providersRegistry(FbOAuth2Service fbOAuth2Service,
                                                   VkOAuth2Service vkOAuth2Service) {
        return Map.of(
                Provider.fb, fbOAuth2Service,
                Provider.vk, vkOAuth2Service
        );
    }
}
