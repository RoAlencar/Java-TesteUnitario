package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertTest {

    @Test
    public void test(){
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);

        /**
         * Verifica se os valores são iguais.
         * Ideal pra trabalhar com int,short, long e até booleano
         *
         * Ele se comporta diferente com
         * double e float, por conta das casas decimais,
         * sendo necessario utilizar o delta, para margem de erro
         */

        Assertions.assertEquals( 1,1);
        Assertions.assertEquals(0.51234,0.513,0.001);
        Assertions.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;
        /**
         * Dependendo da versão do JUnit, seria necessario converter
         * os valores das variaveis.
         *
         * ex1.
         * Assertions.assertEquals(Integer.valueOf(i), i2)
         *
         * ex.2
         * Assertions.assertEquals(i, i2.intValue())
         */
        Assertions.assertEquals(i,i2);

        Assertions.assertEquals("bola", "bola");
        Assertions.assertNotEquals("bola","casa");
        Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assertions.assertTrue("bola".startsWith("bo"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        Assertions.assertEquals(u1,u2);

        /**Nesse caso, so consegue comparar objetos da MESMA instancia
         *   Assertions.assertSame(u1,u2);
         */

        Assertions.assertSame(u2,u2);
        Assertions.assertNotSame(u1,u2);

        Assertions.assertTrue(u3 == null);
        Assertions.assertNull(u3);
        Assertions.assertNotNull(u2);
    }
}
