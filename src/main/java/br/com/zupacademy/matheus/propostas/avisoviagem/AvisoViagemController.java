package br.com.zupacademy.matheus.propostas.avisoviagem;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.cartao.CartaoRepository;
import br.com.zupacademy.matheus.propostas.compartilhado.handler.ApiErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    private final Logger log = LoggerFactory.getLogger(AvisoViagemController.class);
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
    }

    @PostMapping("/aviso-viagem/cartao/{id}")
    @Transactional
    public ResponseEntity<?> salvaAvisoViagem(@PathVariable Long id, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                              @AuthenticationPrincipal Jwt usuario, HttpServletRequest httpServletRequest) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isEmpty()) {
            log.warn("Cartão de id {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = possivelCartao.get();
        Object email = usuario.getClaims().get("email");
        if (cartao.pertenceAPropostaDoEmail(email)) {
            String ip = httpServletRequest.getRemoteAddr();
            String user_agente = httpServletRequest.getHeader("User-Agent");
            AvisoViagem avisoViagem = avisoViagemRequest.toModel(ip, user_agente, cartao);
            avisoViagemRepository.save(avisoViagem);
            log.info("Aviso de viagem para o cartao {} feito com sucesso", id);
            return ResponseEntity.ok().build();
        }

        throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Só é permitido solocitar aviso de viagem para o seu próprio cartão");
    }
}
