package com.celula.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ConviteController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> receberConvite(@RequestBody ConvidadoDTO convidado) {
        try {
            System.out.println("--- Novo contato recebido ---");
            System.out.println("Nome: " + convidado.nome());

            emailService.enviarEmailDeConvite(convidado.nome(), convidado.email(), convidado.telefone());

            return ResponseEntity.ok("Convite enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro no console do IntelliJ
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}