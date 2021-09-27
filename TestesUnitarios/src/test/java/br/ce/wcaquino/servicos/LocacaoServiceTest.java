package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocacaoServiceTest {

    @Test
    public void teste() {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("User1");
        Filme filme = new Filme("Filme1",2,5.0);

        //ação
        Locacao locacao = service.alugarFilme(usuario,filme);
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
}
