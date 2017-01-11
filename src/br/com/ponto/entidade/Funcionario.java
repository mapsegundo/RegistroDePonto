/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shall
 */
@Entity
@Table(schema = "sigi", name = "funcionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionario.listaTodos", query = "SELECT f FROM Funcionario f where f.funcStatus = true")})
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "func_pk_id")
    private Integer funcPkId;
    @Basic(optional = false)
    @Column(name = "func_cpf")
    private String funcCpf;
    @Basic(optional = false)
    @Column(name = "func_nome")
    private String funcNome;
    @Basic(optional = false)
    @Column(name = "func_rg")
    private String funcRg;
    @Column(name = "func_status")
    private Boolean funcStatus;
    @Lob
    @Column(name = "func_digital1")
    private byte[] funcDigital1;
    @Lob
    @Column(name = "func_digital2")
    private byte[] funcDigital2;

    public Funcionario() {
    }

    public Funcionario(Integer funcPkId) {
        this.funcPkId = funcPkId;
    }

    public Funcionario(Integer funcPkId, String funcCpf, Date funcDataNascimento, String funcEndereco, String funcNome, String funcRg, String funcSexo, boolean funcStatus) {
        this.funcPkId = funcPkId;
        this.funcCpf = funcCpf;
        this.funcNome = funcNome;
        this.funcRg = funcRg;
    }

    public Integer getFuncPkId() {
        return funcPkId;
    }

    public void setFuncPkId(Integer funcPkId) {
        this.funcPkId = funcPkId;
    }

    public String getFuncCpf() {
        return funcCpf;
    }

    public void setFuncCpf(String funcCpf) {
        this.funcCpf = funcCpf;
    }

    public String getFuncNome() {
        return funcNome;
    }

    public void setFuncNome(String funcNome) {
        this.funcNome = funcNome;
    }

    public String getFuncRg() {
        return funcRg;
    }

    public void setFuncRg(String funcRg) {
        this.funcRg = funcRg;
    }

    public Boolean getFuncStatus() {
        return funcStatus;
    }

    public void setFuncStatus(Boolean funcStatus) {
        this.funcStatus = funcStatus;
    }
    
    public byte[] getFuncDigital1() {
        return funcDigital1;
    }

    public void setFuncDigital1(byte[] funcDigital1) {
        this.funcDigital1 = funcDigital1;
    }

    public byte[] getFuncDigital2() {
        return funcDigital2;
    }

    public void setFuncDigital2(byte[] funcDigital2) {
        this.funcDigital2 = funcDigital2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcPkId != null ? funcPkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.funcPkId == null && other.funcPkId != null) || (this.funcPkId != null && !this.funcPkId.equals(other.funcPkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return funcNome;
    }
    
}
