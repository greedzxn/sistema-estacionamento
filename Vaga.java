package estacionamento;

public class Vaga {

    private int numero;
    private TipoVeiculo tipo;
    private Veiculo veiculo;

    public Vaga(int numero, TipoVeiculo tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.veiculo = null;
    }

    public boolean estaLivre() {
        return veiculo == null;
    }

    public boolean aceita(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalStateException("O veículo não pode ser nulo");
        }

        return veiculo.getTipo() == tipo;
    }

    public void ocupar(Veiculo veiculo) {
        if (!estaLivre()) {
            throw new IllegalStateException("A vaga já está ocupada");
        }

        if (!aceita(veiculo)) {
            throw new IllegalStateException("O veículo não é compatível com o tipo da vaga");
        }

        this.veiculo = veiculo;
    }

    public void liberar() {
        if (veiculo == null) {
            throw new IllegalStateException("A vaga já está livre");
        }

        this.veiculo = null;
    }

    public int getNumero() {
        return this.numero;
    }

    public TipoVeiculo getTipo() {
        return this.tipo;
    }

    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    @Override
    public String toString() {
        if (this.veiculo != null) {
            return "Vaga " + numero + " - " + tipo + " - Ocupada por " + this.veiculo.getPlaca();
        } else {
            return "Vaga " + numero + " - " + tipo + "- LIVRE";
        }
    }
}