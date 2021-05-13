package br.com.zupacademy.matheus.propostas.biometria;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/biometria")
public class NovaBiometriaController {

    private final Logger log = LoggerFactory.getLogger(NovaBiometriaController.class);

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @PostMapping("/cartao/{id}")
    public ResponseEntity<?> cadastraBiometria(@PathVariable Long id, @RequestBody @Valid BiometriaRequest request,
                                               UriComponentsBuilder uriBuilder) {
        Cartao cartao = manager.find(Cartao.class, id);
        if (cartao == null) {
            log.warn("Cartão de id {} não existente", id);
            return ResponseEntity.notFound().build();
        }
        Biometria biometria = request.toModel(cartao);
        manager.persist(biometria);
        URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
        log.info("Biometria {} cadastrada com sucesso", biometria.getId());
        return ResponseEntity.created(uri).build();
    }
}
