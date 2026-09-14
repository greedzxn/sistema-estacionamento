package estacionamento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EstacionamentoRepository {

    private static final String ARQUIVO_VAGAS = "vagas.txt";
    private static final String ARQUIVO_REGISTROS = "registros.txt";

    public void salvarVagas(List<Vaga> vagas) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_VAGAS))) {

            for (Vaga vaga : vagas) {

                String linha = vaga.getNumero() + ";" + vaga.getTipo();
                writer.write(linha);
                writer.newLine();

            }
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível salvar as vagas.", e);
        }
    }

    public List<Vaga> carregarVagas() {

        try (Stream<String> linhas = Files.lines(Path.of(ARQUIVO_VAGAS))) {

            return linhas
                    .map(linha -> {
                        String[] dados = linha.split(";");

                        int numero = Integer.parseInt(dados[0]);
                        TipoVeiculo tipo = TipoVeiculo.parse(dados[1]);

                        return new Vaga(numero, tipo);
                    })
                    .toList();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public void salvarRegistros(List<RegistroEstacionamento> registros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_REGISTROS))) {

            for (RegistroEstacionamento registro : registros) {
                String linha = registro.getVeiculo().getPlaca() + ";" + registro.getVeiculo().getModelo() + ";"
                        + registro.getVeiculo().getTipo() + ";" + registro.getNumeroVaga() + ";"
                        + registro.getEntrada();

                if (registro.estaAberto()) {
                    linha += ";;0";
                } else {
                    linha += registro.getSaida() + ";" + registro.getValorPago();
                }

                writer.write(linha);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível salvar a lista.", e);
        }
    }

    public List<RegistroEstacionamento> carregarRegistros() {

        try (Stream<String> linhas = Files.lines(Path.of(ARQUIVO_REGISTROS))) {
            return linhas
                    .map(linha -> {
                        String[] dados = linha.split(";");

                        String placa = dados[0];
                        String modelo = dados[1];
                        TipoVeiculo tipo = TipoVeiculo.valueOf(dados[2]);
                        int numeroVaga = Integer.parseInt(dados[3]);

                        Veiculo veiculo = new Veiculo(placa, modelo, tipo);

                        LocalDateTime entrada = LocalDateTime.parse(dados[4]);

                        String saida = dados[5];
                        if (saida.isEmpty()) {
                            return new RegistroEstacionamenFto(veiculo, numeroVaga, entrada);
                        } else {
                            LocalDateTime saidaDateTime = LocalDateTime.parse(dados[5]);
                            double valorPago = Double.parseDouble(dados[6]);
                            return new RegistroEstacionamento(veiculo, numeroVaga, entrada, saidaDateTime, valorPago);
                        }

                    })
                    .toList();

        } catch (IOException e) {
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
