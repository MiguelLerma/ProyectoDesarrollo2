/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectods2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "producto_pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoPedido.findAll", query = "SELECT p FROM ProductoPedido p")
    , @NamedQuery(name = "ProductoPedido.findByProductoProductoid", query = "SELECT p FROM ProductoPedido p WHERE p.productoPedidoPK.productoProductoid = :productoProductoid")
    , @NamedQuery(name = "ProductoPedido.findByPedidoIdpedido", query = "SELECT p FROM ProductoPedido p WHERE p.productoPedidoPK.pedidoIdpedido = :pedidoIdpedido")
    , @NamedQuery(name = "ProductoPedido.findByCantidad", query = "SELECT p FROM ProductoPedido p WHERE p.cantidad = :cantidad")})
public class ProductoPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoPedidoPK productoPedidoPK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "pedido_idpedido", referencedColumnName = "idpedido", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;
    @JoinColumn(name = "producto_productoid", referencedColumnName = "productoid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ProductoPedido() {
    }

    public ProductoPedido(ProductoPedidoPK productoPedidoPK) {
        this.productoPedidoPK = productoPedidoPK;
    }

    public ProductoPedido(ProductoPedidoPK productoPedidoPK, int cantidad) {
        this.productoPedidoPK = productoPedidoPK;
        this.cantidad = cantidad;
    }

    public ProductoPedido(int productoProductoid, int pedidoIdpedido) {
        this.productoPedidoPK = new ProductoPedidoPK(productoProductoid, pedidoIdpedido);
    }

    public ProductoPedidoPK getProductoPedidoPK() {
        return productoPedidoPK;
    }

    public void setProductoPedidoPK(ProductoPedidoPK productoPedidoPK) {
        this.productoPedidoPK = productoPedidoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoPedidoPK != null ? productoPedidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoPedido)) {
            return false;
        }
        ProductoPedido other = (ProductoPedido) object;
        if ((this.productoPedidoPK == null && other.productoPedidoPK != null) || (this.productoPedidoPK != null && !this.productoPedidoPK.equals(other.productoPedidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.ProductoPedido[ productoPedidoPK=" + productoPedidoPK + " ]";
    }
    
}
