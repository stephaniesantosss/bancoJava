package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.ContaServiceImp;

@RestController
@RequestMapping("/contas")
public class ContaController extends BaseController {
	
	private final ContaServiceImp _service;
	
	@Autowired
	public ContaController(ContaServiceImp service) {
		_service = service;
	}
	
	@PostMapping
    public ResponseEntity inserir(@RequestBody ContaRequest contaRequest) {
		try {
			BaseResponse response = _service.inserir(contaRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);			
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
    }
	
	@GetMapping(path = "/{id}")
    public ResponseEntity obter(@PathVariable Long id) {		
		try {
			Conta response = _service.obter(id);
			return ResponseEntity.status(response.getStatusCode()).body(response);	
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}   	
    }
	
	@GetMapping(path = "/saldo/{hash}")
    public ResponseEntity saldo(@PathVariable String hash) {		
		try {
			Conta response = _service.saldo(hash);
			return ResponseEntity.status(response.getStatusCode()).body(response);	
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}   	
    }

//	@GetMapping
//    public ResponseEntity listar() {		
//		try {
//			ContaList contas = _service.listar();  		
//	    	return ResponseEntity.status(HttpStatus.OK).body(contas);			
//		} catch (Exception e) {
//			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);			
//		}		
//    }
//	
//	@DeleteMapping(path = "/{id}")
//	public ResponseEntity deletar(@PathVariable Long id) {
//		try {
//			BaseResponse response = _service.deletar(id);
//			return ResponseEntity.status(response.StatusCode).build(); 
//		} catch (Exception e) {
//			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
//		}
//	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaRequest contaRequest, @PathVariable Long id) {
		try {
			BaseResponse response = _service.atualizar(id, contaRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
	}

}