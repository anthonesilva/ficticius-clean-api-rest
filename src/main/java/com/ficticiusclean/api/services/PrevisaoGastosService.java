package com.ficticiusclean.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ficticiusclean.api.dto.ParametrosCalculoDto;
import com.ficticiusclean.api.dto.PrevisaoGastoCombustivelDto;
import com.ficticiusclean.api.models.Veiculo;

@Service
public class PrevisaoGastosService {
	
	@Autowired
	private VeiculosService veiculosService;
	
	public List<PrevisaoGastoCombustivelDto> calcularPrevisaoGastos(ParametrosCalculoDto params) {
		List<PrevisaoGastoCombustivelDto> listaPrevisaoGasto = new ArrayList<PrevisaoGastoCombustivelDto>();
		
		List<Veiculo> listaVeiculos = veiculosService.buscarTodos();
		
		listaVeiculos.forEach((veiculo) -> {
			PrevisaoGastoCombustivelDto previsao = new PrevisaoGastoCombustivelDto();
			previsao.setNome(veiculo.getNome());
			previsao.setMarca(veiculo.getMarca());
			previsao.setModelo(veiculo.getModelo());
			previsao.setAnoFabricacao(veiculo.getDataFabricacao().getYear());
			previsao.setQuantidadeCombustivelGastoLitros(calculaQuantidadeCombustivelGasto(veiculo, params));
			previsao.setValorTotalGastoCombutivel(calculaValorTotalGastoCombustivel(previsao.getQuantidadeCombustivelGastoLitros(), params.getPrecoCombustivel()));
			listaPrevisaoGasto.add(previsao);			
		});		
		
		return listaPrevisaoGasto;
	}
	
	private BigDecimal calculaQuantidadeCombustivelGasto(Veiculo veiculo, ParametrosCalculoDto params) {
		BigDecimal result = new BigDecimal(0);
		BigDecimal totalCombustivelCidade = params.getTotalKmPercorridoCidade().divide(veiculo.getConsumoMedioCombustivelCidade(), 2, RoundingMode.HALF_EVEN);
		BigDecimal totalCombustivelRodovias = params.getTotalKmPercorridoRodovias().divide(veiculo.getConsumoMedioCombustivelRodovias(), 2, RoundingMode.HALF_EVEN);
		return result.add(totalCombustivelCidade.add(totalCombustivelRodovias));		
	}
	
	private BigDecimal calculaValorTotalGastoCombustivel(BigDecimal quantidadeTotalCombustivel, BigDecimal valorCombustivel) {
		return quantidadeTotalCombustivel.multiply(valorCombustivel).setScale(2, RoundingMode.HALF_EVEN);
	}

	
}
