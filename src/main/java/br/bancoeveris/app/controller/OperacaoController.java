package br.bancoeveris.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.imp.OperacaoServiceImp;

@RestController
@RequestMapping("/operacao")
public class OperacaoController extends BaseController {
	
	private final OperacaoServiceImp _service;
	
	@Autowired
	public OperacaoController(OperacaoServiceImp service) {
		_service = service;
	}
	
	@PostMapping (path = "/depositoSaque")
    public ResponseEntity inserir(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = _service.inserir(operacaoRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);			
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
    }
	@PostMapping (path = "/transferencia")
    public ResponseEntity transferencia(@RequestBody TransferenciaRequest transferencia) {
		try {
			BaseResponse response = _service.transferencia(transferencia);
			return ResponseEntity.status(response.getStatusCode()).body(response);			
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
    }
	
	
//	@GetMapping(path = "/{id}")
//    public ResponseEntity obter(@PathVariable Long id) {		
//		try {
//			Operacao response = _service.obter(id);
//			return ResponseEntity.status(response.StatusCode).body(response);	
//		} catch (Exception e) {
//			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
//		}   	
//    }

//	@GetMapping
//    public ResponseEntity listar() {		
//		try {
//			OperacaoList operacoes = _service.listar();  		
//	    	return ResponseEntity.status(HttpStatus.OK).body(operacoes);			
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
//	
	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody OperacaoRequest operacaoRequest, @PathVariable Long id) {
		try {
			BaseResponse response = _service.atualizar(id, operacaoRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
	}

}