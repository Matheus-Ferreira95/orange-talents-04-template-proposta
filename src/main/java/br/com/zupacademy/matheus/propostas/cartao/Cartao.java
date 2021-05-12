package br.com.zupacademy.matheus.propostas.cartao;

import br.com.zupacademy.matheus.propostas.proposta.Proposta;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id
    private String id;

    private LocalDateTime emitidoEm;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Proposta proposta;

    @Deprecated
    public Cartao() {}

    public Cartao(String id, LocalDateTime emitidoEm, Proposta proposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.proposta = proposta;
    }
}
