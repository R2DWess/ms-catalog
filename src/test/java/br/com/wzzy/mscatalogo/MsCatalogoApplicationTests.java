package br.com.wzzy.mscatalogo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsCatalogoApplicationTests {

    @Test
    void applicationFailsToStartWithInvalidArgs() {
        String[] invalidArgs = {"--invalid.property=value"};
        assertThrows(Exception.class, () -> MsCatalogoApplication.main(invalidArgs));
    }
}
