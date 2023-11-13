package acme;

import com.bruKorczak.globalExceptionalHandler.ContaInvalidaException;
import com.bruKorczak.globalExceptionalHandler.SaldoInsuficienteException;
import com.bruKorczak.model.ContaCorrente;
import com.bruKorczak.service.ContaCorrenteService;
import com.bruKorczak.service.ContaCorrenteServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/contacorrente")
public class GreetingResource {

    List<ContaCorrente> contaCorrentes = new ArrayList<>();
    ContaCorrenteService contaService = new ContaCorrenteServiceImpl(contaCorrentes);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String criarConta(@FormParam("nome") String nome, @FormParam("cpf") String cpf) {
        try {
            ContaCorrente contaCorrente = contaService.criarConta(nome, cpf);

            return String.format(
                    "\nConta criada com sucesso:" +
                            "\nConta Corrente: %s" +
                            "\nSaldo: %s" +
                            "\nTitular:" +
                            "\n  Nome: %s" +
                            "\n  CPF: %s",
                    contaCorrente.getNumConta(),
                    contaCorrente.getSaldo(),
                    nome,
                    cpf);
        } catch (ContaInvalidaException e) {
            return "Erro ao criar conta: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro inesperado ao criar a conta.";
        }
    }

    @GET
    @Path("/contas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listarContas() {
        List<ContaCorrente> contas = contaService.listarContas();
        List<String> informacoesContas = new ArrayList<>();

        for (ContaCorrente conta : contas) {
            informacoesContas.add(String.format("Conta: %s, Saldo: R$%s", conta.getNumConta(), conta.getSaldo().floatValue()));
        }
        return informacoesContas;
    }

    @GET
    @Path("/saldo/{numConta}")
    @Produces(MediaType.TEXT_PLAIN)
    public String verSaldo(@PathParam("numConta") String numConta) {
        ContaCorrente conta = contaService.getContaPorNumero(numConta);
        if (conta != null) {
            return "O saldo atual da conta " + numConta + " é: R$" + conta.getSaldo().floatValue();
        } else {
            return "Conta não encontrada para o número: " + numConta;
        }
    }

    @POST
    @Path("/depositar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String depositar(@FormParam("numConta") String numConta, @FormParam("valor") double valorDeposito) {
        try {
            contaService.depositar(numConta, valorDeposito);
            return "Depósito de R$ " + valorDeposito + " realizado na conta de número: " + numConta;
        } catch (ContaInvalidaException e) {
            return "Erro ao depositar: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro inesperado ao realizar o depósito.";
        }
    }

    @POST
    @Path("/sacar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String sacar(@FormParam("numConta") String numConta, @FormParam("valor") double valorSaque) {
        try {
            contaService.sacar(numConta, valorSaque);
            return "Saque de R$ " + valorSaque + " realizado na conta: " + numConta;
        } catch (ContaInvalidaException e) {
            return "Erro ao sacar: " + e.getMessage();
        } catch (SaldoInsuficienteException e) {
            return "Saldo insuficiente para sacar R$ " + valorSaque + " na conta: " + numConta;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro inesperado ao realizar o saque.";
        }
    }

    @PATCH
    @Path("/transferir")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String transferir(@FormParam("contaOrigem") String contaOrigem,
                             @FormParam("contaDestino") String contaDestino,
                             @FormParam("valor") double valorTransferencia) {
        try {
            contaService.transferir(contaOrigem, contaDestino, valorTransferencia);
            return "Transferência de R$ " + valorTransferencia + " realizada com sucesso de " +
                    "Conta de origem: " + contaOrigem + " para Conta de destino: " + contaDestino;
        } catch (ContaInvalidaException e) {
            return "Erro ao transferir: " + e.getMessage();
        } catch (SaldoInsuficienteException e) {
            return "Saldo insuficiente para transferir R$ " + valorTransferencia + " da conta: " + contaOrigem;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro inesperado na transação de transferência.";
        }
    }
}
