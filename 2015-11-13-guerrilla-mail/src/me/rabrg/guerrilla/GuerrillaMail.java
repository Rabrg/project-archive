package me.rabrg.guerrilla;

import me.rabrg.guerrilla.model.EmailAddress;
import me.rabrg.guerrilla.model.EmailUser;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.io.IOException;

public final class GuerrillaMail {

    /**
     * The Retrofit instance.
     */
    private final Retrofit retrofit;

    /**
     * The service interface for HTTP requests.
     */
    private final GuerrillaMailService service;

    /**
     * The language code in the IETF language tag scheme.
     */
    private String languageCode;

    /**
     * The current session id used to distinguish the client.
     */
    private String sessionId = "";

    /**
     * The current email address.
     */
    private String email;

    /**
     * The UNIX timestamp of when the email address was created.
     */
    private long emailTimestamp;

    /**
     * The current alias for the email address.
     */
    private String alias;

    /**
     * Constructs a new instance with the English language code.
     */
    public GuerrillaMail() {
        this("en");
    }

    /**
     * Constructs a new instance with the specified language code.
     * @param languageCode The language code following the IETF language tag scheme.
     */
    public GuerrillaMail(final String languageCode) {
        retrofit = new Retrofit.Builder().baseUrl("http://api.guerrillamail.com/ajax.php").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(GuerrillaMailService.class);

        setLanguageCode(languageCode);
    }

    public static void main(final String[] args) throws IOException {
        final GuerrillaMail mail = new GuerrillaMail();
        {
            final EmailAddress address = mail.createEmailAddress();
            System.out.println(address.getEmailAddress() + " " + address.getEmailTimestamp() + " " + address.getAlias() + " " + address.getAliasError() + " " + address.getSessionId());
        }
        final EmailUser address = mail.setEmailUser("rabrgtest3");
        System.out.println(address.getEmailAddress() + " " + address.getEmailTimestamp() + " " + address.getAlias() + " " + address.getAliasError() + " " + address.getSessionId());
    }

    /**
     * Creates a new randomly generated email address with the default Guerrilla Mail domain.
     * @return The email address.
     */
    public EmailAddress createEmailAddress() {
        return createEmailAddress("");
    }

    /**
     * Creates a new randomly generate email address with the specified domain.
     * @param domain The domain.
     * @return The email address.
     */
    public EmailAddress createEmailAddress(final String domain) {
        try {
            final EmailAddress address = service.getEmailAddress(getLanguageCode(), getSessionId(), domain).execute().body();
            setSessionId(address.getSessionId());
            setEmail(address.getEmailAddress());
            setEmailTimestamp(address.getEmailTimestamp());
            setAlias(address.getAlias());
            return address;
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to create email address with parameters [languageCode=" + getLanguageCode() + ", sessionId=" + getSessionId() + ", domain=" + domain + "]", e);
        }
    }

    /**
     * Sets the email user part of the email address and the default Guerrilla Mail domain.
     * @param emailUser The email user part of the email address.
     * @return The email user.
     */
    public EmailUser setEmailUser(final String emailUser) {
        return setEmailUser(emailUser, "");
    }

    /**
     * Sets the email user part of the email address with the specified domain.
     * @param emailUser The email user.
     * @param domain The domain.
     * @return The email user.
     */
    public EmailUser setEmailUser(final String emailUser, final String domain) {
        try {
            final EmailUser user = service.setEmailUser(emailUser, getLanguageCode(), getSessionId(), domain).execute().body();
            setSessionId(user.getSessionId());
            setEmail(user.getEmailAddress());
            setEmailTimestamp(user.getEmailTimestamp());
            setAlias(user.getAlias());
            return user;
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to create email address with parameters [languageCode=" + getLanguageCode() + ", sessionId=" + getSessionId() + ", domain=" + domain + "]", e);
        }
    }

    /**
     * Sets the language code in IETF language tag scheme.
     * @param languageCode The language code.
     */
    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Gets the language code in IETF language tag scheme.
     * @return The language code.
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the session id to the specified session id.
     * @param sessionId The sessioni d.
     */
    private void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the  session id.
     * @return The session id.
     */
    private String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the email address to the specified email address.
     * @param email The email address.
     */
    private void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the email address.
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the email address timestamp.
     * @return The email address timestamp.
     */
    private long getEmailTimestamp() {
        return emailTimestamp;
    }

    /**
     * Sets the email address timestamp.
     * @param emailTimestamp The email address timestamp.
     */
    private void setEmailTimestamp(long emailTimestamp) {
        this.emailTimestamp = emailTimestamp;
    }

    /**
     * Sets the email address's alias.
     * @param alias The email address's alias.
     */
    private void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Gets the email address's alias.
     * @return The email address's alias.
     */
    public String getAlias() {
        return alias;
    }
}
