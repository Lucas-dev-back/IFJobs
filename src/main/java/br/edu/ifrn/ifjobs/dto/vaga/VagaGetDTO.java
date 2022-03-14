package br.edu.ifrn.ifjobs.dto.vaga;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.edu.ifrn.ifjobs.dto.Dto;
import br.edu.ifrn.ifjobs.model.Aluno;
import br.edu.ifrn.ifjobs.model.Empresa;
import br.edu.ifrn.ifjobs.model.Vaga;
import br.edu.ifrn.ifjobs.model.enums.StatusVaga;

public class VagaGetDTO implements Dto<Vaga, VagaGetDTO> {

    private int id;

    private String cursoAlvo;

    private String titulo;

    private String descricao;

    private String localizacao;

    private Date dataCriacao;

    private StatusVaga status;

    private Set<Aluno> alunos = new HashSet<>();

    private Empresa empresa;

    private ModelMapper modelMapper;

    @Override
    public Vaga convertDtoToEntity() {
        modelMapper = new ModelMapper();
        return modelMapper.map(this, Vaga.class);
    }

    @Override
    public VagaGetDTO convertEntityToDto(Vaga entity) {
        modelMapper = new ModelMapper();
        return modelMapper.map(entity, VagaGetDTO.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCursoAlvo() {
        return cursoAlvo;
    }

    public void setCursoAlvo(String cursoAlvo) {
        this.cursoAlvo = cursoAlvo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusVaga getStatus() {
        return status;
    }

    public void setStatus(StatusVaga status) {
        this.status = status;
    }

    public Set<Integer> getAlunos() {
        return this.alunos.stream()
                .map(Aluno::getId)
                .collect(Collectors.toSet());
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void addAluno(Aluno aluno) {
        this.alunos.add(aluno);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
