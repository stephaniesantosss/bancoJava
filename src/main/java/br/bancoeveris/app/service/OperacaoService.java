package br.bancoeveris.app.service;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;

@Service
public interface OperacaoService {
	
	BaseResponse inserir(OperacaoRequest operacaoRequest);
	double saldo(Long contaId);
	BaseResponse atualizar(Long id, OperacaoRequest operacaoRequest);
	BaseResponse transferencia(TransferenciaRequest request);

}
