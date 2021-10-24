package com.ficticiusclean.api.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrevisaoGastoCombustivelDto {
	
	@ApiModelProperty(value = "Nome do veículo")
	private String nome;

	@ApiModelProperty(value = "Marca do veículo")
	private String marca;

	@ApiModelProperty(value = "Modelo do veículo")
	private String modelo;
	
	@ApiModelProperty(value = "Ano de fabricação do veículo")
	private int anoFabricacao;
	
	@ApiModelProperty(value = "Previsão da quantidade de litros de combustível")
	private BigDecimal quantidadeCombustivelGastoLitros;
	
	@ApiModelProperty(value = "Previsão do valor total em reais")
	private BigDecimal valorTotalGastoCombutivel;
}
