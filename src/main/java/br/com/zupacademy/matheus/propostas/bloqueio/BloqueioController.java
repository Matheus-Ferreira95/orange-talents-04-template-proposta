package br.com.zupacademy.matheus.propostas.bloqueio;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    @PostMapping("/cartao/{id}")
    @Transactional
    public ResponseEntity<?> realizaBloqueio(@PathVariable Long id, HttpServletRequest request) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isEmpty()) {
            log.warn("Cartão de id {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = possivelCartao.get();
        if (cartao.isBlocked()) {
            log.warn("Cartão de id {} já se encontrada bloqueado", id);
            return ResponseEntity.unprocessableEntity().build();
        }

        String ip = request.getRemoteAddr();
        String user_Agente = request.getHeader("User-Agent");
        cartao.bloqueiaCartao();
        Bloqueio bloqueio = new Bloqueio(ip, user_Agente, cartao);
        bloqueioRepository.save(bloqueio);
        log.info("Bloqueio para o cartão de id {} realizado com sucesso", id);

        return ResponseEntity.ok().build();
    }
}
