package br.com.wzzy.mscatalogo.service;

import br.com.wzzy.mscatalogo.mapper.ProdutoMapper;
import br.com.wzzy.mscatalogo.model.dto.CatalogoProdutoDTO;
import br.com.wzzy.mscatalogo.model.dto.ProdutoEntity;
import br.com.wzzy.mscatalogo.model.dto.ProdutoResponseDTO;
import br.com.wzzy.mscatalogo.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class FakeStoreServiceImplTest {

    @Mock
    private WebClient fakeStoreWebClient;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private FakeStoreServiceImpl fakeStoreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fakeStoreService = new FakeStoreServiceImpl(fakeStoreWebClient, produtoRepository, produtoMapper);
    }

    @Test
    public void testListarProdutosReturnsMappedProductList() {
        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class)).thenReturn(Flux.just(new CatalogoProdutoDTO(), new CatalogoProdutoDTO()));

        var entity1 = new ProdutoEntity();
        var entity2 = new ProdutoEntity();
        var response1 = new ProdutoResponseDTO();
        var response2 = new ProdutoResponseDTO();

        when(produtoMapper.converterCatalogoProdutoDTOParaEntidade(any(CatalogoProdutoDTO.class)))
                .thenReturn(entity1, entity2);
        when(produtoMapper.converterEntidadeParaProdutoResponseDTO(any(ProdutoEntity.class)))
                .thenReturn(response1, response2);

        StepVerifier.create(fakeStoreService.listarProdutos())
                .expectNext(response1)
                .expectNext(response2)
                .verifyComplete();
    }

    @Test
    public void testBuscarProdutoPorIdReturnsMappedProduct() {
        int id = 15;
        CatalogoProdutoDTO dto = new CatalogoProdutoDTO();
        ProdutoEntity entity = new ProdutoEntity();
        ProdutoResponseDTO response = new ProdutoResponseDTO();

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products/{id}"), eq(id))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CatalogoProdutoDTO.class)).thenReturn(Mono.just(dto));
        when(produtoMapper.converterCatalogoProdutoDTOParaEntidade(dto)).thenReturn(entity);
        when(produtoMapper.converterEntidadeParaProdutoResponseDTO(entity)).thenReturn(response);

        StepVerifier.create(fakeStoreService.buscarProdutoPorId(id))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testBuscarProdutoPorCategoriaReturnsMappedProducts() {
        String category = "electronics";
        CatalogoProdutoDTO dto = new CatalogoProdutoDTO();
        ProdutoEntity entity = new ProdutoEntity();
        ProdutoResponseDTO response = new ProdutoResponseDTO();

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products/category/{category}"), eq(category))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class)).thenReturn(Flux.just(dto));
        when(produtoMapper.converterCatalogoProdutoDTOParaEntidade(dto)).thenReturn(entity);
        when(produtoMapper.converterEntidadeParaProdutoResponseDTO(entity)).thenReturn(response);

        StepVerifier.create(fakeStoreService.buscarProdutoPorCategoria(category))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testListarProdutosReturnsEmptyWhenNoProducts() {
        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class)).thenReturn(Flux.empty());

        StepVerifier.create(fakeStoreService.listarProdutos())
                .verifyComplete();
    }

    @Test
    public void testBuscarProdutoPorIdReturnsEmptyWhenNotFound() {
        int id = 999;
        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products/{id}"), eq(id))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CatalogoProdutoDTO.class)).thenReturn(Mono.empty());

        StepVerifier.create(fakeStoreService.buscarProdutoPorId(id))
                .verifyComplete();
    }

    @Test
    public void testServiceHandlesExternalApiErrorsGracefully() {
        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class)).thenReturn(Flux.error(new RuntimeException("API error")));

        StepVerifier.create(fakeStoreService.listarProdutos())
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("API error"))
                .verify();
    }

    @Test
    public void testBuscarProdutoPorTituloRetornaProdutosFiltrados() {
        String tituloBuscado = "iphone";

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec    = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec          = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class))
                .thenReturn(Flux.just(new CatalogoProdutoDTO(), new CatalogoProdutoDTO()));

        ProdutoEntity entityComTitulo   = new ProdutoEntity();
        entityComTitulo.setTitle("Apple iPhone 15 Pro");

        ProdutoEntity entitySemTitulo   = new ProdutoEntity();
        entitySemTitulo.setTitle("Samsung Galaxy S24");

        when(produtoMapper.converterCatalogoProdutoDTOParaEntidade(any(CatalogoProdutoDTO.class)))
                .thenReturn(entityComTitulo, entitySemTitulo);

        ProdutoResponseDTO responseFiltrado = new ProdutoResponseDTO();
        when(produtoMapper.converterEntidadeParaProdutoResponseDTO(entityComTitulo))
                .thenReturn(responseFiltrado);

        StepVerifier.create(fakeStoreService.buscarProdutoPorTitulo(tituloBuscado))
                .expectNext(responseFiltrado)
                .verifyComplete();

        verify(produtoMapper, never()).converterEntidadeParaProdutoResponseDTO(entitySemTitulo);
    }

    @Test
    public void testBuscarProdutoPorTituloRetornaVazioQuandoNaoHaCorrespondencia() {
        String tituloBuscado = "nintendo";

        var requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var requestHeadersSpec    = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec          = mock(WebClient.ResponseSpec.class);

        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CatalogoProdutoDTO.class))
                .thenReturn(Flux.just(new CatalogoProdutoDTO()));

        ProdutoEntity entity = new ProdutoEntity();
        entity.setTitle("PlayStation 5");
        when(produtoMapper.converterCatalogoProdutoDTOParaEntidade(any(CatalogoProdutoDTO.class)))
                .thenReturn(entity);

        StepVerifier.create(fakeStoreService.buscarProdutoPorTitulo(tituloBuscado))
                .verifyComplete();

        verify(produtoMapper, never()).converterEntidadeParaProdutoResponseDTO(any());
    }

}