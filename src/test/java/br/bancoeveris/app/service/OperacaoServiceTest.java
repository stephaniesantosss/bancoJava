package br.bancoeveris.app.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.OperacaoServiceImp;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperacaoServiceTest {

	@TestConfiguration
	static class contaServiceImpTestConfiguration {

		static OperacaoRequest novaOperacaoRequest = new OperacaoRequest();
		static TransferenciaRequest tranferenciaRequest = new TransferenciaRequest();
		static Operacao novaOperacao = new Operacao();
		static List<Operacao> operacoes = new ArrayList();
		static Conta conta = new Conta();
		static Conta conta2 = new Conta();

		@InjectMocks
		OperacaoServiceImp operacaoServiceImp;

		@Mock
		OperacaoRepository operacaoRepository;

		@Mock
		ContaRepository contaRepository;

		@BeforeEach
		public void initMock() {

			MockitoAnnotations.initMocks(this);

		}

		@BeforeEach
		public void setup() {

			novaOperacaoRequest.setTipo("T");
			novaOperacaoRequest.setValor(200);
			novaOperacaoRequest.setHash("123");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta2);
			conta.setHash("123");
			conta2.setHash("321");
			conta.setId(1l);
			conta2.setId(2l);
			novaOperacao.setTipo("T");
			novaOperacao.setValor(200);
			tranferenciaRequest.setValor(200);
			operacoes.add(novaOperacao);

		}

		@Test
		public void inserir() {

			novaOperacaoRequest.setTipo("T");

			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta);
			novaOperacao.setTipo("T");
			novaOperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

		@Test
		public void inserirContaNull() {

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(null);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 404);

		}

		@Test
		public void inserirTipoNull() {

			novaOperacaoRequest.setTipo(null);
			novaOperacao.setTipo(null);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void inserirValorZero() {

			novaOperacaoRequest.setValor(0);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaOperacao.setTipo("T");
			novaOperacao.setValor(0);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void atualizar() {

			conta.setHash("123");
			novaOperacao.setTipo("T");
			novaOperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 200);
		}

		@Test
		public void atualizarTipoVazio() {
			novaOperacaoRequest.setTipo("");

			conta.setHash("123");
			novaOperacao.setTipo("");
			novaOperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void atualizarValorZero() {
			novaOperacaoRequest.setTipo("D");
			novaOperacaoRequest.setValor(0);
			conta.setHash("123");
			novaOperacao.setTipo("D");
			novaOperacao.setValor(0);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void inserirTipoD() {
			novaOperacaoRequest.setTipo("D");

			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta);
			novaOperacao.setTipo("D");
			novaOperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

		@Test
		public void inserirTipoS() {
			novaOperacaoRequest.setTipo("S");

			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta);
			novaOperacao.setTipo("S");
			novaOperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

		@Test
		public void tranferir() {

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(201, resposta.getStatusCode());
		}

		@Test
		public void transferirComHashDestinoNull() {

			tranferenciaRequest.setHashDestino(null);
			tranferenciaRequest.setHashOrigem("123");
			tranferenciaRequest.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(null);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}

		@Test
		public void transferirComHashOrigemNull() {

			tranferenciaRequest.setHashDestino("033");
			tranferenciaRequest.setHashOrigem(null);
			tranferenciaRequest.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(null);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}

		@Test
		public void tranferirComHashDestinoVazio() {

			tranferenciaRequest.setHashDestino("");
			tranferenciaRequest.setHashOrigem("123");
			tranferenciaRequest.setValor(200);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}

		@Test
		public void tranferirComHashOrigemVazio() {

			tranferenciaRequest.setHashDestino("123");
			tranferenciaRequest.setHashOrigem("");

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}

		@Test
		public void valorMenorZero() {
			
			tranferenciaRequest.setValor(-11);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}

		@Test
		public void valorIgualZero() {
			
			tranferenciaRequest.setValor(0);

			Mockito.when(operacaoRepository.save(novaOperacao)).thenReturn(novaOperacao);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashDestino())).thenReturn(conta);
			Mockito.when(contaRepository.findByHash(tranferenciaRequest.getHashOrigem())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.transferencia(tranferenciaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);

		}
		
		@Test
		public void verificarSaldoD() {
			conta.setSaldo(400);
			novaOperacao.setValor(200);
			novaOperacao.setTipo("D");
			
			Mockito.when(operacaoRepository.findOperacoesPorConta(1l)).thenReturn(operacoes);
			
			double resposta = operacaoServiceImp.saldo(1l);

			Assertions.assertEquals(resposta, resposta);

		}
		
		@Test
		public void verificarSaldoS() {

			novaOperacao.setTipo("S");
			
			Mockito.when(operacaoRepository.findOperacoesPorConta(1l)).thenReturn(operacoes);
			
			double resposta = operacaoServiceImp.saldo(1l);

			Assertions.assertEquals(resposta, resposta);

		}
		
		@Test
		public void verificarSaldoT() {
			
			novaOperacao.setTipo("T");
			
			Mockito.when(operacaoRepository.findOperacoesPorConta(1l)).thenReturn(operacoes);
			
			double resposta = operacaoServiceImp.saldo(1l);

			Assertions.assertEquals(resposta, resposta);

		}
		
		@Test
		public void verificarSaldoTContaIgualOrigem() {
			
			novaOperacao.setTipo("T");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta);
			
			Mockito.when(operacaoRepository.findOperacoesPorConta(1l)).thenReturn(operacoes);
			
			double resposta = operacaoServiceImp.saldo(1l);

			Assertions.assertEquals(resposta, resposta);

		}
		
		@Test
		public void verificarSaldoTContaIgualDestino() {
		
			novaOperacao.setTipo("T");
			novaOperacao.setContaDestino(conta);
			novaOperacao.setContaOrigem(conta);
			
			Mockito.when(operacaoRepository.findOperacoesPorConta(1l)).thenReturn(operacoes);
			
			double resposta = operacaoServiceImp.saldo(1l);

			Assertions.assertEquals(resposta, resposta);

		}
		

	}

}
