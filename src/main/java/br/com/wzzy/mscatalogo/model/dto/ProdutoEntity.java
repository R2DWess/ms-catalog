package br.com.wzzy.mscatalogo.model.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class ProdutoEntity {

    @Id
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String image;
}
