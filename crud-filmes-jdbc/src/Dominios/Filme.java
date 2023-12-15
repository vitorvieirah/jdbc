package Dominios;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Filme {
    private Integer id;
    private String nome;
    private String dataDeLancamento;
    private String duracao;
    private String classifacao; 
}
