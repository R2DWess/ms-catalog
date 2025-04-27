package br.com.wzzy.mscatalogo.controller;

import br.com.wzzy.mscatalogo.model.dto.ProdutoResponseDTO;
import br.com.wzzy.mscatalogo.service.FakeStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class FakeStoreControllerTest {

    @Mock
    private FakeStoreService fakeStoreService;

    @InjectMocks
    private FakeStoreController fakeStoreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProdutosReturnsAllProducts() {
        ProdutoResponseDTO produto1 = new ProdutoResponseDTO();
        ProdutoResponseDTO produto2 = new ProdutoResponseDTO();
        List<ProdutoResponseDTO> produtos = Arrays.asList(produto1, produto2);

        when(fakeStoreService.listarProdutos()).thenReturn(Flux.fromIterable(produtos));

        StepVerifier.create(fakeStoreController.listarProdutos())
                .expectNext(produto1)
                .expectNext(produto2)
                .verifyComplete();

        verify(fakeStoreService, times(1)).listarProdutos();
    }

    @Test
    void testBuscarProdutoPorIdReturnsCorrectProduct() {
        int id = 1;
        ProdutoResponseDTO produto = new ProdutoResponseDTO();
        when(fakeStoreService.buscarProdutoPorId(id)).thenReturn(Mono.just(produto));

        StepVerifier.create(fakeStoreController.buscarProdutoPorId(id))
                .expectNext(produto)
                .verifyComplete();

        verify(fakeStoreService, times(1)).buscarProdutoPorId(id);
    }

    @Test
    void testBuscarProdutoPorCategoriaReturnsProductsInCategory() {
        String category = "electronics";
        ProdutoResponseDTO produto1 = new ProdutoResponseDTO();
        ProdutoResponseDTO produto2 = new ProdutoResponseDTO();
        List<ProdutoResponseDTO> produtos = Arrays.asList(produto1, produto2);

        when(fakeStoreService.buscarProdutoPorCategoria(category)).thenReturn(Flux.fromIterable(produtos));

        StepVerifier.create(fakeStoreController.buscarProdutoPorCategoria(category))
                .expectNext(produto1)
                .expectNext(produto2)
                .verifyComplete();

        verify(fakeStoreService, times(1)).buscarProdutoPorCategoria(category);
    }

    @Test
    void testListarProdutosReturnsEmptyListWhenNoProducts() {
        when(fakeStoreService.listarProdutos()).thenReturn(Flux.empty());

        StepVerifier.create(fakeStoreController.listarProdutos())
                .expectNextCount(0)
                .verifyComplete();

        verify(fakeStoreService, times(1)).listarProdutos();
    }

    @Test
    void testBuscarProdutoPorIdWithNonExistentId() {
        int nonExistentId = 999;
        when(fakeStoreService.buscarProdutoPorId(nonExistentId)).thenReturn(Mono.empty());

        StepVerifier.create(fakeStoreController.buscarProdutoPorId(nonExistentId))
                .expectNextCount(0)
                .verifyComplete();

        verify(fakeStoreService, times(1)).buscarProdutoPorId(nonExistentId);
    }

    @Test
    void testBuscarProdutoPorTituloWithInvalidOrMissingTitle() {

        String invalidTitle = "";
        when(fakeStoreService.buscarProdutoPorTitulo(invalidTitle)).thenReturn(Flux.empty());

        StepVerifier.create(fakeStoreController.buscarProdutoPorTitulo(invalidTitle))
                .expectNextCount(0)
                .verifyComplete();

        verify(fakeStoreService, times(1)).buscarProdutoPorTitulo(invalidTitle);
    }
}