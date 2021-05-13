package br.com.zupacademy.matheus.propostas.biometria;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String fingerPrint;

    @CreationTimestamp
    private LocalDateTime associadaEm;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Biometria(){}

    public Biometria(String fingerPrint, Cartao cartao) {
        this.fingerPrint = fingerPrint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
