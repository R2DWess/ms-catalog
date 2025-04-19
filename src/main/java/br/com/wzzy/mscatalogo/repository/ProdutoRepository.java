package br.com.wzzy.mscatalogo.repository;

import br.com.wzzy.mscatalogo.model.dto.ProdutoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdutoRepository extends ReactiveCrudRepository<ProdutoEntity, Long> {
}