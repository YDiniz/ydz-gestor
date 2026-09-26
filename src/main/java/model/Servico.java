package model;

import java.util.ArrayList;
import java.util.List;

public class Servico {
    private int id;
    private String nome;
    private String tipoObra;
    private StatusServico status;
    private Escola escola;
    private List<Funcionario> funcionarios;
    private List<Documento> documentos;
    private List<Pagamento> pagamentos;


    public Servico(int id, String nome, String tipoObra, Escola escola) {
        this.id = id;
        this.nome = nome;
        this.tipoObra = tipoObra;
        this.escola = escola;
        this.status = StatusServico.ORCAMENTO;
        this.funcionarios = new ArrayList<>();
        this.documentos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void adicionarDocumento(Documento documento) {
        documentos.add(documento);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }


}