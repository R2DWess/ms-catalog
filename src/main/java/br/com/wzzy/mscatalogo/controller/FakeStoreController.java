package br.com.wzzy.mscatalogo.controller;

import br.com.wzzy.mscatalogo.model.dto.ProdutoDTO;
import br.com.wzzy.mscatalogo.service.FakeStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/listar-id/{id}")
    public Mono<ResponseEntity<ProdutoDTO>> buscarProdutoPorId(@PathVariable int id) {
        return fakeStoreService.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar-categoria/{category}")
    public Mono<ResponseEntity<ProdutoDTO>> buscarProdutoPorCategoria(@PathVariable String category) {
        return fakeStoreService.buscarProdutoPorCategoria(category)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
