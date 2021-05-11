package br.com.zupacademy.matheus.propostas.feign.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "solicitacao", url = "http://localhost:9999")
public interface SolicitacaoAnaliseCliente {

    @PostMapping("/api/solicitacao")
    SolicitacaoAnaliseResponse consulta(@RequestBody SolicitacaoAnaliseRequest request);
}
