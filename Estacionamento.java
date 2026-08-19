package estacionamento;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

    // teste de branch
    private List<Vaga> vagas;
    private List<RegistroEstacionamento> registros;
    private double precoPorHora;

    public Estacionamento(double precoPorHora) {
        this.vagas = new ArrayList<>();
        this.registros = new ArrayList<>();
        if (precoPorHora <= 0) {
            throw new IllegalArgumentException("O preço cobrado por hora não pode ser menor ou igual a zero");
        }

        this.precoPorHora = precoPorHora;
    }

    public void adicionarVaga(Vaga vaga) {
        if (vaga == null) {
            throw new IllegalArgumentException("A vaga não pode ser nula");
        }

        if (vaga.getNumero() < 0) {
            throw new IllegalArgumentException("O número da vaga deve ser maior que zero");
        }
        if (vagas.contains(vaga)) {
            throw new IllegalArgumentException("A vaga já existe no registro");
        }

        if (buscarVagaPorNumero(vaga.getNumero()) != null) {
            throw new IllegalStateException("Já existe uma vaga com esse número");
        }

        vagas.add(vaga);
    }

    public Vaga buscarVagaPorNumero(int numero) {
        for (Vaga vaga : vagas) {
            if (vaga.getNumero() == numero) {
                return vaga;
            }
        }
        return null;
    }

    public RegistroEstacionamento buscarRegistroAberto(String placa) {
        for (RegistroEstacionamento registro : registros) {
            if (registro.getVeiculo().getPlaca().equals(placa.toUpperCase()) && registro.estaAberto()) {
                return registro;
            }
        }
        return null;
    }

    public Vaga buscarVagaLivre(TipoVeiculo tipo) {
        for (Vaga vaga : vagas) {
            if (vaga.estaLivre() && vaga.getTipo() == tipo) {
                return vaga;
            }
        }
        return null;
    }

    public RegistroEstacionamento estacionar(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException();
        }

        for (RegistroEstacionamento registro : registros) {
            if (registro.estaAberto() && registro.getVeiculo().getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                // adicionar motivo
                throw new IllegalArgumentException("Já existe um veículo estacionado com essa placa");
            }
        }

        for (Vaga vaga : vagas) {
            if (vaga.estaLivre() && vaga.aceita(veiculo)) {
                vaga.ocupar(veiculo);

                RegistroEstacionamento registro = new RegistroEstacionamento(veiculo, vaga.getNumero(),
                        LocalDateTime.now());
                registros.add(registro);

                return registro;
            }
        }

        throw new IllegalStateException("Não há vagas disponíveis para esse tipo de veículo");
    }

    public double retirarVeiculo(String placa) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa inválida");
        }

        for (RegistroEstacionamento registro : registros) {
            if (registro.estaAberto() && registro.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {

                LocalDateTime horarioSaida = LocalDateTime.now();

                double valor = calcularValor(registro.getEntrada(), horarioSaida);

                for (Vaga vaga : vagas) {
                    if (vaga.getNumero() == registro.getNumeroVaga()) {
                        if (vaga.estaLivre()) {
                            throw new IllegalStateException("A vaga já está livre.");
                        }

                        if (!vaga.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                            throw new IllegalArgumentException(
                                    "A placa informada não coincide com a placa do registro");
                        }

                        vaga.liberar();
                        registro.finalizar(horarioSaida, valor);
                        return valor;
                    }
                }

                throw new IllegalStateException("Registro encontrado, mas a vaga correspondente não existe");
            }
        }
        throw new IllegalArgumentException("Veículo não encontrado");
    }

    public double calcularValor(LocalDateTime entrada, LocalDateTime saida) {
        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("A entrada e saída não podem ser nulas");
        }

        if (saida.isBefore(entrada)) {
            throw new IllegalStateException("A saída não pode ser anterior à entrada");
        }

        Duration duracao = Duration.between(entrada, saida);

        long minutos = duracao.toMinutes();
        long horasCobradas = minutos / 60;
        long minutosRestantes = minutos % 60;

        if (minutosRestantes > 0) {
            horasCobradas++;
        }

        if (horasCobradas == 0) {
            horasCobradas++;
        }

        return horasCobradas * precoPorHora;
    }

    public List<Vaga> listarVagasLivres() {
        List<Vaga> vagasLivres = new ArrayList<>();

        for (Vaga vaga : vagas) {
            if (vaga.estaLivre()) {
                vagasLivres.add(vaga);
            }
        }

        if (!vagasLivres.isEmpty()) {
            return vagasLivres;
        }

        System.out.println("Não há vagas livres");
        return null;
    }

    public List<Vaga> listarVagasOcupadas() {
        List<Vaga> vagasOcupadas = new ArrayList<>();

        for (Vaga vaga : vagas) {
            if (!vaga.estaLivre()) {
                vagasOcupadas.add(vaga);
            }
        }

        if (!vagasOcupadas.isEmpty()) {
            return vagasOcupadas;
        }

        System.out.println("Não há vagas ocupadas");
        return null;
    }

    public List<RegistroEstacionamento> listarHistorico() {

        List<RegistroEstacionamento> historico = new ArrayList<>();

        for (RegistroEstacionamento registro : registros) {
            historico.add(registro);
        }

        return historico;
    }

    public int quantidadeVagasLivres() {
        List<Vaga> vagasLivres = new ArrayList<>();

        for (Vaga vaga : vagas) {
            if (vaga.estaLivre()) {
                vagasLivres.add(vaga);
            }
        }

        return vagasLivres.size();
    }

    public int quantidadeVagasOcupadas() {
        List<Vaga> vagasOcupadas = new ArrayList<>();

        for (Vaga vaga : vagas) {
            if (!vaga.estaLivre()) {
                vagasOcupadas.add(vaga);
            }
        }

        return vagasOcupadas.size();
    }

    public double calcularFaturamento() {
        double faturamento = 0;

        for (RegistroEstacionamento registro : registros) {
            faturamento += registro.getValorPago();
        }

        return faturamento;
    }
}
