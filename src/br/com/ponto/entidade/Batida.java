/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.entidade;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shall
 */
@Entity
@Table(name = "batida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batida.findAll", query = "SELECT b FROM Batida b")
    , @NamedQuery(name = "Batida.findByBatPkId", query = "SELECT b FROM Batida b WHERE b.batPkId = :batPkId")
    , @NamedQuery(name = "Batida.findByBatData", query = "SELECT b FROM Batida b WHERE b.batData = :batData")
    , @NamedQuery(name = "Batida.findByBatHora", query = "SELECT b FROM Batida b WHERE b.batHora = :batHora")})
public class Batida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bat_pk_id")
    private Integer batPkId;
    @Column(name = "bat_data")
    @Temporal(TemporalType.DATE)
    private Date batData;
    @Column(name = "bat_hora")
    @Temporal(TemporalType.TIME)
    private Time batHora;
    @JoinColumn(name = "bat_fk_funcionario_id", referencedColumnName = "func_pk_id")
    @ManyToOne
    private Funcionario batFkFuncionarioId;

    public Batida() {
    }

    public Batida(Integer batPkId) {
        this.batPkId = batPkId;
    }

    public Integer getBatPkId() {
        return batPkId;
    }

    public void setBatPkId(Integer batPkId) {
        this.batPkId = batPkId;
    }

    public Date getBatData() {
        return batData;
    }

    public void setBatData(Date batData) {
        this.batData = batData;
    }

    public Time getBatHora() {
        return batHora;
    }

    public void setBatHora(Time batHora) {
        this.batHora = batHora;
    }

    public Funcionario getBatFkFuncionarioId() {
        return batFkFuncionarioId;
    }

    public void setBatFkFuncionarioId(Funcionario batFkFuncionarioId) {
        this.batFkFuncionarioId = batFkFuncionarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (batPkId != null ? batPkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Batida)) {
            return false;
        }
        Batida other = (Batida) object;
        if ((this.batPkId == null && other.batPkId != null) || (this.batPkId != null && !this.batPkId.equals(other.batPkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ponto.entidade.Batida[ batPkId=" + batPkId + " ]";
    }

}
