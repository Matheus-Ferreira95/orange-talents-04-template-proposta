package br.com.zupacademy.matheus.propostas.feign.cartao;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import br.com.zupacademy.matheus.propostas.proposta.Proposta;

import java.time.LocalDateTime;

public class CartaoResponse {

    private String id;
    private LocalDateTime emitidoEm;

    public CartaoResponse(String id, LocalDateTime emitidoEm) {
        this.id = id;
        this.emitidoEm = emitidoEm;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(id, emitidoEm, proposta);
    }
}
