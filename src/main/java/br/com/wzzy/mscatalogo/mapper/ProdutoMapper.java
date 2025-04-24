package br.com.wzzy.mscatalogo.mapper;

import br.com.wzzy.mscatalogo.model.dto.CatalogoProdutoDTO;
import br.com.wzzy.mscatalogo.model.dto.ProdutoEntity;
import br.com.wzzy.mscatalogo.model.dto.ProdutoRequestDTO;
import br.com.wzzy.mscatalogo.model.dto.ProdutoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    private ModelMapper modelMapper;

    public ProdutoEntity converterCatalogoProdutoDTOParaEntidade(CatalogoProdutoDTO catalogoProdutoDTO) {
        return modelMapper.map(catalogoProdutoDTO, ProdutoEntity.class);
    }

    public ProdutoEntity converterRequestDTOParaEntidade(ProdutoRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, ProdutoEntity.class);
    }

    public ProdutoResponseDTO converterEntidadeParaProdutoResponseDTO(ProdutoEntity entidade) {
        return modelMapper.map(entidade, ProdutoResponseDTO.class);
    }
}