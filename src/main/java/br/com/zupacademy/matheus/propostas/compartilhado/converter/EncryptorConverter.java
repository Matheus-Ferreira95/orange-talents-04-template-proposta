package br.com.zupacademy.matheus.propostas.compartilhado.converter;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EncryptorConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try{
            return Encryptors.queryableText("${proposta.encryptor.secret}", "12345678").encrypt(attribute);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String attribute) {
        try{
            return Encryptors.queryableText("${proposta.encryptor.secret}", "12345678").decrypt(attribute);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
