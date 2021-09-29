package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    /**
     * TDD - Iniciar o desenvolvimento da aplicação apartir dos testes.
     */
    private Calculadora calc;

    @BeforeEach
    private void setup(){
        calc = new Calculadora();
    }
    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;

        //ação
        int resultado = calc.somar(a,b);
        //verificação
        Assertions.assertEquals(8,resultado);
    }

    @Test
    public void deveSubtratirDoisValores(){
        int a = 8;
        int b = 5;


        int resultado = calc.subtratir(a,b);

        Assertions.assertEquals(3,resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        int a = 6;
        int b = 3;


        int resultado = calc.divide(a,b);

        Assertions.assertEquals(2,resultado);
    }

    @Test
    public void DeveLancarExcecaoAoDividirPorZero(){
        int a = 10;
        int b = 0;

        try {
            calc.divide(a, b);
        }catch (NaoPodeDividirPorZeroException e){
            Assertions.assertEquals(e.getMessage(),"Não pode dividir por zero!");
        }
    }

}
