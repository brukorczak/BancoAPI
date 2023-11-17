package br.com.banco;

import com.bruKorczak.globalExceptionalHandler.ContaInvalidaException;
import com.bruKorczak.globalExceptionalHandler.SaldoInsuficienteException;
import com.bruKorczak.model.Cliente;
import com.bruKorczak.model.ContaCorrente;
import com.bruKorczak.service.IContaCorrenteService;
import com.bruKorczak.service.ContaCorrenteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/v1/contacorrente")
public class AccountResource {

    List<ContaCorrente> contaCorrentes = new ArrayList<>();
    IContaCorrenteService contaService = new ContaCorrenteServiceImpl(contaCorrentes);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listarContas() {
        List<ContaCorrente> contas = contaService.listarContas();
        List<String> informacoesContas = new ArrayList<>();

        for (ContaCorrente conta : contas) {
            informacoesContas.add(String.format("Conta: %s, Saldo: R$%s", conta.getId(), conta.getSaldo().floatValue()));
        }
        return informacoesContas;
    }

    @POST
    @Path("/criarconta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarConta(String jsonInput) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Cliente cliente = objectMapper.readValue(jsonInput, Cliente.class);
            ContaCorrente contaCorrente = contaService.criarConta(cliente.getNome(), cliente.getCpf());

            String mensagem = String.format(
                    "\nConta criada com sucesso:" +
                            "\nConta Corrente: %s" +
                            "\nSaldo: %s" +
                            "\nTitular:" +
                            "\nID: %s" +
                            "\n  Nome: %s" +
                            "\n  CPF: %s",
                    contaCorrente.getNumConta(),
                    contaCorrente.getSaldo(),
                    contaCorrente.getId(),
                    cliente.getNome(),
                    cliente.getCpf());

            return Response.status(Response.Status.CREATED).entity(mensagem).build();
        } catch (ContaInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar conta: " + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro inesperado ao criar a conta.").build();
        }
    }

    @GET
    @Path("/saldo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSaldo(@PathParam("id") Long id) {
        ContaCorrente conta = contaService.getContaPorId(id);
        if (conta != null) {
            String mensagem = "O saldo atual da conta " + id + " é: R$" + conta.getSaldo().floatValue();
            return Response.ok(mensagem).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada para o número: " + id).build();
        }
    }

    @POST
    @Path("/depositar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response depositar(@FormParam("numConta") String numConta, @FormParam("valor") double valorDeposito) {
        try {
            contaService.depositar(numConta, valorDeposito);
            String mensagem = "Depósito de R$ " + valorDeposito + " realizado na conta de número: " + numConta;
            return Response.ok(mensagem).build();
        } catch (ContaInvalidaException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao depositar: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocorreu um erro inesperado ao realizar o depósito.")
                    .build();
        }
    }

    @POST
    @Path("/sacar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sacar(@FormParam("numConta") String numConta, @FormParam("valor") double valorSaque) {
        try {
            contaService.sacar(numConta, valorSaque);
            String mensagem = "Saque de R$ " + valorSaque + " realizado na conta: " + numConta;
            return Response.ok(mensagem).build();
        } catch (ContaInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao sacar: " + e.getMessage()).build();
        } catch (SaldoInsuficienteException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Saldo insuficiente para sacar R$ " + valorSaque + " na conta: " + numConta).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro inesperado ao realizar o saque.").build();
        }
    }

    @PATCH
    @Path("/transferir")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferir(@FormParam("contaOrigem") String contaOrigem,
                               @FormParam("contaDestino") String contaDestino,
                               @FormParam("valor") double valorTransferencia) {
        try {
            contaService.transferir(contaOrigem, contaDestino, valorTransferencia);
            String mensagem = "Transferência de R$ " + valorTransferencia + " realizada com sucesso de " +
                    "Conta de origem: " + contaOrigem + " para Conta de destino: " + contaDestino;
            return Response.ok(mensagem).build();
        } catch (ContaInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao transferir: " + e.getMessage()).build();
        } catch (SaldoInsuficienteException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Saldo insuficiente para transferir R$ " + valorTransferencia + " da conta: " + contaOrigem).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocorreu um erro inesperado na transação de transferência.").build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirConta(@PathParam("id") Long id) {
        try {
            boolean contaExcluida = contaService.excluirConta(id);
            if (contaExcluida) {
                return Response.ok("Conta excluída com sucesso.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada para o número: " + id).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir a conta.").build();
        }
    }
}
