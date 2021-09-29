package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class LocacaoServiceTest {

    private LocacaoService service;

    @BeforeEach
    public void setup() {
        service = new LocacaoService();
    }

    @Test
    public void teste() throws Exception {
        //cenario
        Usuario usuario = new Usuario("User1");
        List<Filme> filme = Arrays.asList(new Filme("Filme1", 2, 5.0));
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
        List<Filme> filme = Arrays.asList(new Filme("Filme1", 2, 5.0));
        //ação
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testLocacao_filmeSemEstoque2() {
        //cenario
        Usuario usuario = new Usuario("User1");
        List<Filme> filme = Arrays.asList(new Filme("Filme1", 0, 5.0));

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
        List<Filme> filme = Arrays.asList(new Filme("Filme1", 2, 5.0));

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

