import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static conFinanceiro controle = new conFinanceiro();
    private static DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void main(String[] args) {
        int opcao;
        do {
            menu();
            opcao = LerInteiro("Escolha uma opcao");

            switch (opcao) {
                case 1: 
                    cadastrar(); 
                    break;
                case 2: 
                    listar(); 
                    break;
                case 3: 
                    consultarID(); 
                    break;
                case 4: 
                    alterar(); 
                    break;
                case 5: 
                    excluir(); 
                    break;
                case 6: 
                    filtrar(); 
                    break;
                case 7: 
                    consultarPE(); 
                    break;
                case 8: 
                    exibir(); 
                    break;
                case 0: 
                    System.out.println("Programa encerrado."); 
                    break;
                default: 
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
                    
            }
            System.out.println();
        } 
        while (opcao != 0);
        scanner.close();
    }
    private static void menu() {
        System.out.println("======================================");
        System.out.println("   CONTROLE FINANCEIRO PESSOAL");
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
        String descricao = lerDescricao();
        BigDecimal valor = lerValor();
        LocalDate data = lerData("Data (dd/MM/aaaa): ");
        TipoLancamento tipo = lerTipo();
        Categoria categoria = lerCategoria();

        Lancamento novo = controle.adicionarLancamento(descricao, valor, data, tipo, categoria);
        System.out.println("Lançamento cadastrado com sucesso! ID: " + novo.getId());
    }

    private static void listar() {
        System.out.println("--- LANCAMENTOS ---");
        exibirLista(controle.listar());
    }

    private static void consultarID(){
        int id = LerInteiro("Digite o ID: ");

        lancamento encontrado = controle.consultarId(id);

        if (encontrado == null) {
            System.out.println("lancamento nao encontrado");
        } else {
            System.out.println(encontrado);
        }

    }

    private static void alterar() {
        int id = LerInteiro("Digite o ID: ");

        Lancamento encontrado = controle.consultarId(id);

        if (encontrado == null) {
            System.out.println("Lancamento nao encontrado.");
            return;
        }

        System.out.print("Nova descricao: ");
        encontrado.getDescricao(scanner.nextLine());

        System.out.print("Novo valor: ");
        encontrado.getValor(new BigDecimal(scanner.nextLine().replace(",", ".")));

        encontrado.getData(lerData());
        encontrado.getTipo(lerTipo());
        encontrado.getCategoria(lerCategoria());

        System.out.println("Lancamento alterado com sucesso!");
    }

    private static void excluir() {
        int id = LerInteiro("Digite o ID: ");

        if (controle.excluirLancamento(id)) {
            System.out.println("Lancamento excluido!");
        } else {
            System.out.println("Lancamento nao encontrado.");
        }
    }

    private static void filtrar() {
        TipoLancamento tipo = lerTipo();
        exibirLista(controle.filtrar(tipo));
    }

    private static void consultarPE() {
        System.out.println("--- CONSULTAR POR PERIODO ---");

        LocalDate inicio = lerData();
        LocalDate fim = lerData();

        exibirLista(controle.consultarPE(inicio, fim));
    }

    private static void exibir() {
        System.out.println("--- RESUMO FINANCEIRO ---");

        System.out.println("Receitas: R$ " + controle.calcularTotalReceitas());
        System.out.println("Despesas: R$ " + controle.calcularTotalDespesas());
        System.out.println("Saldo: R$ " + controle.calcularSaldo());
    }

    private static void exibirLista(ArrayList<Lancamento> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum lancamento encontrado.");
        } else {
            for (Lancamento l : lista) {
                System.out.println(l);
            }
        }
    }

    private static int LerInteiro(String mensagem) {
        System.out.print(mensagem);
        return Integer.parseInt(scanner.nextLine());
    }

    private static LocalDate lerData() {
        System.out.print("Data (dd/MM/aaaa): ");
        return LocalDate.parse(scanner.nextLine(), FORMATO_DATA);
    }

    private static TipoLancamento lerTipo() {
        System.out.println("1 - RECEITA");
        System.out.println("2 - DESPESA");

        int opcao = LerInteiro("Escolha o tipo: ");

        if (opcao == 1) {
            return TipoLancamento.RECEITA;
        } else {
            return TipoLancamento.DESPESA;
        }
    }

    private static Categoria lerCategoria() {
        Categoria[] categorias = Categoria.values();

        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + " - " + categorias[i]);
        }

        int opcao = LerInteiro("Escolha a categoria: ");

        return categorias[opcao - 1];
    }
}
    

