package me.rabrg.guerrilla.model;

import com.google.gson.annotations.SerializedName;

public final class EmailUser {

    @SerializedName("alias_error")
    private final String aliasError;

    private final String alias;

    @SerializedName("email_addr")
    private final String emailAddress;

    @SerializedName("email_timestamp")
    private final long emailTimestamp;

    @SerializedName("site_id")
    private final int siteId;

    @SerializedName("sid_token")
    private final String sessionId;

    private final String site;

    public EmailUser(final String aliasError, final String alias, final String emailAddress, final long emailTimestamp, final int siteId, final String sessionId, final String site) {
        this.aliasError = aliasError;
        this.alias = alias;
        this.emailAddress = emailAddress;
        this.emailTimestamp = emailTimestamp;
        this.siteId = siteId;
        this.sessionId = sessionId;
        this.site = site;
    }

    public String getAliasError() {
        return aliasError;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getEmailTimestamp() {
        return emailTimestamp;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSite() {
        return site;
    }
}
