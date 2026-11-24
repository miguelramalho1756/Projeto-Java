
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ControleFinanceiro controle = new ControleFinanceiro();
    private static DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao;
        do {
            menu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: consultarID(); break;
                case 4: alterar(); break;
                case 5: excluir(); break;
                case 6: filtrar(); break;
                case 7: consultarPE(); break;
                case 8: exibir(); break;
                case 0: System.out.println("Programa encerrado."); break;
                default: System.out.println("Opcao invalida. Tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
        scanner.close();
    }

    private static void menu() {
        System.out.println("======================================");
        System.out.println("       CONTROLE FINANCEIRO PESSOAL");
        System.out.println("======================================");
        System.out.println("1 - Cadastrar lancamento");
        System.out.println("2 - Listar lancamentos");
        System.out.println("3 - Consultar por identificador");
        System.out.println("4 - Alterar lancamento");
        System.out.println("5 - Excluir lancamento");
        System.out.println("6 - Filtrar por tipo");
        System.out.println("7 - Consultar por periodo");
        System.out.println("8 - Exibir resumo financeiro");
        System.out.println("0 - Sair");
    }

    private static void cadastrar() {
        System.out.println("--- CADASTRAR LANCAMENTO ---");
        Lancamento novo = controle.adicionarLancamento(
            lerDescricao(), lerValor(), lerData(), lerTipo(), lerCategoria()
        );
        System.out.println("Lancamento cadastrado com sucesso! ID: " + novo.getId());
    }

    private static void listar() {
        System.out.println("--- LANCAMENTOS ---");
        exibirLista(controle.listar());
    }

    private static void consultarID() {
        int id = lerInteiro("Digite o ID: ");
        Lancamento encontrado = controle.consultarId(id);

        if (encontrado == null)
            System.out.println("Lancamento nao encontrado.");
        else
            System.out.println(encontrado);
    }

    private static void alterar() {
        int id = lerInteiro("Digite o ID: ");
        Lancamento encontrado = controle.consultarId(id);

        if (encontrado == null) {
            System.out.println("Lancamento nao encontrado.");
            return;
        }

        System.out.println("Lancamento encontrado:\n" + encontrado);
        System.out.println("Digite os novos dados:");

        encontrado.setDescricao(lerDescricao());
        encontrado.setValor(lerValor());
        encontrado.setData(lerData());
        encontrado.setTipo(lerTipo());
        encontrado.setCategoria(lerCategoria());

        System.out.println("Lancamento alterado com sucesso!");
    }

    private static void excluir() {
        int id = lerInteiro("Digite o ID: ");

        if (controle.excluirLancamento(id))
            System.out.println("Lancamento excluido com sucesso!");
        else
            System.out.println("Lancamento nao encontrado.");
    }

    private static void filtrar() {
        System.out.println("--- FILTRAR POR TIPO ---");
        exibirLista(controle.filtrar(lerTipo()));
    }

    private static void consultarPE() {
        System.out.println("--- CONSULTAR POR PERIODO ---");
        System.out.println("Data inicial:");
        LocalDate inicio = lerData();
        System.out.println("Data final:");
        LocalDate fim = lerData();

        if (inicio.isAfter(fim)) {
            System.out.println("A data inicial nao pode ser posterior a data final!");
            return;
        }
        exibirLista(controle.consultarPE(inicio, fim));
    }

    private static void exibir() {
        System.out.println("--- RESUMO FINANCEIRO ---");
        System.out.println("Receitas: R$ " + controle.calcularTotalReceitas().setScale(2, RoundingMode.HALF_UP));
        System.out.println("Despesas: R$ " + controle.calcularTotalDespesas().setScale(2, RoundingMode.HALF_UP));
        System.out.println("Saldo: R$ " + controle.calcularSaldo().setScale(2, RoundingMode.HALF_UP));
    }

    private static void exibirLista(ArrayList<Lancamento> lista) {
        if (lista.isEmpty())
            System.out.println("Nenhum lancamento encontrado.");
        else
            for (Lancamento l : lista)
                System.out.println(l);
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido!");
            }
        }
    }

    private static String lerDescricao() {
        String descricao;
        do {
            System.out.print("Descricao: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty())
                System.out.println("A descricao nao pode ficar vazia!");
        } while (descricao.isEmpty());
        return descricao;
    }

    private static BigDecimal lerValor() {
        while (true) {
            System.out.print("Valor: R$ ");
            try {
                BigDecimal valor = new BigDecimal(scanner.nextLine().replace(",", "."));
                if (valor.compareTo(BigDecimal.ZERO) > 0)
                    return valor;
                System.out.println("O valor deve ser maior que zero!");
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor valido!");
            }
        }
    }

    private static LocalDate lerData() {
        while (true) {
            System.out.print("Data (dd/MM/yyyy): ");
            try {
                return LocalDate.parse(scanner.nextLine(), FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida! Tente novamente.");
            }
        }
    }

    private static TipoLancamento lerTipo() {
        System.out.println("1 - RECEITA\n2 - DESPESA");
        while (true) {
            int opcao = lerInteiro("Escolha o tipo: ");
            if (opcao == 1) return TipoLancamento.RECEITA;
            if (opcao == 2) return TipoLancamento.DESPESA;
            System.out.println("Opcao invalida!");
        }
    }

    private static Categoria lerCategoria() {
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++)
            System.out.println((i + 1) + " - " + categorias[i]);

        while (true) {
            int opcao = lerInteiro("Escolha a categoria: ");
            if (opcao >= 1 && opcao <= categorias.length)
                return categorias[opcao - 1];
            System.out.println("Categoria invalida!");
        }
    }
}
