package com.msrappel.Services;

import com.msrappel.Models.RappelDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void sendRappelEmail(RappelDTO rappel) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("sebastien.cossus@gmail.com");
            helper.setTo(rappel.getUserEmail());
            helper.setSubject("🔔 Rappel : votre rendez-vous approche !");
            helper.setText(buildEmailBody(rappel), true); // true = HTML

            mailSender.send(message);
            log.info("Email envoyé à {} pour le RDV {}", rappel.getUserEmail(), rappel.getRdvId());
        } catch (Exception e) {
            log.error("Erreur envoi email à {} : {}", rappel.getUserEmail(), e.getMessage());
        }
    }

    private String buildEmailBody(RappelDTO rappel) {
        String delaiTexte = formatDelai(rappel.getDelai());

        return """
                <!DOCTYPE html>
                <html lang="fr">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin:0; padding:0; background-color:#B0E1FA; font-family:'Roboto',Arial,sans-serif;">
                
                    <!-- Container -->
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#B0E1FA; padding:30px 0;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0"
                                       style="max-width:600px; width:100%%;">
                
                                    <!-- Header -->
                                    <tr>
                                        <td style="background-color:#4D66F2; border-radius:25px 25px 0 0;
                                                   padding:20px 30px; text-align:center;">
                                            <h1 style="color:#F79BC5; font-size:26px; margin:0;
                                                       font-family:'Roboto',Arial,sans-serif;">
                                                🔔 Rappel Facile
                                            </h1>
                                        </td>
                                    </tr>
                
                                    <!-- Bandeau prestataire (rose comme l'app) -->
                                    <tr>
                                        <td style="background-color:#F79BC5; padding:15px 30px;
                                                   text-align:center; border-bottom:1px solid #ccc;">
                                            <p style="margin:0; font-size:16px; font-weight:bold;">
                                                %s %s
                                            </p>
                                            <p style="margin:0; font-size:12px; color:#555;">
                                                %s
                                            </p>
                                        </td>
                                    </tr>
                
                                    <!-- Titre -->
                                    <tr>
                                        <td style="background-color:#ffffff; padding:25px 30px 10px 30px;
                                                   text-align:center;">
                                            <h2 style="font-size:18px; margin:0; color:#000000;">
                                                Votre rendez-vous approche !
                                            </h2>
                                            <p style="font-size:13px; color:#555; margin-top:8px;">
                                                Rappel prévu <strong>%s</strong> avant le rendez-vous
                                            </p>
                                        </td>
                                    </tr>
                
                                    <!-- Carte Date (fond1 = rose) -->
                                    <tr>
                                        <td style="background-color:#ffffff; padding:5px 30px;">
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td style="background-color:#F79BC5; border-radius:15px;
                                                               padding:12px 20px; margin-bottom:10px;">
                                                        <table width="100%%">
                                                            <tr>
                                                                <td style="font-size:13px; font-weight:bold;">
                                                                    📅 Date
                                                                </td>
                                                                <td style="font-size:13px; text-align:right;">
                                                                    %s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                
                                    <!-- Carte Heure (fond2 = violet) -->
                                    <tr>
                                        <td style="background-color:#ffffff; padding:5px 30px;">
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td style="background-color:#E8C9FA; border-radius:15px;
                                                               padding:12px 20px;">
                                                        <table width="100%%">
                                                            <tr>
                                                                <td style="font-size:13px; font-weight:bold;">
                                                                    ⏰ Heure
                                                                </td>
                                                                <td style="font-size:13px; text-align:right;">
                                                                    %s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                
                                    <!-- Carte Motif (fond3 = jaune) -->
                                    <tr>
                                        <td style="background-color:#ffffff; padding:5px 30px 20px 30px;">
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td style="background-color:#EFE9B7; border-radius:15px;
                                                               padding:12px 20px;">
                                                        <table width="100%%">
                                                            <tr>
                                                                <td style="font-size:13px; font-weight:bold;">
                                                                    📋 Motif
                                                                </td>
                                                                <td style="font-size:13px; text-align:right;">
                                                                    %s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color:#4D66F2; border-radius:0 0 25px 25px;
                                                   padding:20px 30px; text-align:center;">
                                            <p style="color:white; font-size:12px; margin:0;">
                                                Rappel Facile — Ne ratez plus aucun rendez-vous
                                            </p>
                                            <p style="color:#B0E1FA; font-size:11px; margin:5px 0 0 0;">
                                                Cet email a été envoyé automatiquement, merci de ne pas y répondre.
                                            </p>
                                        </td>
                                    </tr>
                
                                </table>
                            </td>
                        </tr>
                    </table>
                
                </body>
                </html>
                """.formatted(
                rappel.getPrestataireNom(),
                rappel.getPrestatairePrenom(),
                rappel.getMotif(),
                delaiTexte,
                rappel.getDateRdv().toLocalDate().toString(),
                rappel.getDateRdv().toLocalTime().toString(),
                rappel.getMotif()
        );
    }

    private String formatDelai(Integer delai) {
        if (delai < 60) return delai + " minutes";
        if (delai == 60) return "1 heure";
        if (delai < 1440) return (delai / 60) + " heures";
        if (delai == 1440) return "1 jour";
        return (delai / 1440) + " jours";
    }
}