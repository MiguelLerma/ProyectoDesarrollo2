/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectods2;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p")
    , @NamedQuery(name = "Pedido.findByTipopedido", query = "SELECT p FROM Pedido p WHERE p.tipopedido = :tipopedido")
    , @NamedQuery(name = "Pedido.findByIdpedido", query = "SELECT p FROM Pedido p WHERE p.idpedido = :idpedido")
    , @NamedQuery(name = "Pedido.findByHorapedido", query = "SELECT p FROM Pedido p WHERE p.horapedido = :horapedido")
    , @NamedQuery(name = "Pedido.findByHoraentrega", query = "SELECT p FROM Pedido p WHERE p.horaentrega = :horaentrega")
    , @NamedQuery(name = "Pedido.findByProductoid", query = "SELECT p FROM Pedido p WHERE p.productoid = :productoid")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "tipopedido")
    private String tipopedido;
    @Id
    @Basic(optional = false)
    @Column(name = "idpedido")
    private Integer idpedido;
    @Basic(optional = false)
    @Column(name = "horapedido")
    @Temporal(TemporalType.TIME)
    private Date horapedido;
    @Basic(optional = false)
    @Column(name = "horaentrega")
    @Temporal(TemporalType.TIME)
    private Date horaentrega;
    @Basic(optional = false)
    @Column(name = "productoid")
    private int productoid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Collection<ProductoPedido> productoPedidoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoIdpedido")
    private Collection<Factura> facturaCollection;
    @JoinColumn(name = "empleado_idempleado", referencedColumnName = "idempleado")
    @ManyToOne(optional = false)
    private Empleado empleadoIdempleado;
    @JoinColumn(name = "mesa_numeromesa", referencedColumnName = "numeromesa")
    @ManyToOne(optional = false)
    private Mesa mesaNumeromesa;

    public Pedido() {
    }

    public Pedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Pedido(Integer idpedido, String tipopedido, Date horapedido, Date horaentrega, int productoid) {
        this.idpedido = idpedido;
        this.tipopedido = tipopedido;
        this.horapedido = horapedido;
        this.horaentrega = horaentrega;
        this.productoid = productoid;
    }

    public String getTipopedido() {
        return tipopedido;
    }

    public void setTipopedido(String tipopedido) {
        this.tipopedido = tipopedido;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Date getHorapedido() {
        return horapedido;
    }

    public void setHorapedido(Date horapedido) {
        this.horapedido = horapedido;
    }

    public Date getHoraentrega() {
        return horaentrega;
    }

    public void setHoraentrega(Date horaentrega) {
        this.horaentrega = horaentrega;
    }

    public int getProductoid() {
        return productoid;
    }

    public void setProductoid(int productoid) {
        this.productoid = productoid;
    }

    @XmlTransient
    public Collection<ProductoPedido> getProductoPedidoCollection() {
        return productoPedidoCollection;
    }

    public void setProductoPedidoCollection(Collection<ProductoPedido> productoPedidoCollection) {
        this.productoPedidoCollection = productoPedidoCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    public Empleado getEmpleadoIdempleado() {
        return empleadoIdempleado;
    }

    public void setEmpleadoIdempleado(Empleado empleadoIdempleado) {
        this.empleadoIdempleado = empleadoIdempleado;
    }

    public Mesa getMesaNumeromesa() {
        return mesaNumeromesa;
    }

    public void setMesaNumeromesa(Mesa mesaNumeromesa) {
        this.mesaNumeromesa = mesaNumeromesa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpedido != null ? idpedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.idpedido == null && other.idpedido != null) || (this.idpedido != null && !this.idpedido.equals(other.idpedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.Pedido[ idpedido=" + idpedido + " ]";
    }
    
}
