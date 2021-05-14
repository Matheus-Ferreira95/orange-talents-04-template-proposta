package br.com.zupacademy.matheus.propostas.cartao;

import br.com.zupacademy.matheus.propostas.biometria.Biometria;
import br.com.zupacademy.matheus.propostas.proposta.Proposta;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    private LocalDateTime emitidoEm;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private Set<Biometria> biometrias;

    @Enumerated(EnumType.STRING)
    private StatusCartao status = StatusCartao.ATIVO;

    @Deprecated
    public Cartao() {}

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void bloqueiaCartao() {
        this.status = StatusCartao.BLOQUEADO;
    }

    public boolean isBlocked() {
        return status.equals(StatusCartao.BLOQUEADO);
    }
}
