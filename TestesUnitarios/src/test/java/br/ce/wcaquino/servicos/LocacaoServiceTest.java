package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Calendar;
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

    /**
     * Com a nova exceção para que se o aluguel for feito no sabado, voce devolver com 2 dias de diferença, o metodo quebra.
     * @throws Exception
     */
    @Test
    public void Teste() throws Exception {
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
    public void deveAlugarFilme() throws Exception {
        //cenario
        Usuario usuario = new Usuario("User1");
        List<Filme> filme = Arrays.asList(new Filme("Filme1", 1, 5.0));
        //ação
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() {
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
    public void naoDeveAlguarSemUsuario() throws FilmeSemEstoqueException {
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
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException {
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

    @Test
    public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2, 4.0),
                new Filme("Filme2", 2, 4.0),
                new Filme("Filme3", 2, 4.0));

       Locacao resultado = service.alugarFilme(usuario,filmes);

       //verificacao
        //4+4+4=11
        Assertions.assertEquals(11.0,resultado.getValor());
    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2, 4.0),
                new Filme("Filme2", 2, 4.0),
                new Filme("Filme3", 2, 4.0),
                new Filme("Filme4", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario,filmes);

        //verificacao
        //4+4+3+2=13
        Assertions.assertEquals(13.0,resultado.getValor());
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2, 4.0),
                new Filme("Filme2", 2, 4.0),
                new Filme("Filme3", 2, 4.0),
                new Filme("Filme4", 2, 4.0),
                new Filme("Filme5", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario,filmes);

        //verificacao
        //4+4+3+2+1=14
        Assertions.assertEquals(14.0,resultado.getValor());
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2, 4.0),
                new Filme("Filme2", 2, 4.0),
                new Filme("Filme3", 2, 4.0),
                new Filme("Filme4", 2, 4.0),
                new Filme("Filme5", 2, 4.0),
                new Filme("Filme6", 2, 4.0));

        Locacao resultado = service.alugarFilme(usuario,filmes);

        //verificacao
        //4+4+3+2+1+0=14
        Assertions.assertEquals(14.0,resultado.getValor());
    }

    /**
     * exceção funciona apenas no sabado (utiliza a data atual da maquina)
     * @throws FilmeSemEstoqueException
     * @throws LocadoraException
     */
    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        //Cenario
        Usuario usuario = new Usuario("Usuario");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1",1,5.0));

        //Ação
        Locacao retorno = service.alugarFilme(usuario,filmes);

        //Verificação
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assertions.assertTrue(ehSegunda);
    }
}

