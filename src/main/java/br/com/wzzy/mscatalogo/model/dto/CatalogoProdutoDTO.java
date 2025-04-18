package br.com.wzzy.mscatalogo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CatalogoProdutoDTO {

    private int id;
    private String nome;
    private double preco;
    private String categoria;
    private String descricao;
    private String imagem;
}
