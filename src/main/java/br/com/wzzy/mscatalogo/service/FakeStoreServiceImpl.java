package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.model.dto.ProdutoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FakeStoreServiceImpl implements FakeStoreService {

    private final WebClient fakeStoreWebClient;

    public FakeStoreServiceImpl(WebClient fakeStoreWebClient) {
        this.fakeStoreWebClient = fakeStoreWebClient;
    }

    @Override
    public Flux<ProdutoDTO> listarProdutos() {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProdutoDTO.class);
    }

    @Override
    public Mono<ProdutoDTO> buscarProdutoPorId(int id) {
        return fakeStoreWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(ProdutoDTO.class);
    }

    @Override
    public Flux<ProdutoDTO> buscarProdutoPorCategoria(String category) {
        return fakeStoreWebClient.get()
                .uri("/products/category/{category}", category)
                .retrieve()
                .bodyToFlux(ProdutoDTO.class);
    }

    @Override
    public Flux<ProdutoDTO> buscarProdutoPorTitulo(String title) {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProdutoDTO.class)
                .filter(product -> product.getTitle().toLowerCase().contains(title.toLowerCase()));
    }
}
