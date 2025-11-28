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

            String seuEmailReal = "pedrohenriquemenezes76@gmail.com";

            Map<String, Object> bodyLider = new HashMap<>();
            bodyLider.put("from", "onboarding@resend.dev");
            bodyLider.put("to", seuEmailReal);
            bodyLider.put("subject", "NOVO VISITANTE: " + nome);

            String htmlLider = String.format(
                    "<div style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color: #d9534f;'>Novo Cadastro no Site!</h2>" +
                            "<div style='background-color: #f9f9f9; padding: 15px; border-radius: 5px; border-left: 5px solid #057EB9;'>" +
                            "<p><strong>Nome:</strong> %s</p>" +
                            "<p><strong>WhatsApp:</strong> <a href='https://wa.me/55%s'>%s</a></p>" +
                            "<p><strong>E-mail:</strong> %s</p>" +
                            "</div>" +
                            "</div>",
                    nome, telefone.replaceAll("[^0-9]", ""), telefone, emailVisitante
            );
            bodyLider.put("html", htmlLider);

            HttpEntity<Map<String, Object>> requestLider = new HttpEntity<>(bodyLider, headers);
            restTemplate.postForEntity(url, requestLider, String.class);

            System.out.println("Aviso enviado para o líder com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar: " + e.getMessage());
        }
    }
}