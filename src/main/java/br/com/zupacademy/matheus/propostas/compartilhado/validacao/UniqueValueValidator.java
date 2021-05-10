package br.com.zupacademy.matheus.propostas.compartilhado.validacao;

import br.com.zupacademy.matheus.propostas.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String campo;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue params) {
        campo = params.campo();
        domainClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Boolean uniqueValue = manager.createQuery("SELECT COUNT(T) < 1 FROM " +
                domainClass.getName() + " T WHERE " + campo + " =:value", Boolean.class)
                .setParameter("value", value)
                .getSingleResult();

        return uniqueValue;
    }
}
