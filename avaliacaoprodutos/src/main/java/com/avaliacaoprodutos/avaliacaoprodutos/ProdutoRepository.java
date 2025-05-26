package com.avaliacaoprodutos.avaliacaoprodutos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}