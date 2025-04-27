package br.com.wzzy.mscatalogo.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelMapperConfigTest {

    @Component
    static class TestComponent {
        @Autowired
        ModelMapper modelMapper;
    }

    @Test
    void testModelMapperBeanIsPresentInContext() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(ModelMapperConfig.class);

        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(ModelMapper.class);
            assertThat(context.getBean(ModelMapper.class)).isNotNull();
        });
    }

    @Test
    void testModelMapperBeanIsSingleton() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(ModelMapperConfig.class);

        contextRunner.run(context -> {
            ModelMapper bean1 = context.getBean(ModelMapper.class);
            ModelMapper bean2 = context.getBean(ModelMapper.class);
            assertThat(bean1).isSameAs(bean2);
        });
    }

    @SpringJUnitConfig(classes = {ModelMapperConfig.class, TestComponent.class})
    static class AutowireTestContext {
        @Autowired
        TestComponent testComponent;
    }

    @Test
    void testModelMapperAutowiredInComponent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModelMapperConfig.class, TestComponent.class);
        TestComponent component = context.getBean(TestComponent.class);
        assertThat(component.modelMapper).isNotNull();
        context.close();
    }

}