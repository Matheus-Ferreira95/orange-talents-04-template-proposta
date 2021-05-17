package br.com.zupacademy.matheus.propostas.carteira;

import br.com.zupacademy.matheus.propostas.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByCartaoAndCarteira(Cartao cartao, TipoCarteira carteira);
}
