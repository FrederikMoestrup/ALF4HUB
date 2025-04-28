package dat.services;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


// To setup MailHog,open docker desktop and open a terminal in the bottom right corner. Run the following command: docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog
// MailHog frontend view will be available at http://localhost:8025

public class EmailService {

    // --- MailHog SMTP Configuration ---
    // Note: In a real app, configure these via properties, not hardcoded!
    // Using hardcoded MailHog details
    private final String smtpHost = "localhost"; // MailHog host
    private final int smtpPort = 1025;          // MailHog SMTP port
    private final String smtpUsername = null; // MailHog default (no auth)
    private final String smtpPassword = null; // MailHog default (no auth)
    private final boolean startTlsEnabled = false; // Not needed for MailHog
    private final boolean sslOnConnect = false;    // Not needed for MailHog
    private final String senderEmail = "no-reply@altf4hub.com"; // The 'From' address for emails

    public void sendEmail(String recipientEmail, String subject, String body) {

        SimpleEmail email = new SimpleEmail();

        try {
            // 1. Configure the SMTP server details
            email.setHostName(this.smtpHost);
            email.setSmtpPort(this.smtpPort);

            // Only set authenticator if required by the SMTP server (MailHog default is none)
            if (this.smtpUsername != null && this.smtpPassword != null) {
                email.setAuthenticator(new DefaultAuthenticator(this.smtpUsername, this.smtpPassword));
            }


            email.setStartTLSEnabled(this.startTlsEnabled);
            email.setSSLOnConnect(this.sslOnConnect);


            email.setFrom(this.senderEmail);
            email.addTo(recipientEmail);
            email.setSubject(subject);
            email.setMsg(body);


            email.send();

            System.out.println("Email sent successfully via MailHog to " + recipientEmail);

        } catch (EmailException e) {

            System.err.println("Error sending email via MailHog: " + e.getMessage());
            e.printStackTrace();

        }
    }

}
