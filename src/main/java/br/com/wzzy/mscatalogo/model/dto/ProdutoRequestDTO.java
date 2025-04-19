package br.com.wzzy.mscatalogo.model.dto;

import lombok.Data;

@Data
public class ProdutoRequestDTO {

    private int id;
    private String title;
    private double price;
    private String category;
    private String image;
}
