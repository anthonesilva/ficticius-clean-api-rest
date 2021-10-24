package com.ficticiusclean.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ficticiusclean.api.models.Veiculo;
import com.ficticiusclean.api.repositories.VeiculosRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class VeiculosServiceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private VeiculosService veiculosService;
	
	@MockBean
	private VeiculosRepository veiculosRepository;
	
	@BeforeEach
	void setUp() {
		veiculosService = new VeiculosService(veiculosRepository);
	}
	
	/*
	 * TODO: 1 - Onde o projeto foi iniciado
	 * Teste criado para iniciar o desenvolvimento da API e satisfazer
	 * as dependências do Service, o projeto foi iniciado aqui...
	 * 
	 * Ao criar o Service, sabendo da necessidade do Repository adicionei
	 * o mesmo na classe do Service, mesmo mockando o Repository como ainda
	 * não tinha sido criado foi necessário criá-lo e por consequência
	 * criar o Model de veículo.
	 * 
	 * Aproveitei para criar o Model anotado como Entity, defini o nome da
	 * tabela no banco e adicionei os campos que julguei necessários para
	 * atender ao desafio, com isso, finalizei com as importações necessárias
	 * no Repository, Service e no teste.
	 * 
	 * Ao fim do teste estavam criados os objetos Model do Veículo e respectivos
	 * Repository e Service.
	 */
	@Test
	@Order(1)
	@DisplayName("Deve retornar não nulo quando acessar o service bean")
	public void deveRetornarNaoNulo_quandoAcessarService() {
		assertNotNull(this.veiculosService);
	}
	
	/*
	 * Teste criado para verificar o método findAll
	 */
	@Test
	@Order(2)
	@DisplayName("Deve retornar List<Veiculo> preenchida quando buscar todos")
	public void deveRetornarListaVeiculos_quandoChamarServiceBuscarTodos() {		
		veiculosService.buscarTodos();
		verify(veiculosRepository, times(1)).findAll();
	}
	
	/*
	 * Teste criado para verificar o método salvar
	 */
	@Test
	@Order(3)
	@DisplayName("Deve salvar e retornar um Veiculo quando salvar")
	public void deveRetornarVeiculoCriado_quandoChamarServiceSalvar() {
		Veiculo veiculo = new Veiculo(null, "Carro 1", "Volkswagen", "Gol", LocalDate.parse("2018-10-21"), new BigDecimal(9), new BigDecimal(12));
		
		veiculosService.salvar(veiculo);
		ArgumentCaptor<Veiculo> capturaVeiculoSalvo = ArgumentCaptor.forClass(Veiculo.class);
		verify(veiculosRepository).save(capturaVeiculoSalvo.capture());
		
		Veiculo veiculoRetorno = capturaVeiculoSalvo.getValue();
		
		assertThat(veiculoRetorno).isEqualTo(veiculo);
	}
	
	/*
	 * Teste criado para verificar o método buscarVeiculoById
	 * 
	 * Reescrito posteriormente para validar com Optional
	 */
	@Test
	@Order(4)
	@DisplayName("Deve retornar um Veiculo quando buscar por ID")
	public void deveRetornarVeiculo_quandoChamarServiceBuscarVeiculoById() {
		Long veiculoId = 1L;
		Veiculo veiculo = new Veiculo(1L, "Carro 1", "Volkswagen", "Gol", LocalDate.parse("2018-10-21"), new BigDecimal(9), new BigDecimal(12));
		Optional<Veiculo> veiculoRetornoNulo = Optional.empty();
		
		when(veiculosRepository.findById(veiculoId)).thenReturn(veiculoRetornoNulo);
		
		Veiculo retorno = veiculosService.buscarVeiculoById(veiculoId);
		assertEquals(veiculoRetornoNulo, Optional.empty());
		
		when(veiculosRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));
		retorno = veiculosService.buscarVeiculoById(veiculoId);
		
		assertThat(retorno).isEqualTo(veiculo);
	}
	
	/*
	 * Teste criado para verificar o método excluirById
	 * 
	 * O teste precisou ser alterado posteriormente para lidar
	 * com as exceções
	 */
	@Test()
	@Order(5)
	@DisplayName("Deve excluir um Veiculo quando enviar ID para exclusão")
	public void deveExcluirVeiculo_quandoChamarServiceExcluirById() throws Exception {
		Long veiculoId = 1L;
		when(veiculosRepository.existsById(veiculoId)).thenReturn(false);
		assertThrows(IllegalArgumentException.class, () -> veiculosService.excluirById(veiculoId));
		verify(veiculosRepository, times(0)).deleteById(veiculoId);	
		
		when(veiculosRepository.existsById(veiculoId)).thenReturn(true);
		veiculosService.excluirById(veiculoId);
		verify(veiculosRepository, times(1)).deleteById(veiculoId);
	}
	
	/*
	 * Teste criado para verificar o método atualizar
	 * 
	 * Esse teste foi criado posteriormente diante da necessidade
	 * de tratar as atualizações de veículos de forma diferente
	 */
	@Test
	@Order(6)
	@DisplayName("Deve atualizar e retornar o Veiculo atualizado quando atualizar")
	public void deveRetornarVeiculoAtualizado_quandoChamarServiceAtualizar() {
		Veiculo veiculo = new Veiculo(1L, "Carro 1", "Volkswagen", "Gol", LocalDate.parse("2018-10-21"), new BigDecimal(9), new BigDecimal(12));

		when(veiculosRepository.existsById(veiculo.getId())).thenReturn(false);
		assertThrows(IllegalArgumentException.class, () -> veiculosService.atualizar(veiculo));
		verify(veiculosRepository, times(0)).save(veiculo);
				
		veiculosService.salvar(veiculo);
		ArgumentCaptor<Veiculo> capturaVeiculoSalvo = ArgumentCaptor.forClass(Veiculo.class);
		verify(veiculosRepository).save(capturaVeiculoSalvo.capture());
		
		Veiculo veiculoRetorno = capturaVeiculoSalvo.getValue();
		
		assertThat(veiculoRetorno).isEqualTo(veiculo);
	}
}
