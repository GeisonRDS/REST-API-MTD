package br.com.restapimtd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restapimtd.models.Conta;
import br.com.restapimtd.repository.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	
	public List<Conta> listaContas() {
		return contaRepository.findAll();
	}
	
	public Conta salvaConta(Conta conta) {
		
		if (conta.getDataPagamento().after(conta.getDataVencimento())) {
			
			conta.setQuantidadeDias(calculaQuantidadeDiasAtraso(conta));
			conta.setValorCorrigido(calculaValorCorrigido(conta));
			
		} else {
			
			conta.setQuantidadeDias(0);
			conta.setValorCorrigido(0.0);
			
		}
		
		conta.setRegra(montaRegra(conta));
		
		return contaRepository.save(conta);
	}
	
	private Integer calculaQuantidadeDiasAtraso(Conta conta) {
			
		Integer milissegundos = (int) conta.getDataPagamento().getTime() - (int) conta.getDataVencimento().getTime();

		return milissegundos / (1000*60*60*24);
	
	}
	
	private double calculaValorCorrigido(Conta conta) {
		
		double valor = conta.getValor();
		
		double jurosMulta = this.calculoJurosMulta(conta);

		return valor + (jurosMulta * valor);

	}
	
	private double calculoJurosMulta(Conta conta) {
		
		Integer quantidadeDias = conta.getQuantidadeDias();
		
		if (quantidadeDias <= 3) {
			
			return (quantidadeDias * (0.1 / 100)) + 2.0 / 100;
			
		} else if (quantidadeDias <= 10) {
			
			return(quantidadeDias * (0.2 / 100)) + 3.0 / 100;
			
		} else {
			
			return (quantidadeDias * (0.3 / 100)) + 5.0 / 100;
			
		} 
		
	}
	
	private String montaRegra(Conta conta) {
		
		Integer quantidadeDias = conta.getQuantidadeDias();
		
		String regraUm = "Multa 2.0% e juros de 0.1% ao dia";
		String regraDois = "Multa 3.0% e juros de 0.2% ao dia";
		String regraTres = "Multa 5.0% e juros de 0.3% ao dia";
		
		if (quantidadeDias <= 3) {
			
			return regraUm;
			
		} else if (quantidadeDias <= 10) {
			
			return regraDois;
			
		} else {
			
			return regraTres;
			
		} 
		
	}

}
