package com.springboottest.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.web.source")
@PropertySource(value = "classpath:resource.properties")
public class Resource {
    private String website;
    private String domain;
    private String httpAgreement;
    private String picPre;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHttpAgreement() {
        return httpAgreement;
    }

    public void setHttpAgreement(String httpAgreement) {
        this.httpAgreement = httpAgreement;
    }

    public String getPicPre() {
        return picPre;
    }

    public void setPicPre(String picPre) {
        this.picPre = picPre;
    }
}
