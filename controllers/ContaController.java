package br.com.restapimtd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restapimtd.models.Conta;
import br.com.restapimtd.services.ContaService;

@RestController
@RequestMapping(value="/api")
public class ContaController {
	
	@Autowired
	ContaService contaService;
	
	@GetMapping("/contas")
	public List<Conta> listar() {
		return contaService.listaContas();
	}
	
	@PostMapping("/conta")
	public Conta salvar(@RequestBody Conta conta) {
		return contaService.salvaConta(conta);
	}
	
}
