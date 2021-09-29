package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    //definição do contador
    private static Integer contador = 0;
    @BeforeEach
    public void setup() {
        System.out.println("Before");
        service = new LocacaoService();
        //incremento
         contador++;
        //impressão do contador
        System.out.println(contador);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("After");
    }

    /**
     * AfterEach e BeforeEach (JUnit5) = After e Before(JUnit4)
     * AfterAll e BeforeAll (JUnit5) = AfterClass e BeforeClass(JUnit4) -> Antes da classe ser intanciada e depois dela ser destruida, logo é necessario colocar como static
     */
    @BeforeAll
    public static void setupClass() {
        System.out.println("BeforeAll");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("AfterAll");
    }

    @Test
    public void teste() throws Exception {
        //cenario
        Usuario usuario = new Usuario("User1");
        Filme filme = new Filme("Filme1", 2, 5.0);

        System.out.println("Teste");
        //ação
        Locacao locacao;

        locacao = service.alugarFilme(usuario, filme);

        //verificação

        /**
         * Usar o assertAll para que o teste não pare no primeiro erro.
         * assim, ele mostra todos os erros das 3 classes
         */
        Assertions.assertAll("teste",
                () -> assertEquals(5.0, locacao.getValor(), 0.01),
                () -> assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date())),
                () -> assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)))
        );

    }

    @Test
    public void testLocacao_filmeSemEstoque() throws Exception {
        //cenario
        Usuario usuario = new Usuario("User1");
        Filme filme = new Filme("Filme1", 2, 5.0);

        //ação
        service.alugarFilme(usuario, filme);

    }

    @Test
    public void testLocacao_filmeSemEstoque2() {
        //cenario
        Usuario usuario = new Usuario("User1");
        Filme filme = new Filme("Filme1", 0, 5.0);

        //ação
        try {
            service.alugarFilme(usuario, filme);
            Assertions.fail("Deveria lançar uma exceção");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Filme sem estoque");
        }
    }

    @Test
    public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
        //cenario
        Filme filme = new Filme("Filme3", 1, 4.0);

        //Ação
        try {
            service.alugarFilme(null, filme);
            Assertions.fail(); //Pode colocar mensagem de erro!!!!
        } catch (LocadoraException e) {
            Assertions.assertEquals(e.getMessage(), "Usuario vazio");
        }

    }

    @Test
    public void testLocacao_Filmevazio() throws FilmeSemEstoqueException {
        //Cenario
        Usuario usuario = new Usuario("Usuario 1");
        //Ação
        try {
            service.alugarFilme(usuario, null);
            Assertions.fail();
        } catch (LocadoraException e) {
            Assertions.assertEquals(e.getMessage(), "Filme vazio");
        }
    }


}

