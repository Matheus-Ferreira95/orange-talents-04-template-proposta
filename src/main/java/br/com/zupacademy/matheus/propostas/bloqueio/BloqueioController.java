package br.com.zupacademy.matheus.propostas.bloqueio;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.cartao.CartaoRepository;
import br.com.zupacademy.matheus.propostas.compartilhado.handler.ApiErrorException;
import br.com.zupacademy.matheus.propostas.feign.cartao.CartaoClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    private final Logger log = LoggerFactory.getLogger(BloqueioController.class);

    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final CartaoClient cartaoClient;

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository, CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoClient = cartaoClient;
    }

    @Transactional
    @PostMapping("/cartao/{id}")
    public ResponseEntity<?> realizaBloqueio(@PathVariable Long id, HttpServletRequest request,
                                             @AuthenticationPrincipal Jwt usuario) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isEmpty()) {
            log.warn("Cartão de id {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        Object email = usuario.getClaims().get("email");
        Cartao cartao = possivelCartao.get();
        if (cartao.pertenceAPropostaDoEmail(email)) {
            try {
                BloqueioCartaoRequest bloqueioCartaoRequest = new BloqueioCartaoRequest("propostas");
                BloqueioCartaoResponse bloqueioCartaoResponse = cartaoClient.tentarBloquear(cartao.getNumeroCartao(), bloqueioCartaoRequest);
                cartao.bloqueiaCartao();
                Bloqueio bloqueio = criaBloqueio(request, cartao);
                bloqueioRepository.save(bloqueio);
                log.info("Bloqueio para o cartão de id {} realizado com sucesso", id);
                return ResponseEntity.ok().build();
            } catch (FeignException.UnprocessableEntity ex) {
                log.warn("Cartão de id {} já se encontrada bloqueado", id);
                return ResponseEntity.unprocessableEntity().build();
            } catch (FeignException ex) {
                log.warn("Algo de errado não está certo xD");
            }
        }

      throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Só é permitido bloquear seu próprio cartão");
    }

    private Bloqueio criaBloqueio(HttpServletRequest request, Cartao cartao) {
        String ip = request.getRemoteAddr();
        String user_Agente = request.getHeader("User-Agent");
        return new Bloqueio(ip, user_Agente, cartao);
    }
}
