package br.com.zupacademy.matheus.propostas.feign.cartao;

import br.com.zupacademy.matheus.propostas.bloqueio.BloqueioCartaoRequest;
import br.com.zupacademy.matheus.propostas.bloqueio.BloqueioCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartoes", url = "${cartoes.url}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse buscarCartao(@RequestParam(value = "idProposta") Long idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse tentarBloquear(@PathVariable String id, @RequestBody BloqueioCartaoRequest request);
}
