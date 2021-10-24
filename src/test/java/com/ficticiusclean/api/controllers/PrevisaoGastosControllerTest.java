package com.ficticiusclean.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.ficticiusclean.api.dto.ParametrosCalculoDto;
import com.ficticiusclean.api.dto.PrevisaoGastoCombustivelDto;
import com.ficticiusclean.api.services.PrevisaoGastosService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PrevisaoGastosControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JacksonTester<ParametrosCalculoDto> jsonParamsDto;

	@Autowired
	JacksonTester<List<PrevisaoGastoCombustivelDto>> jsonListaPrevisao;
	
	@MockBean
	private PrevisaoGastosService previsaoGastosService;
	
	@Autowired
	private PrevisaoGastosController previsaoGastosController;
	
	/*
	 * TODO: 4 - Etapa final dos testes
	 * 
	 * Na etapa de teste do service criei um método só que será
	 * responsável por realizar o cálculo dos gastos de combustível
	 * 
	 * Analisando o problema cheguei a conclusão que não seria necessário
	 * uma entidade específica para a previsão de gastos, então criei apenas
	 * dois DTOs, um será para os parâmetros que serão recebidos no resource,
	 * outro para ser o objeto de retorno da requisição
	 * 
	 * Os testes deste controller seguem os mesmos passos do controller
	 * dos Veículos
	 * 
	 * Ao fim desses testes já foi possível validar o resultado da aplicação
	 * como um todo 
	 */
	@Test
	@Order(1)
	@DisplayName("Deve retornar não nulo quando acessar o controller bean")
	public void deveRetornarNaoNulo_quandoAcessarPrevisaoGastosController() {
		assertNotNull(previsaoGastosController);
	}
	
	@Test
	@Order(2)
	@DisplayName("Deve retornar uma lista de previsão de gastos quando solicitar GET /previsao-gastos")
	public void deveRetornarListaPrevisaoGastos_quandoSolicitarPrevisaoGastos() throws IOException, Exception {
		ParametrosCalculoDto params = new ParametrosCalculoDto(new BigDecimal(7), new BigDecimal(200), new BigDecimal(400));
		given(previsaoGastosService.calcularPrevisaoGastos(params)).willReturn(criaListaPrevisaoGastos());
		
		MockHttpServletResponse response = mockMvc.perform(get("/previsao-gastos")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(jsonParamsDto.write(params).getJson())).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaPrevisao.write(criaListaPrevisaoGastos()).getJson());
	}
	
	private List<PrevisaoGastoCombustivelDto> criaListaPrevisaoGastos() {
		List<PrevisaoGastoCombustivelDto> listaPrevisaoGastos = new ArrayList<PrevisaoGastoCombustivelDto>();
		listaPrevisaoGastos.add(new PrevisaoGastoCombustivelDto("Carro 1", "GW", "Gol", 2018, new BigDecimal(350), new BigDecimal(670)));
		listaPrevisaoGastos.add(new PrevisaoGastoCombustivelDto("Carro 2", "Fiat", "Palio", 2017, new BigDecimal(350), new BigDecimal(670)));
		listaPrevisaoGastos.add(new PrevisaoGastoCombustivelDto("Carro 3", "Hyundai", "HB20", 2019, new BigDecimal(280), new BigDecimal(470)));	
		return listaPrevisaoGastos.stream().sorted((v1, v2) -> v2.getValorTotalGastoCombutivel().compareTo(v1.getValorTotalGastoCombutivel()))
				.collect(Collectors.toList());
	}

}
