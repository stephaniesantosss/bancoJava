package br.bancoeveris.app.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.OperacaoServiceImp;

@WebMvcTest(OperacaoController.class)
public class OperacaoControllerTest {
	
	static Operacao operacao = new Operacao();
	static BaseResponse baseResponse = new BaseResponse();
	static OperacaoRequest request = new OperacaoRequest();
	
	Gson gson = new Gson();
	
	@Autowired
	private OperacaoController operacaoController;
	
	@MockBean
	private OperacaoServiceImp operacaoServiceImp;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.operacaoController);
	}
		
		@BeforeAll
		public static void Dados() {
			baseResponse.setStatusCode(201);
			baseResponse.setMessage("OK");
			request.setHash("123");
			request.setTipo("S");
			request.setValor(200);
			
			
		}
		
		@Test
		public void inserirComSucesso() {
			when(this.operacaoServiceImp.inserir(Mockito.any())).thenReturn(baseResponse);
			
			given().contentType("application/json").body(gson.toJson(request))
			
			.when()
			.post("/operacao/depositoSaque")
		.then()
			.statusCode(HttpStatus.CREATED.value());
			
		}
		
		@Test
		public void errro500AoTentarInserir() {
			given().contentType("application/json").body(gson.toJson(request))
			
			.when()
			.post("/operacao/depositoSaque")
		.then()
			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void transferirComSucesso() {
			when(this.operacaoServiceImp.transferencia(Mockito.any())).thenReturn(baseResponse);
			
			given().contentType("application/json").body(gson.toJson(request))
			
			.when()
			.post("/operacao/transferencia")
		.then()
			.statusCode(HttpStatus.CREATED.value());
			
		}
		
		@Test
		public void errro500AoTentarTransferir() {
			given().contentType("application/json").body(gson.toJson(request))
			
			.when()
			.post("/operacao/transferencia")
		.then()
			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		@Test
		public void atualizarComSucesso() {
			when(this.operacaoServiceImp.atualizar(Mockito.any(), Mockito.any())).thenReturn(new BaseResponse(200, "OK"));
			
			given().contentType("application/json").body(gson.toJson(request))
			.when()
			.put("/operacao/{id}", 1l)
			.then()
			.statusCode(HttpStatus.OK.value());
		}
		
		@Test
		public void erro500Atualizar() {
			
			given().contentType("application/json").body(gson.toJson(request))
			.when()
			.put("/operacao/{id}", 1l)
			.then()
			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		

}
