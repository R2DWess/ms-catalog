package br.com.wzzy.mscatalogo.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FakeStoreApiConfigTest {

    @Test
    void testWebClientBeanUsesConfiguredBaseUrl() {
        String expectedBaseUrl = "https://fakestoreapi.com";
        FakeStoreApiConfig config = new FakeStoreApiConfig();

        try {
            java.lang.reflect.Field field = FakeStoreApiConfig.class.getDeclaredField("baseUrl");
            field.setAccessible(true);
            field.set(config, expectedBaseUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<WebClient> webClientStatic = Mockito.mockStatic(WebClient.class, Mockito.CALLS_REAL_METHODS)) {
            WebClient.Builder builderMock = mock(WebClient.Builder.class);
            WebClient webClientMock = mock(WebClient.class);

            webClientStatic.when(WebClient::builder).thenReturn(builderMock);
            when(builderMock.baseUrl(expectedBaseUrl)).thenReturn(builderMock);
            when(builderMock.build()).thenReturn(webClientMock);

            WebClient result = config.fakeStoreWebClient();

            verify(builderMock).baseUrl(expectedBaseUrl);
            verify(builderMock).build();
            assertThat(result).isSameAs(webClientMock);
        }
    }

    @Test
    void testFakeStoreApiConfigIsSpringConfiguration() {
        Configuration annotation = FakeStoreApiConfig.class.getAnnotation(Configuration.class);
        assertThat(annotation).isNotNull();
    }

    @Test
    void testWebClientBeanIsPresentInContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        TestPropertyValues.of("fakestore.api.url=https://fakestoreapi.com").applyTo(context);
        context.register(FakeStoreApiConfig.class);
        context.refresh();

        WebClient webClient = context.getBean("fakeStoreWebClient", WebClient.class);
        assertThat(webClient).isNotNull();

        context.close();
    }


    @Test
    void testWebClientBeanWithInvalidBaseUrl() {
        String invalidBaseUrl = "ht!tp://invalid-url";
        FakeStoreApiConfig config = new FakeStoreApiConfig();
        try {
            java.lang.reflect.Field field = FakeStoreApiConfig.class.getDeclaredField("baseUrl");
            field.setAccessible(true);
            field.set(config, invalidBaseUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<WebClient> webClientStatic = Mockito.mockStatic(WebClient.class, Mockito.CALLS_REAL_METHODS)) {
            WebClient.Builder builderMock = mock(WebClient.Builder.class);
            WebClient webClientMock = mock(WebClient.class);

            webClientStatic.when(WebClient::builder).thenReturn(builderMock);
            when(builderMock.baseUrl(invalidBaseUrl)).thenReturn(builderMock);
            when(builderMock.build()).thenReturn(webClientMock);

            WebClient result = config.fakeStoreWebClient();

            verify(builderMock).baseUrl(invalidBaseUrl);
            verify(builderMock).build();
            assertThat(result).isSameAs(webClientMock);
        }
    }

    @Test
    void testWebClientBeanIsSingleton() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        TestPropertyValues.of("fakestore.api.url=https://fakestoreapi.com").applyTo(context);
        context.register(FakeStoreApiConfig.class);
        context.refresh();

        WebClient bean1 = context.getBean("fakeStoreWebClient", WebClient.class);
        WebClient bean2 = context.getBean("fakeStoreWebClient", WebClient.class);

        assertThat(bean1).isSameAs(bean2);

        context.close();
    }
}