package com.ficticiusclean.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ficticiusclean.api.models.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Long>{

}
