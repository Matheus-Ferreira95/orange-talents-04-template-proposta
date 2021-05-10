package br.com.zupacademy.matheus.propostas.proposta;

import br.com.zupacademy.matheus.propostas.compartilhado.validacao.CpfOrCnpj;
import br.com.zupacademy.matheus.propostas.compartilhado.validacao.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CpfOrCnpj
    @UniqueValue(campo = "documento", domainClass = Proposta.class)
    private String documento;

    @NotBlank
    @Email
    @UniqueValue(campo = "email", domainClass = Proposta.class)
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Positive
    @NotNull
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email,
                               @NotBlank String nome, @NotBlank String endereco, @Positive @NotNull BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(nome, documento, email, endereco, salario);
    }
}
