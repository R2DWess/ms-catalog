package br.com.wzzy.mscatalogo.controller;

import br.com.wzzy.mscatalogo.model.dto.ProdutoDTO;
import br.com.wzzy.mscatalogo.service.FakeStoreService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/produtos")
public class FakeStoreController {

    private final FakeStoreService fakeStoreService;

    public FakeStoreController(FakeStoreService fakeStoreService) {
        this.fakeStoreService = fakeStoreService;
    }

    @GetMapping
    public Flux<ProdutoDTO> listarProdutos() {
        return fakeStoreService.listarProdutos();
    }

    @GetMapping("/id/{id}")
    public Mono<ProdutoDTO> buscarProdutoPorId(@PathVariable int id) {
        return fakeStoreService.buscarProdutoPorId(id);
    }
    @GetMapping("/categoria/{category}")
    public Flux<ProdutoDTO> buscarProdutoPorCategoria(@PathVariable String category) {
        return fakeStoreService.buscarProdutoPorCategoria(category);
    }

    @GetMapping("/titulo")
    public Flux<ProdutoDTO> buscarProdutoPorTitulo(@RequestParam String title) {
        return fakeStoreService.buscarProdutoPorTitulo(title);
    }
}
