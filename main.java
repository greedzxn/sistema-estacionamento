package estacionamento;

import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Repositorio repositorio = new Repositorio();
        List<Vaga> vagas = repositorio.carregarVagas();
        List<RegistroEstacionamento> registros = repositorio.carregarRegistros();

        Estacionamento estacionamento = new Estacionamento(10);

        for (Vaga vaga : vagas) {
            estacionamento.adicionarVaga(vaga);
        }

        for (RegistroEstacionamento registro : registros) {
            estacionamento.carregarRegistro(registro);
        }

        Menu menu = new Menu(scanner, estacionamento, repositorio);

        menu.iniciar();
    }

}
