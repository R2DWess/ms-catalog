package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.mapper.ProdutoMapper;
import br.com.wzzy.mscatalogo.model.dto.CatalogoProdutoDTO;
import br.com.wzzy.mscatalogo.model.dto.ProdutoResponseDTO;
import br.com.wzzy.mscatalogo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FakeStoreServiceImpl implements FakeStoreService {

    private final WebClient fakeStoreWebClient;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public FakeStoreServiceImpl(WebClient fakeStoreWebClient, ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.fakeStoreWebClient = fakeStoreWebClient;
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Flux<ProdutoResponseDTO> listarProdutos() {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(CatalogoProdutoDTO.class)
                .map(produtoMapper::converterCatalogoProdutoDTOParaEntidade)
                .map(produtoMapper::converterEntidadeParaProdutoResponseDTO);
    }

    @Override
    public Mono<ProdutoResponseDTO> buscarProdutoPorId(int id) {
        return fakeStoreWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(CatalogoProdutoDTO.class)
                .map(produtoMapper::converterCatalogoProdutoDTOParaEntidade)
                .map(produtoMapper::converterEntidadeParaProdutoResponseDTO);
    }

    @Override
    public Flux<ProdutoResponseDTO> buscarProdutoPorCategoria(String category) {
        return fakeStoreWebClient.get()
                .uri("/products/category/{category}", category)
                .retrieve()
                .bodyToFlux(CatalogoProdutoDTO.class)
                .map(produtoMapper::converterCatalogoProdutoDTOParaEntidade)
                .map(produtoMapper::converterEntidadeParaProdutoResponseDTO);
    }

    @Override
    public Flux<ProdutoResponseDTO> buscarProdutoPorTitulo(String title) {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(CatalogoProdutoDTO.class)
                .map(produtoMapper::converterCatalogoProdutoDTOParaEntidade)
                .filter(produto -> produto.getTitle().toLowerCase().contains(title.toLowerCase()))
                .map(produtoMapper::converterEntidadeParaProdutoResponseDTO);
    }
}
