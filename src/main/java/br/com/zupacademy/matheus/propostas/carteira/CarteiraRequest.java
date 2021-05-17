package br.com.zupacademy.matheus.propostas.carteira;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public CarteiraRequest(@NotBlank @Email String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao, String idCarteira) {
        return new Carteira(email, carteira, cartao, idCarteira);
    }
}
