package com.ficticiusclean.api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(value = "Nome do veículo", example = "Carro 1")
	@NotBlank(message = "O campo do nome não pode ser vazio")
	private String nome;
	
	@ApiModelProperty(value = "Marca do veículo", example = "Fiat")
	@NotBlank(message = "O campo da marca não pode ser vazio")
	private String marca;
	
	@ApiModelProperty(value = "Modelo do veículo", example = "Uno")
	@NotBlank(message = "O campo do modelo não pode ser vazio")
	private String modelo;
	
	@ApiModelProperty(value = "Data de vabricação", example = "1999-01-01")
	@NotNull(message = "O campo data de fabrição não pode ser nulo")
	private LocalDate dataFabricacao;
	
	@ApiModelProperty(value = "Consumo médio de combustível do veículo na cidade (Km/L)", example = "8.5")
	@NotNull(message = "O consumo médio de combustível do veículo na cidade não pode ser nulo")
	@DecimalMin(value="0.0", message="O consumo médio de combustível do veículo na cidade não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6, message = "Deve ser informado o consumo médio de combustível do veículo na cidade")
	private BigDecimal consumoMedioCombustivelCidade;
	
	@ApiModelProperty(value = "Consumo médio de combustível do veículo nas rodovias (Km/L)", example = "10.5")
	@NotNull(message = "O consumo médio de combustível do veículo nas rodovias não pode ser nulo")
	@DecimalMin(value="0.0", message="O consumo médio de combustível do veículo nas rodovias não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6, message = "Deve ser informado o consumo médio de combustível do veículo nas rodovias")
	private BigDecimal consumoMedioCombustivelRodovias;
	
}
