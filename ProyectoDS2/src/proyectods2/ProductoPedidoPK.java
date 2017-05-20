/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectods2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author User
 */
@Embeddable
public class ProductoPedidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "producto_productoid")
    private int productoProductoid;
    @Basic(optional = false)
    @Column(name = "pedido_idpedido")
    private int pedidoIdpedido;

    public ProductoPedidoPK() {
    }

    public ProductoPedidoPK(int productoProductoid, int pedidoIdpedido) {
        this.productoProductoid = productoProductoid;
        this.pedidoIdpedido = pedidoIdpedido;
    }

    public int getProductoProductoid() {
        return productoProductoid;
    }

    public void setProductoProductoid(int productoProductoid) {
        this.productoProductoid = productoProductoid;
    }

    public int getPedidoIdpedido() {
        return pedidoIdpedido;
    }

    public void setPedidoIdpedido(int pedidoIdpedido) {
        this.pedidoIdpedido = pedidoIdpedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productoProductoid;
        hash += (int) pedidoIdpedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoPedidoPK)) {
            return false;
        }
        ProductoPedidoPK other = (ProductoPedidoPK) object;
        if (this.productoProductoid != other.productoProductoid) {
            return false;
        }
        if (this.pedidoIdpedido != other.pedidoIdpedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectods2.ProductoPedidoPK[ productoProductoid=" + productoProductoid + ", pedidoIdpedido=" + pedidoIdpedido + " ]";
    }
    
}
