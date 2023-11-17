package com.bruKorczak.model;

import com.bruKorczak.globalExceptionalHandler.SaldoInsuficienteException;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContaCorrente extends ContaBancaria {
    @Id
    private Long id;
    private static final double TAXA_MANUTENCAO_CC = 0.001;
    private static final AtomicInteger contadorContas = new AtomicInteger(1);
    private final double taxaManutencao;

    public ContaCorrente(String numConta, double saldo, Cliente titular) {
        super(numConta, saldo, titular);
        this.id = (long) contadorContas.getAndIncrement();
        this.taxaManutencao = TAXA_MANUTENCAO_CC;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        double taxa = valor * taxaManutencao;
        super.sacar(valor + taxa);
    }
}
