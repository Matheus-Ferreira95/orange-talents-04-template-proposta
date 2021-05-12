package br.com.zupacademy.matheus.propostas.feign.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartoes", url = "${cartoes.url}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse buscarCartao(@RequestParam(value = "idProposta") Long idProposta);
}
