package br.com.restapimtd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restapimtd.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
