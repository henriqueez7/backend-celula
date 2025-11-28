package com.celula.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    public void enviarEmailDeConvite(String nome, String emailVisitante, String telefone) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.resend.com/emails";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + resendApiKey);

            Map<String, Object> bodyVisitante = new HashMap<>();
            bodyVisitante.put("from", "onboarding@resend.dev");
            bodyVisitante.put("to", emailVisitante);
            bodyVisitante.put("subject", "Seja muito bem-vindo(a)! 🚀 | Geração Extraordinária");

            String htmlVisitante = String.format(
                    "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px;'>" +
                            "<h2 style='color: #057EB9;'>Olá %s, a paz do Senhor! 🕊️</h2>" +
                            "<p>Que alegria imensa receber seu contato! Ficamos muito honrados com seu interesse em conhecer nossa família.</p>" +
                            "<p>Somos a célula <strong>Geração Extraordinária</strong>, um lugar de comunhão, palavra de Deus e novas amizades.</p>" +
                            "<hr style='border: 1px solid #eee; margin: 20px 0;'>" +
                            "<h3>📅 Venha participar do nosso próximo encontro:</h3>" +
                            "<p><strong>Quando:</strong> Todas ás terça feira, às 20:30h</p>" +
                            "<p><strong>Onde:</strong> RUA JABOTICABAL, QD 82 LT 20, Luziânia - GO</p>" +
                            "<p><a href='https://maps.app.goo.gl/UAemPdwa7x83QFXa6' style='background-color: #057EB9; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px;'>📍 Abrir no Google Maps</a></p>" +
                            "<hr style='border: 1px solid #eee; margin: 20px 0;'>" +
                            "<p>Já anotamos seu WhatsApp <strong>(%s)</strong> e, em breve, um de nossos líderes entrará em contato para te dar as boas-vindas pessoalmente.</p>" +
                            "<p>Esperamos ver você em breve!</p>" +
                            "<p>Com carinho,<br><strong>Equipe Geração Extraordinária</strong></p>" +
                            "</div>",
                    nome, telefone
            );
            bodyVisitante.put("html", htmlVisitante);

            HttpEntity<Map<String, Object>> requestVisitante = new HttpEntity<>(bodyVisitante, headers);
            restTemplate.postForEntity(url, requestVisitante, String.class);

            String seuEmailReal = "pedrohenriquemenezes76@gmail.com";

            Map<String, Object> bodyLider = new HashMap<>();
            bodyLider.put("from", "onboarding@resend.dev");
            bodyLider.put("to", seuEmailReal);
            bodyLider.put("subject", "🎯 NOVO VISITANTE: " + nome);

            String htmlLider = String.format(
                    "<div style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color: #d9534f;'>🔔 Novo Cadastro no Site!</h2>" +
                            "<p>Uma pessoa acabou de preencher o formulário de interesse. Entre em contato o quanto antes!</p>" +
                            "<div style='background-color: #f9f9f9; padding: 15px; border-radius: 5px; border-left: 5px solid #057EB9;'>" +
                            "<p><strong>👤 Nome:</strong> %s</p>" +
                            "<p><strong>📱 WhatsApp:</strong> <a href='https://wa.me/55%s'>%s</a> (Clique para chamar)</p>" +
                            "<p><strong>✉️ E-mail:</strong> %s</p>" +
                            "</div>" +
                            "<p><em>Sistema de Landing Page - Geração Extraordinária</em></p>" +
                            "</div>",
                    nome, telefone.replaceAll("[^0-9]", ""), telefone, emailVisitante
            );
            bodyLider.put("html", htmlLider);

            HttpEntity<Map<String, Object>> requestLider = new HttpEntity<>(bodyLider, headers);
            restTemplate.postForEntity(url, requestLider, String.class);

            System.out.println("E-mails enviados via Resend API com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar: " + e.getMessage());
        }
    }
}