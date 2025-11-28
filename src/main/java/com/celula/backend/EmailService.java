package com.celula.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String MEU_EMAIL = "pedrohenriquemenezes76@gmail.com";

    private final String LINK_MAPS = "-16.150226, -47.954381";
    private final String ENDERECO_TEXTO = "RUA JABOTICABAL, QD 82 LT 20, Luziânia - GO";

    public void enviarEmailDeConvite(String nome, String emailVisitante, String telefone) {
        try {
            SimpleMailMessage mensagemVisitante = new SimpleMailMessage();
            mensagemVisitante.setFrom(MEU_EMAIL);
            mensagemVisitante.setTo(emailVisitante);
            mensagemVisitante.setSubject("Bem-vindo à Geração Extraordinária! 🚀");

            String textoVisitante = String.format(
                    "Olá %s, a paz do Senhor!\n\n" +
                            "Que alegria receber seu contato! Ficamos muito honrados com seu interesse em conhecer nossa família.\n\n" +
                            "Somos a célula Geração Extraordinária, um lugar de comunhão, palavra e amigos.\n\n" +
                            "📅 NOSSOS ENCONTROS:\n" +
                            "Dia e Horário: Terça Feira ás 20:30\n" +
                            "📍 Local: %s\n" +
                            "🔗 Ver no Mapa: %s\n\n" +
                            "Um de nossos líderes entrará em contato pelo WhatsApp (%s) para tirar qualquer dúvida.\n\n" +
                            "Esperamos ver você em breve!\n\n" +
                            "Com carinho,\n" +
                            "Equipe Geração Extraordinária",
                    nome, ENDERECO_TEXTO, LINK_MAPS, telefone
            );
            mensagemVisitante.setText(textoVisitante);
            mailSender.send(mensagemVisitante);


            // --- 2. E-MAIL PARA VOCÊ (LÍDER) (Direto e Informativo) ---
            SimpleMailMessage mensagemLider = new SimpleMailMessage();
            mensagemLider.setFrom(MEU_EMAIL);
            mensagemLider.setTo(MEU_EMAIL);
            mensagemLider.setSubject("🎯 NOVO VISITANTE: " + nome);

            String textoLider = String.format(
                    "🔔 NOVO CADASTRO NO SITE!\n\n" +
                            "Uma pessoa acabou de preencher o formulário de interesse.\n\n" +
                            "📋 DADOS DO VISITANTE:\n" +
                            "--------------------------------------------------\n" +
                            "👤 Nome: %s\n" +
                            "📱 WhatsApp: %s\n" +
                            "✉️ E-mail: %s\n" +
                            "--------------------------------------------------\n\n" +
                            "✅ PRÓXIMOS PASSOS:\n" +
                            "1. Salve o contato na agenda.\n" +
                            "2. Chame no WhatsApp dando as boas-vindas.\n" +
                            "3. Convide para o próximo encontro.",
                    nome, telefone, emailVisitante
            );
            mensagemLider.setText(textoLider);
            mailSender.send(mensagemLider);

            System.out.println("E-mails enviados com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}