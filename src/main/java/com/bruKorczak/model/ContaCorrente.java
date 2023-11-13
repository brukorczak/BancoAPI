package com.bruKorczak.model;

import com.bruKorczak.globalExceptionalHandler.SaldoInsuficienteException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContaCorrente extends ContaBancaria {
    private static final double TAXA_MANUTENCAO_CC = 0.001;
    private final double taxaManutencao;

    public ContaCorrente(String numConta, double saldo, Cliente titular) {
        super(numConta, saldo, titular);
        this.taxaManutencao = TAXA_MANUTENCAO_CC;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        double taxa = valor * taxaManutencao;
        super.sacar(valor + taxa);
    }

    @Override
    public String toString() {
        return "\nConta Corrente: " +
                "\n Numero da Conta: " + getNumConta() +
                "\n Saldo: " + getSaldo() +
                "\n Titular: " + getTitular();
    }
}
