package estacionamento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EstacionamentoRepository {

    private static final String ARQUIVO_VAGAS = "vagas.txt";
    private static final String ARQUIVO_REGISTROS = "registros.txt";

    public void salvarVagas(List<Vaga> vagas) {

        try (BufferedReader writer = new BufferedWriter(new FileWriter(ARQUIVO_VAGAS))) {

            for (Vaga vaga : vagas) {

                String linha = vaga.getNumero() + ";" + vaga.getTipo();
                writer.write(linha);
                writer.newLine();

            }
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível salvar a lista.", e);
        }
    }

    public List<Vaga> carregarVagas() {
        List<Vaga> vagas = new ArrayList<>();

        try (Stream<String> linhas = Files.lines(Path.of(REGISTRO_VAGAS))) {

            return linhas
                    .map(linha -> {
                        String[] dados = linha.split(";");

                        int numero = Integer.valueOf(dados[0]);
                        String tipo = dados[1];

                        return new Vaga(numero, tipo);
                    })
                    .toList();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return vagas;
    }

    public void salvarRegistros() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_REGISTROS))) {

        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível salvar a lista.", e);
        }
    }

    public void carregarRegistros() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_REGISTROS))) {

        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível carregar a lista.", e);
        }
    }
}

/*
 * public List<Vaga> carregarVagas() {
 * List<Vaga> vagas = new ArrayList<>();
 * 
 * try (BufferedReader reader = new BufferedReader(new
 * FileReader(ARQUIVO_VAGAS))) {
 * 
 * String linha;
 * 
 * while ((linha = reader.readLine()) != null) {
 * 
 * String[] dados = linha.split(";");
 * int numero = Integer.valueOf(dados[0]);
 * String tipo = dados[1];
 * 
 * Vaga vaga = new Vaga(numero, tipo);
 * vagas.add(vaga);
 * 
 * }
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * 
 * return vagas;
 * }
 */
