package com.ficticiusclean.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ficticiusclean.api.config.SwaggerConfig;
import com.ficticiusclean.api.dto.ParametrosCalculoDto;
import com.ficticiusclean.api.dto.PrevisaoGastoCombustivelDto;
import com.ficticiusclean.api.services.PrevisaoGastosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/previsao-gastos")
@Api(tags = {SwaggerConfig.PREVISAO_CALCULOS_TAG})
public class PrevisaoGastosController {
	
	@Autowired
	private PrevisaoGastosService previsaoGastosService;
	
	@GetMapping
	@ApiOperation(value = "Calcular previsão de gastos com combustível")
	public ResponseEntity<List<PrevisaoGastoCombustivelDto>> calcularPrevisaoGastos(@Valid @RequestBody ParametrosCalculoDto params) {
		try {
			List<PrevisaoGastoCombustivelDto> listaPrevisaoGastos = previsaoGastosService.calcularPrevisaoGastos(params)
					.stream().sorted((v1, v2) -> v2.getValorTotalGastoCombutivel().compareTo(v1.getValorTotalGastoCombutivel()))
					.collect(Collectors.toList());
			return ResponseEntity.ok(listaPrevisaoGastos);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

}
