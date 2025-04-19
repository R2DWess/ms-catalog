package br.com.wzzy.mscatalogo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoResponseDTO {

    private int id;
    private String title;
    private Double price;
    private String category;
}
