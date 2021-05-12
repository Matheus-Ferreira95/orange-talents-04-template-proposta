package br.com.zupacademy.matheus.propostas.proposta;

import br.com.zupacademy.matheus.propostas.compartilhado.ExecutorTransacao;
import br.com.zupacademy.matheus.propostas.compartilhado.handler.ApiErrorException;
import br.com.zupacademy.matheus.propostas.feign.analise.SolicitacaoAnaliseCliente;
import br.com.zupacademy.matheus.propostas.feign.analise.SolicitacaoAnaliseRequest;
import br.com.zupacademy.matheus.propostas.feign.analise.SolicitacaoAnaliseResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final Logger log = LoggerFactory.getLogger(PropostaController.class);
    private final ExecutorTransacao executorTransacao;
    private final SolicitacaoAnaliseCliente analiseCliente;

    public PropostaController(PropostaRepository propostaRepository, ExecutorTransacao executorTransacao,
                              SolicitacaoAnaliseCliente analiseCliente) {
        this.propostaRepository = propostaRepository;
        this.executorTransacao = executorTransacao;
        this.analiseCliente = analiseCliente;
    }

    @PostMapping
    public ResponseEntity<?> cadastroProposta(@RequestBody @Valid NovaPropostaRequest request) {
        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            // na prática eu não iria expor o documento dessa forma, como é só testes qqmuda?
            log.warn("Proposta não criada, portador do documento {} já criou uma proposta", request.getDocumento());
            return ResponseEntity.unprocessableEntity().body("Proposta recusada! Já existe uma proposta para o documento informado");
        }

        Proposta proposta = request.toModel();
        executorTransacao.salvaEComita(proposta);
        log.info("Proposta do documento {} criada com sucesso", request.getDocumento());

        consultaDados(proposta);
        executorTransacao.atualizaEComita(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body("Proposta cadastrada!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaPropostaPorId(@PathVariable Long id) {
        Optional<Proposta> possivelProposta = propostaRepository.findById(id);
        if (possivelProposta.isPresent()) {
            return ResponseEntity.ok(new PropostaResponse(possivelProposta.get()));
        }
        throw new ApiErrorException(HttpStatus.NOT_FOUND, "Não foi encontrada proposta com id " + id);
    }

    private void consultaDados(Proposta proposta) {
        try {
            SolicitacaoAnaliseRequest solicitacao = new SolicitacaoAnaliseRequest(proposta);
            SolicitacaoAnaliseResponse solicitacaoResponse = analiseCliente.consulta(solicitacao);
            proposta.setStatus(StatusProposta.ELEGIVEL);
            log.info("Proposta {} não apresenta restrição!", proposta.getId());
        } catch (FeignException.UnprocessableEntity ex) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
            log.warn("Proposta {} apresenta restrição financeira!", proposta.getId());
        }
    }
}
