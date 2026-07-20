package estacionamento;

public class Veiculo {

    private String placa;
    private String modelo;
    private TipoVeiculo tipo;

    public Veiculo(String placa, String modelo, TipoVeiculo tipo) {
        if (placa == null || modelo == null || tipo == null) {
            throw new IllegalArgumentException("Nenhum dos parâmetros pode ser nulo");
        }
        this.placa = placa.trim().toUpperCase();
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return placa + " - " + modelo + " - " + tipo;
    }
}