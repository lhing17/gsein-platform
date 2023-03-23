package cn.gsein.platform.system.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security.ignore")
@Component
public class SecurityIgnoreUrl {
    private String[] urls;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
