package br.com.wzzy.mscatalogo.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoResponseDTO {

    private int id;
    private String title;
    private Double price;
    private String category;
}
