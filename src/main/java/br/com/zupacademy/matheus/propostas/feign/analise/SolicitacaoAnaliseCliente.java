package br.com.zupacademy.matheus.propostas.feign.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${solicitacaoAnalise.name}", url = "${solicitacaoAnalise.url}")
public interface SolicitacaoAnaliseCliente {

    @PostMapping("/api/solicitacao")
    SolicitacaoAnaliseResponse consulta(SolicitacaoAnaliseRequest request);
}
