package br.com.zupacademy.matheus.propostas.feign.cartao;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.proposta.Proposta;
import br.com.zupacademy.matheus.propostas.proposta.PropostaRepository;
import br.com.zupacademy.matheus.propostas.proposta.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.List;

@Configuration
public class BuscaCartaoScheduling {

    private final Logger log = LoggerFactory.getLogger(BuscaCartaoScheduling.class);

    private final PropostaRepository propostaRepository;
    private final CartaoClient cartaoClient;

    public BuscaCartaoScheduling(PropostaRepository propostaRepository, CartaoClient cartaoClient) {
        this.propostaRepository = propostaRepository;
        this.cartaoClient = cartaoClient;
    }

    @Scheduled(fixedDelay = 50000)
    @Transactional
    public void buscaCartao() {
        List<Proposta> propostas = propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);
        for (Proposta proposta : propostas) {
            try {
                CartaoResponse cartaoResponse = cartaoClient.buscarCartao(proposta.getId());
                Cartao cartao = cartaoResponse.toModel(proposta);
                proposta.setCartao(cartao);
                propostaRepository.save(proposta);
                log.info("Cartão gerado para a proposta {}", proposta.getId());
            } catch (FeignException ex) {
                ex.printStackTrace();
                log.info("Proposta {} ainda não possui cartão!", proposta.getId());
            }
        }
    }
}
