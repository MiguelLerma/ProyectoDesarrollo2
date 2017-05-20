/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectods2;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "mesa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesa.findAll", query = "SELECT m FROM Mesa m")
    , @NamedQuery(name = "Mesa.findByNumeromesa", query = "SELECT m FROM Mesa m WHERE m.numeromesa = :numeromesa")
    , @NamedQuery(name = "Mesa.findByPuestos", query = "SELECT m FROM Mesa m WHERE m.puestos = :puestos")})
public class Mesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numeromesa")
    private Integer numeromesa;
    @Basic(optional = false)
    @Column(name = "puestos")
    private int puestos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mesaNumeromesa")
    private Collection<Pedido> pedidoCollection;

    public Mesa() {
    }

    public Mesa(Integer numeromesa) {
        this.numeromesa = numeromesa;
    }

    public Mesa(Integer numeromesa, int puestos) {
        this.numeromesa = numeromesa;
        this.puestos = puestos;
    }

    public Integer getNumeromesa() {
        return numeromesa;
    }

    public void setNumeromesa(Integer numeromesa) {
        this.numeromesa = numeromesa;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeromesa != null ? numeromesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.numeromesa == null && other.numeromesa != null) || (this.numeromesa != null && !this.numeromesa.equals(other.numeromesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.Mesa[ numeromesa=" + numeromesa + " ]";
    }
    
}
