package com.ads.restaurante;

import com.ads.restaurante.model.Manager;
import com.ads.restaurante.repository.ManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria um manager padrão na primeira execução (tabela vazia), senão não haveria como
 * fazer o primeiro login para depois cadastrar os demais. Não faz nada se já existe manager.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminPassword;

    public DataInitializer(ManagerRepository managerRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${admin.password:admin12345}") String adminPassword) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (managerRepository.count() > 0) {
            return;
        }
        managerRepository.save(new Manager(
                "admin",
                passwordEncoder.encode(adminPassword),
                "00000000000",
                "admin@restaurante.local"
        ));
        log.warn("Nenhum manager encontrado — criado 'admin' com a senha de admin.password "
                + "(padrão: admin12345). Troque em produção.");
    }
}
