package com.avaliacaoprodutos.avaliacaoprodutos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images";
    @Autowired
    private ProdutoRepository produtoRepo;

    @GetMapping("/upload/{id}")
    public String showUploadForm(@PathVariable Long id, Model model) {
        Produto produto = produtoRepo.findById(id).orElse(null);
        if (produto != null) {
            model.addAttribute("produto", produto);
            return "uploadfoto"; // Retorna a view para o formulário de upload
        }
        return "redirect:/index";
    }

    @PostMapping("/upload/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
                Files.write(filePath, image.getBytes());
                Produto produto = produtoRepo.findById(id).orElse(null);
                produto.setImagem("/images/" + fileName);
                produtoRepo.save(produto);                
                return "redirect:/";
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/";
            }
            
        }
        return "redirect:/upload/" + id;
    }
}