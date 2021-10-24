package com.ficticiusclean.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosCalculoDto {
	
	@ApiModelProperty(value="Preço do Combustível (R$)", example = "6.95")
	@NotNull(message = "O preço do combustível não pode ser nulo")
	@DecimalMin(value="0.0", message="O preço do combustível não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6)
	private BigDecimal precoCombustivel;

	@ApiModelProperty(value="Valor total de quilômetros que será percorrido na cidade (Km)", example = "200")
	@NotNull(message = "O valor total em quilômetros percorrido na cidade não pode ser nulo")
	@DecimalMin(value="0.0", message="O valor total em quilômetros percorrido na cidade não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6)
	private BigDecimal totalKmPercorridoCidade;
	
	@ApiModelProperty(value="Valor total de quilômetros que será percorrido nas rodovias (Km)", example = "400")
	@NotNull(message = "O valor total em quilômetros percorrido nas rodovias não pode ser nulo")
	@DecimalMin(value="0.0", message="O valor total em quilômetros percorrido nas rodovias não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6)
	private BigDecimal totalKmPercorridoRodovias;
}
