package br.com.zupacademy.matheus.propostas.biometria;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.springframework.util.Assert;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String fingerPrint;

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        byte[] fingerPrintBase64 = Base64.decodeBase64(fingerPrint);
        return new Biometria(new String(fingerPrintBase64), cartao);
    }
}
