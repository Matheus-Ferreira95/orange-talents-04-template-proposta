package br.com.zupacademy.matheus.propostas.proposta;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Cartao cartao;

    @Deprecated
    public Proposta() {}

    public Proposta(String nome, String documento, String email, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setCartao(@NotNull Cartao cartao) {
        Assert.notNull(cartao, "cartão não pode ser nulo");
        this.cartao = cartao;
    }
}

