package domains;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Filme {
    private Integer id;
    private String nome;
    private String dataDeLancamento;
    private String duracao;
    private String classifacao; 
}
