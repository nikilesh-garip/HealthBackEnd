package com.healthcare.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioNotificationService {
    private final String accountSid;
    private final String authToken;
    private final String fromNumber;

    public TwilioNotificationService(@Value("${twilio.account-sid}") String accountSid,
                                     @Value("${twilio.auth-token}") String authToken,
                                     @Value("${twilio.from-number}") String fromNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromNumber = fromNumber;
    }

    @PostConstruct
    public void init() {
        if (accountSid != null && authToken != null) {
            Twilio.init(accountSid, authToken);
        }
    }

    public void sendSms(String to, String body) {
        if (to == null || to.isBlank() || fromNumber == null || fromNumber.isBlank()) {
            throw new IllegalArgumentException("Missing to/from number");
        }
        Message.creator(new PhoneNumber(to), new PhoneNumber(fromNumber), body).create();
    }
}
