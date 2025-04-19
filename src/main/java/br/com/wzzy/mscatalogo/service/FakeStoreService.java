package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.model.dto.ProdutoResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FakeStoreService {

    Flux<ProdutoResponseDTO> importarProdutos();

    Flux<ProdutoResponseDTO> listarProdutos();

    Mono<ProdutoResponseDTO> buscarProdutoPorId(int id);

    Flux<ProdutoResponseDTO> buscarProdutoPorCategoria(String category);

    Flux<ProdutoResponseDTO> buscarProdutoPorTitulo(String title);
}
