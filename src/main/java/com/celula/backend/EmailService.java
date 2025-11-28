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
            bodyVisitante.put("subject", "Ola! Mensagem da Celula");

            String htmlVisitante = String.format(
                    "<p>Ola %s.</p>" +
                            "<p>Recebemos seu cadastro com sucesso.</p>" +
                            "<p>Vamos entrar em contato pelo WhatsApp em breve.</p>",
                    nome
            );
            bodyVisitante.put("html", htmlVisitante);

            HttpEntity<Map<String, Object>> requestVisitante = new HttpEntity<>(bodyVisitante, headers);
            restTemplate.postForEntity(url, requestVisitante, String.class);

            String seuEmailReal = "pedrohenriquemenezes76@gmail.com";

            Map<String, Object> bodyLider = new HashMap<>();
            bodyLider.put("from", "onboarding@resend.dev");
            bodyLider.put("to", seuEmailReal);
            bodyLider.put("subject", "Novo cadastro no site");

            String htmlLider = String.format(
                    "<p>Novo cadastro recebido:</p>" +
                            "<p>Nome: %s</p>" +
                            "<p>Telefone: %s</p>" +
                            "<p>Email: %s</p>",
                    nome, telefone, emailVisitante
            );
            bodyLider.put("html", htmlLider);

            HttpEntity<Map<String, Object>> requestLider = new HttpEntity<>(bodyLider, headers);
            restTemplate.postForEntity(url, requestLider, String.class);

            System.out.println("Emails enviados com sucesso via Resend");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar: " + e.getMessage());
        }
    }
}