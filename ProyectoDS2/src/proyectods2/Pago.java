/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectods2;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pago.findByNombrePago", query = "SELECT p FROM Pago p WHERE p.nombrePago = :nombrePago")
    , @NamedQuery(name = "Pago.findByValorPago", query = "SELECT p FROM Pago p WHERE p.valorPago = :valorPago")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_pago")
    private int idPago;
    @Basic(optional = false)
    @Column(name = "nombre_pago")
    private String nombrePago;
    @Id
    @Basic(optional = false)
    @Column(name = "valor_pago")
    private Integer valorPago;
    @ManyToMany(mappedBy = "pagoCollection")
    private Collection<Factura> facturaCollection;

    public Pago() {
    }

    public Pago(Integer valorPago) {
        this.valorPago = valorPago;
    }

    public Pago(Integer valorPago, int idPago, String nombrePago) {
        this.valorPago = valorPago;
        this.idPago = idPago;
        this.nombrePago = nombrePago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getNombrePago() {
        return nombrePago;
    }

    public void setNombrePago(String nombrePago) {
        this.nombrePago = nombrePago;
    }

    public Integer getValorPago() {
        return valorPago;
    }

    public void setValorPago(Integer valorPago) {
        this.valorPago = valorPago;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valorPago != null ? valorPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.valorPago == null && other.valorPago != null) || (this.valorPago != null && !this.valorPago.equals(other.valorPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.Pago[ valorPago=" + valorPago + " ]";
    }
    
}
