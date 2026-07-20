package estacionamento;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RegistroEstacionamento {

    private Veiculo veiculo;
    private int numeroVaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public RegistroEstacionamento(Veiculo veiculo, int numeroVaga, LocalDateTime entrada) {
        this.veiculo = veiculo;
        this.numeroVaga = numeroVaga;
        this.entrada = entrada;
        this.valorPago = 0;
    }

    public boolean estaAberto() {
        if (saida == null) {
            return true;
        }

        return false;
    }

    public void finalizar(LocalDateTime saida, double valorPago) {
        if (!estaAberto()) {
            throw new IllegalArgumentException("O registro já foi finalizado");
        }

        if (saida == null) {
            throw new IllegalStateException("O horário de saída não pode ser nulo");
        }

        if (saida.isBefore(entrada)) {
            throw new IllegalStateException("O horário de saída não pode ser anterior ao horário de entrada");
        }

        if (valorPago < 0) {
            throw new IllegalStateException("Valor do pagamento negativo");
        }

        this.saida = saida;
        this.valorPago = valorPago;
    }

    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public int getNumeroVaga() {
        return this.numeroVaga;
    }

    public LocalDateTime getEntrada() {
        return this.entrada;
    }

    public LocalDateTime getSaida() {
        return this.saida;
    }

    public double getValorPago() {
        return this.valorPago;
    }

    @Override
    public String toString() {
        if (estaAberto()) {
            return veiculo.getPlaca() + " - Vaga " + numeroVaga + " - Entrada: " + entrada.format(FORMATO_DATA)
                    + " EM ABERTO";
        } else {
            NumberFormat formatador = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

            String valorFormatado = formatador.format(valorPago);

            return veiculo.getPlaca() + " - Vaga " + numeroVaga + " - Entrada: " + entrada.format(FORMATO_DATA)
                    + "\n Saida: "
                    + saida.format(FORMATO_DATA)
                    + " - Valor: " + valorFormatado;
        }
    }
}
