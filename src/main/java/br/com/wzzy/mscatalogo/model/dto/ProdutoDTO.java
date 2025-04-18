package br.com.wzzy.mscatalogo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProdutoDTO {

    private int id;
    private String title;
    private double price;
    private String category;
}