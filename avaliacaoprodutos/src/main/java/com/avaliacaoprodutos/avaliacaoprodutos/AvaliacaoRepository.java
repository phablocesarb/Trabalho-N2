package com.avaliacaoprodutos.avaliacaoprodutos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByProduto(Produto produto);
}
