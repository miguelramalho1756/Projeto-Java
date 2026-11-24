
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lancamento {

    private int id;
    private String descricao;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private Categoria categoria;
    private LocalDate data;

    public Lancamento(int id, String descricao, BigDecimal valor, TipoLancamento tipo, Categoria categoria, LocalDate data) {
        
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.data = data;
    }

    public int getId() {return id;}
    public String getDescricao() {return descricao;}
    public BigDecimal getValor() {return valor;}
    public TipoLancamento getTipo() {return tipo;}
    public Categoria getCategoria() {return categoria;}
    public LocalDate getData() {return data;}

    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setValor(BigDecimal valor) {this.valor = valor;}
    public void setTipo(TipoLancamento tipo) {this.tipo = tipo;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public void setData(LocalDate data) {this.data = data;}

    @Override
    public String toString() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "\nID: " + id
                + " | Descricao: " + descricao
                + " | Valor: R$ " + valor.setScale(2, RoundingMode.HALF_UP)
                + " | Tipo: " + tipo
                + " | Categoria: " + categoria
                + " | Data: " + data.format(formato)
                + "\n--------------------------------------";
    }
}
