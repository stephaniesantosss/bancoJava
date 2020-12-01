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

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.ContaServiceImp;

@WebMvcTest(ContaController.class)
public class ContaControllerTest {

	static Conta conta = new Conta();
	static BaseResponse baseResponse = new BaseResponse();
	static ContaRequest request = new ContaRequest();

	Gson gson = new Gson();

	@Autowired
	private ContaController contaController;

	@MockBean
	private ContaServiceImp contaServiceImp;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.contaController);
	}

	@BeforeAll
	public static void Dados() {
		baseResponse.setStatusCode(201);
		baseResponse.setMessage("Conta Inserida");
		conta.setId(1l);
		conta.setSaldo(200);
		request.setAgencia("0001");
		request.setNumConta("222");
		request.setHash("0003");
		
	}
	
	@Test
	public void inserirContaComSucesso () {
		when(this.contaServiceImp.inserir(Mockito.any())).thenReturn(baseResponse);
		
		given().contentType("application/json").body(gson.toJson(request))
		
		.when()
		.post("/contas")
	.then()
		.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void erro500AoInserir () {
		
		given().contentType("application/json").body(gson.toJson(request))
		
		.when()
		.post("/contas")
	.then()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	public void obterPorIdComSucesso() {
		when(this.contaServiceImp.obter(1l)).thenReturn(conta);
		
		given().when()
		.get("/contas/{id}", 1l)
		.then()
		.statusCode(HttpStatus.OK.value());
		
	}
	
	@Test
	public void erro500AoObterPorId() {
		
		given().when()
		.get("/contas/{id}", 1l)
		.then()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
	}
	
	@Test
	public void obterSaldoComSucesso() {
		when(this.contaServiceImp.saldo("123")).thenReturn(conta);
		
		given().when()
		.get("/contas/saldo/{hash}", "123")
		.then()
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void erro500AoObterSaldo() {
		
		given().when()
		.get("/contas/saldo/{hash}", "123")
		.then()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	public void atualizarComSucesso() {
		when(this.contaServiceImp.atualizar(Mockito.any(), Mockito.any())).thenReturn(new BaseResponse(200, "OK"));
		
		given().contentType("application/json").body(gson.toJson(request))
		.when()
		.put("/contas/{id}", 1l)
		.then()
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void erro500Atualizar() {
		
		given().contentType("application/json").body(gson.toJson(request))
		.when()
		.put("/contas/{id}", 1l)
		.then()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	
	

}