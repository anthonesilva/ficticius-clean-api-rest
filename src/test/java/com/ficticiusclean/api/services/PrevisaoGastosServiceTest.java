package com.ficticiusclean.api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ficticiusclean.api.dto.ParametrosCalculoDto;
import com.ficticiusclean.api.dto.PrevisaoGastoCombustivelDto;
import com.ficticiusclean.api.models.Veiculo;

@SpringBootTest
public class PrevisaoGastosServiceTest {
	
	@Autowired
	private PrevisaoGastosService previsaoGastosService;
	
	@MockBean
	private VeiculosService veiculosService;
	
	/*
	 * Valida funcionamento do Service
	 */
	@Test
	@Order(1)
	@DisplayName("Deve retornar não nulo quando acessar o service bean")
	public void deveRetornarNaoNulo_quandoAcessarService() {
		assertNotNull(this.previsaoGastosService);
	}
	
	/*
	 * TODO: 2 - Segundo passo
	 * 
	 * A ideia principal é enviar uma requisição para o controller da previsão de gastos,
	 * utilizar um DTO para os veículos retornando os valores calculados em uma lista
	 * ranqueada descrescente em relação ao maior valor gasto
	 * 
	 * O cálculo será realizado na camada de serviço e utilizará como base os dados
	 * da requisição enviada e a lista de veiculos do banco de dados
	 * 
	 * A princípio considerei se era necessário realizar a persistência da lista, porém,
	 * cheguei a conclusão que não seria necessário nesse momento e criei os DTOs a fim
	 * de atender ao propósito do desafio
	 * 
	 * Ao fim desses testes o service da previsão de gastos estava criado e funcionando
	 */
	@Test
	@Order(2)
	@DisplayName("Deve retornar uma lista de DTOs dos cálculos de previsão de combustível")
	public void deveRetornarListaPrevisaoGastos_quandoChamarMetodoCalcularGastos() {
		ParametrosCalculoDto params = new ParametrosCalculoDto(new BigDecimal(6.95), new BigDecimal(200.15), new BigDecimal(400.40));
		List<PrevisaoGastoCombustivelDto> previsaoGastos = new ArrayList<PrevisaoGastoCombustivelDto>();

		when(veiculosService.buscarTodos()).thenReturn(criaListaVeiculos());
		when(previsaoGastosService.calcularPrevisaoGastos(params)).thenReturn(previsaoGastos);
		
		previsaoGastos = previsaoGastosService.calcularPrevisaoGastos(params);
		assertNotNull(previsaoGastos);
	}
	
	private List<Veiculo> criaListaVeiculos()  {
		List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
		listaVeiculos.add(new Veiculo(1L, "Carro 1", "Volks", "GOL", LocalDate.parse("2008-10-01"), new BigDecimal(9), new BigDecimal(11)));
		listaVeiculos.add(new Veiculo(2L, "Carro 2", "Fiat", "Palio", LocalDate.parse("2009-10-01"), new BigDecimal(10), new BigDecimal(12)));
		listaVeiculos.add(new Veiculo(3L, "Carro 3", "Hyundai", "HB20", LocalDate.parse("2010-10-01"), new BigDecimal(8), new BigDecimal(11)));
		return listaVeiculos;
	}

}
