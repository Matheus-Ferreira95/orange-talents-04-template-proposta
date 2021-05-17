package br.com.zupacademy.matheus.propostas.carteira;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCarteira carteira;

    @Column(nullable = false)
    private String idCarteira;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira () {}

    public Carteira(String email, TipoCarteira carteira, Cartao cartao, String idCarteira) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
        this.idCarteira = idCarteira;
    }

    public Long getId() {
        return id;
    }
}
