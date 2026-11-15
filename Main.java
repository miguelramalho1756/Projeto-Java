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
            opcao = LerInteiro("Escolha uma opção");

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
                    System.out.println("Opção inválida. Tente novamente.");
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
        System.out.println("1 - Cadastrar lançamento");
        System.out.println("2 - Listar lançamentos");
        System.out.println("3 - Consultar por identificador");
        System.out.println("4 - Alterar lançamento");
        System.out.println("5 - Excluir lançamento");
        System.out.println("6 - Filtrar por tipo");
        System.out.println("7 - Consultar por período");
        System.out.println("8 - Exibir resumo financeiro");
        System.out.println("0 - Sair");
    }

    private static void cadastrar() {
        System.out.println("--- CADASTRAR LANÇAMENTO ---");
        String descricao = lerDescricao();
        BigDecimal valor = lerValor();
        LocalDate data = lerData("Data (dd/MM/aaaa): ");
        TipoLancamento tipo = lerTipo();
        Categoria categoria = lerCategoria();

        Lancamento novo = controle.adicionarLancamento(descricao, valor, data, tipo, categoria);
        System.out.println("Lançamento cadastrado com sucesso! ID: " + novo.getId());
    }

    private static void listar() {
        System.out.println("--- LANÇAMENTOS ---");
        exibirLista(controle.listar());
    }
    
}
