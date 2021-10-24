package com.ficticiusclean.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import com.ficticiusclean.api.models.Veiculo;
import com.ficticiusclean.api.services.VeiculosService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class VeiculosControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private VeiculosController veiculosController;
	
	@MockBean
	private VeiculosService veiculosService;
	
	@Autowired
	private JacksonTester<Veiculo> jsonVeiculo;
	
	@Autowired
	private JacksonTester<List<Veiculo>> jsonListaVeiculo;
	
	/*
	 * TODO: 3 - Terceiro passo
	 * Após a concepção dos services a ideia agora é implementar
	 * os Controllers, iniciando pelos Veiculos, a princípio,
	 * implementar todas as requisições para atender ao REST e
	 * complementar com as validações e tratamentos de exceções
	 * 
	 * Após isso, vou seguir para o controller da previsão de
	 * gastos e posteriormente configurar o Swagger para facilitar
	 * o uso da API
	 * 
	 * Ao fim destes testes já foi possível enviar requisições para
	 * os endpoints 
	 */
	@Test
	@Order(1)
	@DisplayName("Deve retornar não nulo quando acessar o controller bean")
	public void deveRetornarNaoNulo_quandoAcessarController() {
		assertNotNull(veiculosController);
	}
	
	/*
	 * Teste criado para validar o endpoint que busca todos os veículos
	 */
	@Test
	@Order(2)
	@DisplayName("Deve retornar lista de veiculos ao solicitar via GET /carros")
	public void deveRetornarListaVeiculo_quandoSolicitarGetBuscarTodos() throws Exception {
		
		given(veiculosService.buscarTodos()).willReturn(criaListaVeiculos());
		
		MockHttpServletResponse response = mockMvc.perform(get("/veiculos")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaVeiculo.write(criaListaVeiculos()).getJson());
	}
	/*
	 * Teste criado para validar o endpoint que busca por ID
	 * 
	 * Durante a execução dos testes foi necessário mudar o método utilizado
	 * no repositório e consequente mudar o retorno para Optional
	 * 
	 * O teste de service foi refatorado, assim como o método do controller
	 */
	@Test
	@Order(3)
	@DisplayName("Deve retornar veículo ao solicitar via GET /veiculos/{id}")
	public void deveRetornarVeiculo_quandoSolicitarGetBuscarVeiculoById() throws Exception {
		
		given(veiculosService.buscarVeiculoById(1L)).willReturn(retornaVeiculoCriado());
		
		MockHttpServletResponse response = mockMvc.perform(get("/veiculos/1")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonVeiculo.write(retornaVeiculoCriado()).getJson());	
	}
	
	/*
	 * Teste criado para validar o endpoint que salva um veículo
	 */
	@Test
	@Order(4)
	@DisplayName("Deve retornar veículo salvo ao solicitar via POST /veiculos")
	public void deveRetornarVeiculo_quandoSolicitarPostSalvar() throws IOException, Exception {
		
		given(veiculosService.salvar(retornaVeiculoRequisicao())).willReturn(retornaVeiculoCriado());
		
		MockHttpServletResponse response = mockMvc.perform(
				post("/veiculos")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonVeiculo.write(retornaVeiculoRequisicao()).getJson().toString())).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonVeiculo.write(retornaVeiculoCriado()).getJson());
	}
	
	/*
	 * Mediante a necessidade de realizar a alteração do veículo de uma forma
	 * diferente foi necessário criar posteriormente esse teste para garantir
	 * que a atualização funcionasse de forma correta
	 * 
	 * Também foi atualizado o teste do service correspondente
	 */	
	@Test
	@Order(5)
	@DisplayName("Deve retornar veículo alterado ao solicitar PUT /veiculos")
	public void deveRetornarVeiculoAlterado_quandoSolicitarPutAlterar() throws IOException, Exception {
		
		given(veiculosService.atualizar(retornaVeiculoRequisicao())).willReturn(retornaVeiculoRequisicao());
		
		MockHttpServletResponse responseBadRequest = mockMvc.perform(
				put("/veiculos")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonVeiculo.write(retornaVeiculoRequisicao()).getJson())).andReturn().getResponse();
		
		assertThat(responseBadRequest.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		given(veiculosService.atualizar(alteraVeiculo())).willReturn(alteraVeiculo());
		
		MockHttpServletResponse responseOk = mockMvc.perform(
				put("/veiculos")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonVeiculo.write(alteraVeiculo()).getJson())).andReturn().getResponse();
		
		assertThat(responseOk.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseOk.getContentAsString()).isEqualTo(jsonVeiculo.write(alteraVeiculo()).getJson());
	}
	
	/*
	 * Teste criado para validar o endpoint que exclui um veículo
	 */	
	@Test
	@Order(6)
	@DisplayName("Deve retornar status OK ao solicitar DELETE /veiculos/{id}")
	public void deveRetornarOk_quandoSolicitarDeleteById() throws Exception {
		
		doNothing().when(veiculosService).excluirById(2L);
		
		MockHttpServletResponse response = mockMvc.perform(delete("/veiculos/2")).andReturn().getResponse();
		
		verify(veiculosService, times(1)).excluirById(2L);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	private List<Veiculo> criaListaVeiculos()  {
		List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
		listaVeiculos.add(new Veiculo(1L, "Carro 1", "Volks", "GOL", LocalDate.parse("2008-10-01"), new BigDecimal(9), new BigDecimal(11)));
		listaVeiculos.add(new Veiculo(2L, "Carro 2", "Fiat", "Palio", LocalDate.parse("2009-10-01"), new BigDecimal(10), new BigDecimal(12)));
		listaVeiculos.add(new Veiculo(3L, "Carro 3", "Hyundai", "HB20", LocalDate.parse("2010-10-01"), new BigDecimal(8), new BigDecimal(11)));
		return listaVeiculos;
	}
	
	private Veiculo retornaVeiculoRequisicao() {
		return new Veiculo(null, "Carro 1", "Volks", "GOL", LocalDate.parse("2008-10-01"), new BigDecimal(9), new BigDecimal(11));
	}
	
	private Veiculo retornaVeiculoCriado() {
		return new Veiculo(1L, "Carro 1", "Volks", "GOL", LocalDate.parse("2008-10-01"), new BigDecimal(9), new BigDecimal(11));
	}
	
	private Veiculo alteraVeiculo() {
		return new Veiculo(1L, "Carro 1", "Fiat", "Palio", LocalDate.parse("2008-10-01"), new BigDecimal(9), new BigDecimal(11));
	}

}
