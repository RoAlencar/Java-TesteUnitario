package br.ce.wcaquino.servicos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * No JUnit4, a anotação para definir uma logica nos testes, seria @FixMethodOrder.
 * No caso, pra definir os testes em ordem alfabetica, seria: @FixMethodOrder(MethodSorters.NAME_ASCENDING)
 * <p>
 * Já no JUnit5, a anotação é a @TestMethodOrder.
 * Para definir os testes em orderm alfabetica, seria utilizando o MethodOrder. "forma de ordenação desejada" . class
 */

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OrdemTest {

    /*
    Declarar em static define que o valor será mantido entre os testes
     */
    public static int contador = 0;

    @Test
    public void inicia() {
        contador = 1;
    }

    @Test
    public void verifica() {
        Assertions.assertEquals(1, contador);
    }
}
