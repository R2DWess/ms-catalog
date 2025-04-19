package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.mapper.ProdutoMapper;
import br.com.wzzy.mscatalogo.model.dto.CatalogoProdutoDTO;
import br.com.wzzy.mscatalogo.model.dto.ProdutoEntity;
import br.com.wzzy.mscatalogo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FakeStoreServiceImpl implements FakeStoreService {

    private final WebClient fakeStoreWebClient;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Autowired
    public FakeStoreServiceImpl(WebClient fakeStoreWebClient, ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.fakeStoreWebClient = fakeStoreWebClient;
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Flux<ProdutoEntity> importarProdutos() {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(CatalogoProdutoDTO.class)
                .map(produtoMapper::converterCatalogoProdutoDTOParaEntidade)
                .flatMap(produtoRepository::save);
    }

    @Override
    public Flux<ProdutoEntity> listarProdutos() {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProdutoEntity.class);
    }

    @Override
    public Mono<ProdutoEntity> buscarProdutoPorId(int id) {
        return fakeStoreWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(ProdutoEntity.class);
    }

    @Override
    public Flux<ProdutoEntity> buscarProdutoPorCategoria(String category) {
        return fakeStoreWebClient.get()
                .uri("/products/category/{category}", category)
                .retrieve()
                .bodyToFlux(ProdutoEntity.class);
    }

    @Override
    public Flux<ProdutoEntity> buscarProdutoPorTitulo(String title) {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProdutoEntity.class)
                .filter(product -> product.getTitle().toLowerCase().contains(title.toLowerCase()));
    }
}