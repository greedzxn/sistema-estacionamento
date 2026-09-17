package estacionamento;

import java.util.Scanner;
import java.util.List;

public class Menu {

    private Scanner scanner;
    private Estacionamento estacionamento;
    private Repositorio repositorio;

    public Menu(Scanner scanner, Estacionamento estacionamento, Repositorio repositorio) {
        this.scanner = scanner;
        this.estacionamento = estacionamento;
        this.repositorio = repositorio;
    }

    public void iniciar() {
        while (true) {

            System.out.println();
            System.out.println("========== ESTACIONAMENTO ==========");
            System.out.println("1. Estacionar veículo");
            System.out.println("2. Retirar veículo");
            System.out.println("3. Listar vagas");
            System.out.println("4. Listar registros");
            System.out.println("0. Salvar e sair");
            System.out.println("====================================");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    estacionarVeiculo();
                    break;

                case 2:
                    retirarVeiculo();
                    break;

                case 3:
                    listarVagas();
                    break;

                case 4:
                    listarRegistros();
                    break;

                case 0:
                    // repositorio.salvarVagas(estacionamento.getVagas());
                    // repositorio.salvarRegistros(estacionamento.getRegistros());
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void estacionarVeiculo() {
        System.out.println("Digite a placa do carro: ");
        String placa = scanner.nextLine().toUpperCase();

        System.out.println("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine().toUpperCase();

        System.out.println("Digite o tipo do veículo (carro ou moto): ");
        TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(scanner.nextLine().toUpperCase());

        Veiculo veiculo = new Veiculo(placa, modelo, tipoVeiculo);

        estacionamento.estacionar(veiculo);
    }

    private void retirarVeiculo() {
        System.out.println("Digite a placa do veículo a ser retirado: ");
        String placa = scanner.nextLine();

        try {

            double valor = estacionamento.retirarVeiculo(placa);
            System.out.println("Veículo retirado com sucesso");
            System.out.println("Valor a ser pago: R$ " + valor);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível retirar o veículo");
            System.out.println(e.getMessage());
        }
    }

    private void listarVagas() {
        List<Vaga> vagasLivres = estacionamento.listarVagasLivres();

        System.out.println("Vagas livres:");
        for (Vaga vaga : vagasLivres) {
            System.out.println(vaga);
        }

        // mostrar vagas
    }

    private void listarRegistros() {
        // mostrar registros
    }

}
