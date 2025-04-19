package br.com.wzzy.mscatalogo.model.dto;

import lombok.Data;

@Data
public class CatalogoProdutoDTO {

    private int id;
    private String title;
    private Double price;
    private String category;
    private String image;
}
