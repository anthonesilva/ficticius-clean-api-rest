package com.ficticiusclean.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ficticiusclean.api.models.Veiculo;
import com.ficticiusclean.api.repositories.VeiculosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VeiculosService {

	private VeiculosRepository veiculosRepository;
	
	public List<Veiculo> buscarTodos() {
		return veiculosRepository.findAll();
	}
	
	public Veiculo salvar(Veiculo veiculo) {
		return veiculosRepository.save(veiculo);
	}
	
	public Veiculo atualizar(Veiculo veiculo) {
		if(!veiculosRepository.existsById(veiculo.getId())) {
			throw new IllegalArgumentException("O veículo não existe, não será possível atualizá-lo!");
		}
		return veiculosRepository.save(veiculo);
	}

	public Veiculo buscarVeiculoById(Long veiculoId) {
		Optional<Veiculo> veiculo = veiculosRepository.findById(veiculoId);
		if(veiculo.isPresent()) {
			return veiculo.get();
		}		
		return null;
	}
	
	public void excluirById(Long veiculoId) throws Exception {
		if(veiculosRepository.existsById(veiculoId)) {
			veiculosRepository.deleteById(veiculoId);
		} else {
			throw new IllegalArgumentException("O objeto não existe no banco de dados e não pode ser excluído.");
		}
	}	
}
