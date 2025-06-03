package com.avaliacaoprodutos.avaliacaoprodutos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private AvaliacaoRepository avaliacaoRepo;

    @GetMapping("/")
    public String home(Model model) {
    List<Produto> produtos = produtoRepo.findAll();
    model.addAttribute("produtos", produtos);
    return "index";
}

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoRepo.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @GetMapping("/produto/{id}")
    public String detalhesProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepo.findById(id).orElse(null);
        if (produto != null) {
            model.addAttribute("produto", produto);
            model.addAttribute("avaliacoes", avaliacaoRepo.findByProduto(produto));
        }
        return "detalhesProduto";
    }

    @PostMapping("/produto/{id}/avaliar")
    public String avaliarProduto(@PathVariable Long id, @RequestParam String comentario, @RequestParam int estrelas) {
        Produto produto = produtoRepo.findById(id).orElse(null);
        if (produto != null) {
            Avaliacao avaliacao = new Avaliacao(comentario, estrelas, produto);
            avaliacaoRepo.save(avaliacao);
        }
        return "redirect:/produto/" + id;
    }
}