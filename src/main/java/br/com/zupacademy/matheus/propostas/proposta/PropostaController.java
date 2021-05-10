package br.com.zupacademy.matheus.propostas.proposta;

import br.com.zupacademy.matheus.propostas.compartilhado.handler.ApiErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final Logger log = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastroProposta(@RequestBody @Valid NovaPropostaRequest request,
                                                 UriComponentsBuilder uriBuilder) {

        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            // na prática eu não iria expor o documento dessa forma, como é só testes qqmuda?
            log.warn("Proposta não criada, portador do documento {} já criou uma proposta", request.getDocumento());
            return ResponseEntity.unprocessableEntity().body(new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Proposta recusada! Já existe uma proposta para o documento informado"));
        }

        Proposta proposta = request.toModel();
        propostaRepository.save(proposta);
        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        log.info("Proposta do documento ={} criada com sucesso", request.getDocumento());
        return ResponseEntity.created(uri).body("Proposta criada com sucesso!");
    }
}
