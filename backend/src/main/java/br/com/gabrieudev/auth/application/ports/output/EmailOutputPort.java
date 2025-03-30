package br.com.gabrieudev.auth.application.ports.output;

public interface EmailOutputPort {
    boolean sendEmail(String to, String subject, String body);
}
