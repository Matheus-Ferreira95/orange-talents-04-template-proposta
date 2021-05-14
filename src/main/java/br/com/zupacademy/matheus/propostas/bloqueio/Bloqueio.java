package br.com.zupacademy.matheus.propostas.bloqueio;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime bloqueadoEm;

    private String ipCliente;

    private String userAgent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    public Bloqueio(String ipCliente, String userAgent, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
