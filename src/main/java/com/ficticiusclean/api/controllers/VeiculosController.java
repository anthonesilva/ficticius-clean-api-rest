package com.ficticiusclean.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.ficticiusclean.api.config.SwaggerConfig;
import com.ficticiusclean.api.models.Veiculo;
import com.ficticiusclean.api.services.VeiculosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/veiculos")
@Api(tags = {SwaggerConfig.VEICULOS_TAG})
public class VeiculosController {
	
	@Autowired
	private VeiculosService veiculosService;
	
	@GetMapping
	@ApiOperation(value = "Consultar todos os veículos")
	public ResponseEntity<List<Veiculo>> buscarTodos() {
		return ResponseEntity.ok(veiculosService.buscarTodos());		
	}
	
	@GetMapping(path = {"/{id}"})
	@ApiOperation(value = "Consultar um veículo por ID")
	public ResponseEntity<Veiculo> buscarVeiculoById(@PathVariable long id) {
		Veiculo veiculo = veiculosService.buscarVeiculoById(id);
		if(veiculo != null) {
			return ResponseEntity.ok(veiculo);
		}
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ApiOperation(value = "Criar um novo veículo")
	public ResponseEntity<Veiculo> salvarVeiculo(@Valid @RequestBody Veiculo veiculo) {
		try {
			Veiculo veiculoRetorno = veiculosService.salvar(veiculo);
			return ResponseEntity
					.created(UriComponentsBuilder.fromPath("/veiculos/{id}").buildAndExpand(veiculoRetorno.getId()).toUri())
					.body(veiculoRetorno);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	@PutMapping
	@ApiOperation(value = "Atualizar um veículo")
	public ResponseEntity<Veiculo> atualizarVeiculo(@Valid @RequestBody Veiculo veiculo) {
		try {
			if(veiculo.getId() == null) {
				throw new Exception("O ID do veículo é obrigatório na atualização.");
			}
			Veiculo veiculoSalvo = veiculosService.atualizar(veiculo);
			return ResponseEntity.ok(veiculoSalvo);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	@DeleteMapping(path = {"/{id}"})
	@ApiOperation(value = "Excluir um veículo")
	public ResponseEntity<?> excluirVeiculo(@PathVariable long id) {
		try {
			veiculosService.excluirById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

}
