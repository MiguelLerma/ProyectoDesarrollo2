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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByNombreproducto", query = "SELECT p FROM Producto p WHERE p.nombreproducto = :nombreproducto")
    , @NamedQuery(name = "Producto.findByFoto", query = "SELECT p FROM Producto p WHERE p.foto = :foto")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByCategoria", query = "SELECT p FROM Producto p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Producto.findByProductoid", query = "SELECT p FROM Producto p WHERE p.productoid = :productoid")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nombreproducto")
    private String nombreproducto;
    @Basic(optional = false)
    @Column(name = "foto")
    private String foto;
    @Basic(optional = false)
    @Column(name = "precio")
    private int precio;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "categoria")
    private String categoria;
    @Id
    @Basic(optional = false)
    @Column(name = "productoid")
    private Integer productoid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductoPedido> productoPedidoCollection;

    public Producto() {
    }

    public Producto(Integer productoid) {
        this.productoid = productoid;
    }

    public Producto(Integer productoid, String nombreproducto, String foto, int precio, String descripcion, String categoria) {
        this.productoid = productoid;
        this.nombreproducto = nombreproducto;
        this.foto = foto;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getProductoid() {
        return productoid;
    }

    public void setProductoid(Integer productoid) {
        this.productoid = productoid;
    }

    @XmlTransient
    public Collection<ProductoPedido> getProductoPedidoCollection() {
        return productoPedidoCollection;
    }

    public void setProductoPedidoCollection(Collection<ProductoPedido> productoPedidoCollection) {
        this.productoPedidoCollection = productoPedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoid != null ? productoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoid == null && other.productoid != null) || (this.productoid != null && !this.productoid.equals(other.productoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.Producto[ productoid=" + productoid + " ]";
    }
    
}
