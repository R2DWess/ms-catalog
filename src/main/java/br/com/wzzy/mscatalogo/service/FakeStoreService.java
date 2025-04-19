package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.model.dto.ProdutoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FakeStoreService {

    Flux<ProdutoEntity> importarProdutos();

    Flux<ProdutoEntity> listarProdutos();

    Mono<ProdutoEntity> buscarProdutoPorId(int id);

    Flux<ProdutoEntity> buscarProdutoPorCategoria(String category);

    Flux<ProdutoEntity> buscarProdutoPorTitulo(String title);
}
