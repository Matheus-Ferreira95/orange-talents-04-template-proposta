package br.com.zupacademy.matheus.propostas.avisoviagem;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDate termino;

    @CreationTimestamp
    private LocalDateTime avisoFeitoEm;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String user_agente;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {}

    public AvisoViagem(String destino, LocalDate termino, String ip, String user_agente, Cartao cartao) {
        this.destino = destino;
        this.termino = termino;
        this.ip = ip;
        this.user_agente = user_agente;
        this.cartao = cartao;
    }
}
