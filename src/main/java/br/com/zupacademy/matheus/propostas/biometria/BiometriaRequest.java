package br.com.zupacademy.matheus.propostas.biometria;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;


import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaRequest {

    @NotBlank
    private String fingerPrint;

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        byte[] fingerPrintBase64 = Base64.getDecoder().decode(fingerPrint.getBytes());
        String mensagemDecodificada = new String(fingerPrintBase64);
        return new Biometria(mensagemDecodificada, cartao);
    }
}
