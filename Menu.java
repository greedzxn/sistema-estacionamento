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
            System.out.println("5. Consultar vaga");
            System.out.println("6. Listar veículos estacionados");
            System.out.println("7. Mostrar faturamento");
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

                case 5:
                    consultarVaga();
                    break;

                case 6:
                    listarVeiculosEstacionados();
                    break;

                case 7:
                    mostrarFaturamento();
                    break;

                case 0:
                    repositorio.salvarVagas(estacionamento.getVagas());
                    repositorio.salvarRegistros(estacionamento.getRegistros());
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void estacionarVeiculo() {

        System.out.println("========== ESTACIONAR VEÍCULO ==========\n");
        System.out.print("Digite a placa do carro: ");
        String placa = scanner.nextLine().toUpperCase();

        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine().toUpperCase();

        System.out.print("Digite o tipo do veículo (carro ou moto): ");
        TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(scanner.nextLine().toUpperCase());

        Veiculo veiculo = new Veiculo(placa, modelo, tipoVeiculo);

        try {
            estacionamento.estacionar(veiculo);
            System.out.println("Seu veículo foi estacionado com sucesso!\n");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\nNão foi possível estacionar o veículo");
            System.out.println(e.getMessage() + "\n");
        }
    }

    private void retirarVeiculo() {

        System.out.println("========== RETIRAR VEÍCULO ==========\n");
        System.out.print("Digite a placa do veículo a ser retirado: ");
        String placa = scanner.nextLine();

        try {

            double valor = estacionamento.retirarVeiculo(placa);
            System.out.println("Veículo retirado com sucesso");
            System.out.println("Valor a ser pago: R$ " + valor + "\n");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível retirar o veículo");
            System.out.println(e.getMessage() + "\n");
        }
    }

    private void listarVagas() {

        List<Vaga> vagasLivres = estacionamento.listarVagasLivres();
        List<Vaga> vagasOcupadas = estacionamento.listarVagasOcupadas();

        System.out.println("========== VAGAS ==========\n");

        if (vagasLivres.isEmpty() && vagasOcupadas.isEmpty()) {
            System.out.println("Não há vagas livres nem ocupadas");
            return;
        }

        if (vagasLivres.isEmpty()) {
            System.out.println("Não há vagas livres");
        } else {
            System.out.println("Livres:");
            for (Vaga vaga : vagasLivres) {
                System.out.println(vaga);

            }
        }

        if (vagasOcupadas.isEmpty()) {
            System.out.println("Não há vagas ocupadas");
        } else {
            System.out.println("\nOcupadas:");
            for (Vaga vaga : vagasOcupadas) {
                System.out.println(vaga);
            }
        }

    }

    private void listarRegistros() {

        List<RegistroEstacionamento> registros = estacionamento.listarHistorico();

        System.out.println("========== REGISTROS ==========\n");

        if (registros.isEmpty()) {
            System.out.println("Não há registros\n");
            return;
        }

        for (RegistroEstacionamento registro : registros) {
            System.out.println(registro);
        }
    }

    private void consultarVaga() {

        System.out.println("========== CONSULTAR VAGA ==========\n");
        System.out.print("Digite o número da vaga para consulta: ");
        Vaga vaga = estacionamento.buscarVagaPorNumero(Integer.parseInt(scanner.nextLine()));

        if (vaga.estaLivre()) {
            System.out.println("A vaga " + vaga.getNumero() + " está livre!\n");
        } else {
            System.out.println("A vaga " + vaga.getNumero() + " está ocupada\n");
        }

    }

    private void listarVeiculosEstacionados() {

        List<Vaga> vagas = estacionamento.listarVagasOcupadas();

        System.out.println("========== // ==========\n");
        System.out.println("Veículos estacionados:");

        for (Vaga vaga : vagas) {
            System.out.println("Vaga " + vaga.getNumero() + ": " + vaga.getVeiculo().getModelo() + " - "
                    + vaga.getVeiculo().getPlaca());
        }
        System.out.println();

    }

    private void mostrarFaturamento() {

        System.out.println("========== FATURAMENTO ==========\n");
        System.out.println("Total faturado: R$" + estacionamento.calcularFaturamento());
        System.out.println();

    }
}
