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
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.ContaServiceImp;
import br.bancoeveris.app.service.imp.OperacaoServiceImp;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContaServiceTest {

	@TestConfiguration
	static class contaServiceImpTestConfiguration {

		static ContaRequest novaContaRequest = new ContaRequest();
		static Conta novaConta = new Conta();

		@InjectMocks
		ContaServiceImp contaServiceImp;

		@Mock
		ContaRepository contaRepository;
		
		@Mock
		OperacaoServiceImp operacaoServiceImp;

		@BeforeEach
		public void initMock() {

			MockitoAnnotations.initMocks(this);

		}

		@BeforeAll
		public static void setup() {

			novaContaRequest.setAgencia("1123");
			novaContaRequest.setNumConta("1234");
			novaConta.setAgencia("1123");
			novaConta.setNumConta("1234");
			novaConta.setSaldo(200);
			novaConta.setHash("123");

			novaConta.setStatusCode(200);

		}

		@Test
		public void inserirConta() {

			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.inserir(novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 201);
		}
		
		
		@Test
		public void inserirContaVazia() {
			
			novaContaRequest.setAgencia("");
			novaContaRequest.setNumConta("");
			novaConta.setAgencia("");
			novaConta.setNumConta("");
			
			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.inserir(novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}
		
		@Test
		public void inserirNumContaVazio() {
			novaContaRequest.setAgencia("123");
			novaContaRequest.setNumConta("");
			novaConta.setAgencia("123");
			novaConta.setNumConta("");
			
			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.inserir(novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
			
		}
		
		@Test
		public void inserirNumAgenciaVazio() {
			novaContaRequest.setAgencia("");
			novaContaRequest.setNumConta("1011");
			novaConta.setAgencia("");
			novaConta.setNumConta("1011");
			novaConta.setId(1l);
			
			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.inserir(novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
			
		}

		@Test
		public void obterPorId() {
			Long id = 1l;

			Mockito.when(contaRepository.findById(id)).thenReturn(java.util.Optional.of(novaConta));

			Conta response = contaServiceImp.obter(id);

			Assertions.assertEquals(response.getStatusCode(), 200);

		}
		
		@Test
		public void obterPorIdNull() {
			Long id = null;
			
			Mockito.when(contaRepository.findById(id)).thenReturn(null);

			Conta response = contaServiceImp.obter(id);

			Assertions.assertEquals(response.getStatusCode(), 404);
		}

		@Test
		public void atualizar() {
			novaContaRequest.setAgencia("0001");
			novaContaRequest.setNumConta("2222");
			novaConta.setAgencia("0001");
			novaConta.setNumConta("2222");

			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.atualizar(1l, novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 200);
		}
		
		@Test
		public void atualizarSemConta() {
			novaContaRequest.setAgencia("0001");
			novaContaRequest.setNumConta("");
			novaConta.setAgencia("0001");
			novaConta.setNumConta("");

			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.atualizar(1l, novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}
		
		@Test
		public void atualizarSemAgencia() {
			novaContaRequest.setAgencia("");
			novaContaRequest.setNumConta("2222");
			novaConta.setAgencia("");
			novaConta.setNumConta("2222");

			Mockito.when(contaRepository.save(novaConta)).thenReturn(novaConta);

			BaseResponse resposta = contaServiceImp.atualizar(1l, novaContaRequest);

			Assertions.assertEquals(resposta.getStatusCode(), 400);
		}

		@Test
		public void consultarSaldo() {
			String hash = "123";
			double saldo = 0;

			Mockito.when(contaRepository.findByHash(hash)).thenReturn(novaConta);
			Mockito.when(operacaoServiceImp.saldo(novaConta.getId())).thenReturn(saldo);

			Conta response = contaServiceImp.saldo(hash);
			
			Assertions.assertEquals(response.getStatusCode(), 200);
		}
		
		@Test
		public void consultarSaldoHashVazio() {
			String hash = "";
			double saldo = 0;
			Conta conta = new Conta();

			Mockito.when(contaRepository.findByHash(hash)).thenReturn(null);
			Mockito.when(operacaoServiceImp.saldo(conta.getId())).thenReturn(saldo);

			Conta response = contaServiceImp.saldo(hash);
			
			Assertions.assertEquals(response.getStatusCode(), 400);
		}
	}

}
