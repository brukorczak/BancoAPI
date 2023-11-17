package com.bruKorczak.service;

import com.bruKorczak.globalExceptionalHandler.ContaInvalidaException;
import com.bruKorczak.globalExceptionalHandler.SaldoInsuficienteException;
import com.bruKorczak.model.ContaCorrente;

import java.util.List;

public interface IContaCorrenteService {

    ContaCorrente getContaPorNumero(String numConta);

    ContaCorrente getContaPorId(Long id);

    boolean cpfExistente(String cpf);

    boolean excluirConta(Long id);

    List<ContaCorrente> listarContas();

    ContaCorrente criarConta(String nome, String cpf) throws ContaInvalidaException;

    void depositar(String numConta, double valor) throws ContaInvalidaException;

    void sacar(String numConta, double valor) throws ContaInvalidaException, SaldoInsuficienteException;

    void transferir(String contaOrigem, String contaDestino, double valor)
            throws ContaInvalidaException, SaldoInsuficienteException;
}
