package me.rabrg.guerrilla.model;

import com.google.gson.annotations.SerializedName;

public final class EmailAddress {

    @SerializedName("email_addr")
    private final String emailAddress;

    @SerializedName("email_timestamp")
    private final long emailTimestamp;

    private final String alias;

    @SerializedName("alias_error")
    private final String aliasError;

    @SerializedName("sid_token")
    private final String sessionId;

    public EmailAddress(final String emailAddress, final long emailTimestamp, final String alias, final String aliasError, final String sessionId) {
        this.emailAddress = emailAddress;
        this.emailTimestamp = emailTimestamp;
        this.alias = alias;
        this.aliasError = aliasError;
        this.sessionId = sessionId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getEmailTimestamp() {
        return emailTimestamp;
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasError() {
        return aliasError;
    }

    public String getSessionId() {
        return sessionId;
    }
}
