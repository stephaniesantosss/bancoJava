package br.bancoeveris.app.service.imp;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.ContaService;

@Service
public class ContaServiceImp implements ContaService {

	final ContaRepository _repository;
	final OperacaoServiceImp _operacaoService;

	public ContaServiceImp(ContaRepository repository, OperacaoServiceImp operacaoService) {
		_repository = repository;
		_operacaoService = operacaoService;
	}

	public Conta saldo(String hash) {
		Conta conta = new Conta();
		Conta response = new Conta();

		conta = _repository.findByHash(hash);

		if (conta == null) {
			response.setStatusCode(400);
			response.setMessage("Conta não encontrada!");
			return response;
		}

		double saldo = _operacaoService.saldo(conta.getId());
		conta.setSaldo(saldo);
		conta.setHash(conta.getHash());
		conta.setAgencia(conta.getAgencia());
		conta.setNumConta(conta.getNumConta());

		conta.setStatusCode(200);
		conta.setMessage("Consulta de saldo ok!");
		return conta;
	}

	public BaseResponse inserir(ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);
		boolean existe = true;

		while (existe == true) {
			String randomHash = conta.getHash();
			Conta contaExiste = _repository.findByHash(randomHash);

			if (contaExiste != null)
				existe = true;
			else
				existe = false;
		}

		if (contaRequest.getNumConta() == "") {
			base.setMessage("O Número da conta do cliente não foi preenchido.");
			return base;
		}
		if (contaRequest.getAgencia() == "") {
			base.setMessage("A agencia do cliente não foi preenchido.");
			return base;
		}

		conta.setHash(contaRequest.getHash());
		conta.setNumConta(contaRequest.getNumConta());
		conta.setAgencia(contaRequest.getAgencia());

		_repository.save(conta);
		base.setStatusCode(201);
		base.setMessage("Conta inserida com sucesso.");
		return base;
	}

	public Conta obter(Long id) {
		Optional<Conta> conta = _repository.findById(id);
		Conta response = new Conta();

		if (conta == null) {
			response.setMessage("Conta não encontrada");
			response.setStatusCode(404);
			return response;
		}

		response.setMessage("Conta obtida com sucesso");
		response.setStatusCode(200);
		response.setAgencia(conta.get().getAgencia());
		response.setNumConta(conta.get().getNumConta());
		response.setHash(conta.get().getHash());
		return response;
	}

	public BaseResponse atualizar(Long id, ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);

		if (contaRequest.getNumConta() == "") {
			base.setMessage("O número da conta não foi preenchido");
			return base;
		}
		if (contaRequest.getAgencia() == "") {
			base.setMessage("A agencia da conta não foi preenchida");
			return base;
		}

		conta.setId(id);
		conta.setHash(contaRequest.getHash());
		conta.setAgencia(contaRequest.getAgencia());
		conta.setNumConta(contaRequest.getNumConta());

		_repository.save(conta);
		base.setStatusCode(200);
		base.setMessage("Conta atualizada com sucesso.");
		return base;
	}

}
