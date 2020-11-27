package br.bancoeveris.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.OperacaoServiceImp;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperacaoServiceTest {

	@TestConfiguration
	static class contaServiceImpTestConfiguration {

		static OperacaoRequest novaOperacaoRequest = new OperacaoRequest();
		static Operacao novaoperacao = new Operacao();
		static Conta conta = new Conta();

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

		@BeforeAll
		public static void setup() {

			novaOperacaoRequest.setTipo("T");
			novaOperacaoRequest.setValor(200);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaoperacao.setTipo("T");
			novaoperacao.setValor(200);

		}

		@Test
		public void inserir() {

			novaOperacaoRequest.setTipo("T");
			novaOperacaoRequest.setValor(200);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaoperacao.setContaDestino(conta);
			novaoperacao.setContaOrigem(conta);
			novaoperacao.setTipo("T");
			novaoperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

		@Test
		public void inserirContaNull() {

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(null);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 404);

		}

		@Test
		public void inserirTipoNull() {

			novaOperacaoRequest.setTipo(null);
			novaoperacao.setTipo(null);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void inserirValorZero() {

			novaOperacaoRequest.setTipo("T");
			novaOperacaoRequest.setValor(0);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaoperacao.setTipo("T");
			novaoperacao.setValor(0);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void atualizar() {
			novaOperacaoRequest.setTipo("T");
			novaOperacaoRequest.setValor(200);
			conta.setHash("123");
			novaoperacao.setTipo("T");
			novaoperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 200);
		}

		@Test
		public void atualizarTipoVazio() {
			novaOperacaoRequest.setTipo("");
			novaOperacaoRequest.setValor(200);
			conta.setHash("123");
			novaoperacao.setTipo("");
			novaoperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void atualizarValorZero() {
			novaOperacaoRequest.setTipo("D");
			novaOperacaoRequest.setValor(0);
			conta.setHash("123");
			novaoperacao.setTipo("D");
			novaoperacao.setValor(0);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);

			BaseResponse resposta = operacaoServiceImp.atualizar(1l, novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void inserirTipoD() {
			novaOperacaoRequest.setTipo("D");
			novaOperacaoRequest.setValor(200);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaoperacao.setContaDestino(conta);
			novaoperacao.setContaOrigem(conta);
			novaoperacao.setTipo("D");
			novaoperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

		@Test
		public void inserirTipoS() {
			novaOperacaoRequest.setTipo("S");
			novaOperacaoRequest.setValor(200);
			novaOperacaoRequest.setHash("123");
			conta.setHash("123");
			novaoperacao.setContaDestino(conta);
			novaoperacao.setContaOrigem(conta);
			novaoperacao.setTipo("S");
			novaoperacao.setValor(200);

			Mockito.when(operacaoRepository.save(novaoperacao)).thenReturn(novaoperacao);
			Mockito.when(contaRepository.findByHash(conta.getHash())).thenReturn(conta);

			BaseResponse resposta = operacaoServiceImp.inserir(novaOperacaoRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}

	}

}
