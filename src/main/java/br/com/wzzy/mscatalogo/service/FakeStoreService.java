package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.model.dto.ProdutoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FakeStoreService {

    Flux<ProdutoDTO> listarProdutos();

    Mono<ProdutoDTO> buscarProdutoPorId(int id);

    Flux<ProdutoDTO> buscarProdutoPorCategoria(String category);

    Flux<ProdutoDTO> buscarProdutoPorTitulo(String title);
}
