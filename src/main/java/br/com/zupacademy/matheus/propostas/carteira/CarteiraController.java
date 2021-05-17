package br.com.zupacademy.matheus.propostas.carteira;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.cartao.CartaoRepository;
import br.com.zupacademy.matheus.propostas.compartilhado.handler.ApiErrorException;
import br.com.zupacademy.matheus.propostas.feign.cartao.CartaoClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final Logger log = LoggerFactory.getLogger(CarteiraController.class);
    private final CartaoClient cartaoClient;
    private final CartaoRepository cartaoRepository;
    private final CarteiraRepository carteiraRepository;

    public CarteiraController(CartaoClient cartaoClient, CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository) {
        this.cartaoClient = cartaoClient;
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/cartao/{id}")
    @Transactional
    public ResponseEntity<?> associaCartaoACarteira(@PathVariable Long id, @RequestBody @Valid CarteiraRequest request) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isEmpty()) {
            log.warn("Cartão de id {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = possivelCartao.get();
        Carteira carteira;
        try {
            ResultadoCarteira resultadoCarteira = cartaoClient.associaCartaoACarteira(cartao.getNumeroCartao(), request);
            carteira = request.toModel(cartao, resultadoCarteira.getId());
            carteiraRepository.save(carteira);
            log.info("Cartão {} associado com sucesso à carteira {}", id, request.getCarteira());

        } catch (FeignException.UnprocessableEntity ex) {
            log.warn("cartão de id {} já se encontra associado a carteira {}", id, request.getCarteira());
            return ResponseEntity.unprocessableEntity().body("Não é possivel associar mais de uma vez um cartão para a mesma carteira digital");

        } catch (FeignException ex) {
            ex.printStackTrace();
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Aconteceu algo inesperado, por favor contate o administrador do sistema");
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
