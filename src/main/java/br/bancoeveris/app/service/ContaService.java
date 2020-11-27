package br.bancoeveris.app.service;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;


@Service
public interface ContaService {

	Conta saldo(String hash);
	BaseResponse inserir(ContaRequest contaRequest);
	Conta obter(Long id);
	BaseResponse atualizar(Long id, ContaRequest contaRequest);
	
	
}
