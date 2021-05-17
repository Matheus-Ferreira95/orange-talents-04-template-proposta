package br.com.zupacademy.matheus.propostas.feign.cartao;

import br.com.zupacademy.matheus.propostas.avisoviagem.AvisoViagemRequest;
import br.com.zupacademy.matheus.propostas.avisoviagem.AvisoViagemResponse;
import br.com.zupacademy.matheus.propostas.bloqueio.BloqueioCartaoRequest;
import br.com.zupacademy.matheus.propostas.bloqueio.BloqueioCartaoResponse;
import br.com.zupacademy.matheus.propostas.carteira.CarteiraRequest;
import br.com.zupacademy.matheus.propostas.carteira.ResultadoCarteira;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartoes", url = "${cartoes.url}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse buscarCartao(@RequestParam(value = "idProposta") Long idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse tentarBloquear(@PathVariable String id, @RequestBody BloqueioCartaoRequest request);

    @PostMapping("/api/cartoes/{id}/avisos")
    AvisoViagemResponse solicitaAvisoViagem(@PathVariable String id, @RequestBody AvisoViagemRequest request);

    @PostMapping("/api/cartoes/{id}/carteiras")
    ResultadoCarteira associaCartaoACarteira(@PathVariable String id, @RequestBody CarteiraRequest request);
}
