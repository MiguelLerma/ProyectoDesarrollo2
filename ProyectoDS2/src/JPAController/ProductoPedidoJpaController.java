/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAController;

import JPAController.exceptions.NonexistentEntityException;
import JPAController.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import proyectods2.Pedido;
import proyectods2.Producto;
import proyectods2.ProductoPedido;
import proyectods2.ProductoPedidoPK;

/**
 *
 * @author User
 */
public class ProductoPedidoJpaController implements Serializable {

    public ProductoPedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoPedido productoPedido) throws PreexistingEntityException, Exception {
        if (productoPedido.getProductoPedidoPK() == null) {
            productoPedido.setProductoPedidoPK(new ProductoPedidoPK());
        }
        productoPedido.getProductoPedidoPK().setProductoProductoid(productoPedido.getProducto().getProductoid());
        productoPedido.getProductoPedidoPK().setPedidoIdpedido(productoPedido.getPedido().getIdpedido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = productoPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getIdpedido());
                productoPedido.setPedido(pedido);
            }
            Producto producto = productoPedido.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getProductoid());
                productoPedido.setProducto(producto);
            }
            em.persist(productoPedido);
            if (pedido != null) {
                pedido.getProductoPedidoCollection().add(productoPedido);
                pedido = em.merge(pedido);
            }
            if (producto != null) {
                producto.getProductoPedidoCollection().add(productoPedido);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoPedido(productoPedido.getProductoPedidoPK()) != null) {
                throw new PreexistingEntityException("ProductoPedido " + productoPedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoPedido productoPedido) throws NonexistentEntityException, Exception {
        productoPedido.getProductoPedidoPK().setProductoProductoid(productoPedido.getProducto().getProductoid());
        productoPedido.getProductoPedidoPK().setPedidoIdpedido(productoPedido.getPedido().getIdpedido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoPedido persistentProductoPedido = em.find(ProductoPedido.class, productoPedido.getProductoPedidoPK());
            Pedido pedidoOld = persistentProductoPedido.getPedido();
            Pedido pedidoNew = productoPedido.getPedido();
            Producto productoOld = persistentProductoPedido.getProducto();
            Producto productoNew = productoPedido.getProducto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getIdpedido());
                productoPedido.setPedido(pedidoNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getProductoid());
                productoPedido.setProducto(productoNew);
            }
            productoPedido = em.merge(productoPedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getProductoPedidoCollection().remove(productoPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getProductoPedidoCollection().add(productoPedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoPedidoCollection().remove(productoPedido);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoPedidoCollection().add(productoPedido);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProductoPedidoPK id = productoPedido.getProductoPedidoPK();
                if (findProductoPedido(id) == null) {
                    throw new NonexistentEntityException("The productoPedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoPedidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoPedido productoPedido;
            try {
                productoPedido = em.getReference(ProductoPedido.class, id);
                productoPedido.getProductoPedidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = productoPedido.getPedido();
            if (pedido != null) {
                pedido.getProductoPedidoCollection().remove(productoPedido);
                pedido = em.merge(pedido);
            }
            Producto producto = productoPedido.getProducto();
            if (producto != null) {
                producto.getProductoPedidoCollection().remove(productoPedido);
                producto = em.merge(producto);
            }
            em.remove(productoPedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoPedido> findProductoPedidoEntities() {
        return findProductoPedidoEntities(true, -1, -1);
    }

    public List<ProductoPedido> findProductoPedidoEntities(int maxResults, int firstResult) {
        return findProductoPedidoEntities(false, maxResults, firstResult);
    }

    private List<ProductoPedido> findProductoPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoPedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProductoPedido findProductoPedido(ProductoPedidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoPedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoPedido> rt = cq.from(ProductoPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
