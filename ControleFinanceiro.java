import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class conFinanceiro {

    private final ArrayList<Lancamento> lancamentos;
    private int proximoId;

    public conFinanceiro() {
        lancamentos = new ArrayList<>();
        proximoId = 1;
    }

    public Lancamento adicionarLancamento(String descricao,
            BigDecimal valor, LocalDate data,
            TipoLancamento tipo, Categoria categoria) {

        Lancamento novo = new Lancamento(
                proximoId++, descricao, valor, tipo, categoria, data);

        lancamentos.add(novo);

        return novo;
    }

    public Lancamento consultarId(int id) {
        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getId() == id) {
                return lancamento;
            }
        }

        return null;
    }

    public ArrayList<Lancamento> listar() {
        return new ArrayList<>(lancamentos);
    }

    public boolean excluirLancamento(int id) {
        Lancamento encontrado = consultarId(id);

        if (encontrado == null) {
            return false;
        }

        lancamentos.remove(encontrado);
        return true;
    }

    public ArrayList<Lancamento> filtrar(TipoLancamento tipo) {
        ArrayList<Lancamento> resultado = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getTipo() == tipo) {
                resultado.add(lancamento);
            }
        }

        return resultado;
    }

    public ArrayList<Lancamento> consultarPE(
            LocalDate inicio, LocalDate fim) {

        ArrayList<Lancamento> resultado = new ArrayList<>();

        for (Lancamento lancamento : lancamentos) {
            LocalDate data = lancamento.getData();

            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                resultado.add(lancamento);
            }
        }

        return resultado;
    }

    public BigDecimal calcularTotalReceitas() {
        BigDecimal total = BigDecimal.ZERO;

        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getTipo() == TipoLancamento.RECEITA) {
                total = total.add(lancamento.getValor());
            }
        }

        return total;
    }

    public BigDecimal calcularTotalDespesas() {
        BigDecimal total = BigDecimal.ZERO;

        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getTipo() == TipoLancamento.DESPESA) {
                total = total.add(lancamento.getValor());
            }
        }

        return total;
    }

    public BigDecimal calcularSaldo() {
        return calcularTotalReceitas()
                .subtract(calcularTotalDespesas());
    }
}