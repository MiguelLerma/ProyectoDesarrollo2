/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAController;

import JPAController.exceptions.IllegalOrphanException;
import JPAController.exceptions.NonexistentEntityException;
import JPAController.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import proyectods2.Empleado;
import proyectods2.Mesa;
import proyectods2.ProductoPedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import proyectods2.Factura;
import proyectods2.Pedido;

/**
 *
 * @author User
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) throws PreexistingEntityException, Exception {
        if (pedido.getProductoPedidoCollection() == null) {
            pedido.setProductoPedidoCollection(new ArrayList<ProductoPedido>());
        }
        if (pedido.getFacturaCollection() == null) {
            pedido.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleadoIdempleado = pedido.getEmpleadoIdempleado();
            if (empleadoIdempleado != null) {
                empleadoIdempleado = em.getReference(empleadoIdempleado.getClass(), empleadoIdempleado.getIdempleado());
                pedido.setEmpleadoIdempleado(empleadoIdempleado);
            }
            Mesa mesaNumeromesa = pedido.getMesaNumeromesa();
            if (mesaNumeromesa != null) {
                mesaNumeromesa = em.getReference(mesaNumeromesa.getClass(), mesaNumeromesa.getNumeromesa());
                pedido.setMesaNumeromesa(mesaNumeromesa);
            }
            Collection<ProductoPedido> attachedProductoPedidoCollection = new ArrayList<ProductoPedido>();
            for (ProductoPedido productoPedidoCollectionProductoPedidoToAttach : pedido.getProductoPedidoCollection()) {
                productoPedidoCollectionProductoPedidoToAttach = em.getReference(productoPedidoCollectionProductoPedidoToAttach.getClass(), productoPedidoCollectionProductoPedidoToAttach.getProductoPedidoPK());
                attachedProductoPedidoCollection.add(productoPedidoCollectionProductoPedidoToAttach);
            }
            pedido.setProductoPedidoCollection(attachedProductoPedidoCollection);
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : pedido.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getFacturaid());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            pedido.setFacturaCollection(attachedFacturaCollection);
            em.persist(pedido);
            if (empleadoIdempleado != null) {
                empleadoIdempleado.getPedidoCollection().add(pedido);
                empleadoIdempleado = em.merge(empleadoIdempleado);
            }
            if (mesaNumeromesa != null) {
                mesaNumeromesa.getPedidoCollection().add(pedido);
                mesaNumeromesa = em.merge(mesaNumeromesa);
            }
            for (ProductoPedido productoPedidoCollectionProductoPedido : pedido.getProductoPedidoCollection()) {
                Pedido oldPedidoOfProductoPedidoCollectionProductoPedido = productoPedidoCollectionProductoPedido.getPedido();
                productoPedidoCollectionProductoPedido.setPedido(pedido);
                productoPedidoCollectionProductoPedido = em.merge(productoPedidoCollectionProductoPedido);
                if (oldPedidoOfProductoPedidoCollectionProductoPedido != null) {
                    oldPedidoOfProductoPedidoCollectionProductoPedido.getProductoPedidoCollection().remove(productoPedidoCollectionProductoPedido);
                    oldPedidoOfProductoPedidoCollectionProductoPedido = em.merge(oldPedidoOfProductoPedidoCollectionProductoPedido);
                }
            }
            for (Factura facturaCollectionFactura : pedido.getFacturaCollection()) {
                Pedido oldPedidoIdpedidoOfFacturaCollectionFactura = facturaCollectionFactura.getPedidoIdpedido();
                facturaCollectionFactura.setPedidoIdpedido(pedido);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldPedidoIdpedidoOfFacturaCollectionFactura != null) {
                    oldPedidoIdpedidoOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldPedidoIdpedidoOfFacturaCollectionFactura = em.merge(oldPedidoIdpedidoOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedido(pedido.getIdpedido()) != null) {
                throw new PreexistingEntityException("Pedido " + pedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdpedido());
            Empleado empleadoIdempleadoOld = persistentPedido.getEmpleadoIdempleado();
            Empleado empleadoIdempleadoNew = pedido.getEmpleadoIdempleado();
            Mesa mesaNumeromesaOld = persistentPedido.getMesaNumeromesa();
            Mesa mesaNumeromesaNew = pedido.getMesaNumeromesa();
            Collection<ProductoPedido> productoPedidoCollectionOld = persistentPedido.getProductoPedidoCollection();
            Collection<ProductoPedido> productoPedidoCollectionNew = pedido.getProductoPedidoCollection();
            Collection<Factura> facturaCollectionOld = persistentPedido.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = pedido.getFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoPedido productoPedidoCollectionOldProductoPedido : productoPedidoCollectionOld) {
                if (!productoPedidoCollectionNew.contains(productoPedidoCollectionOldProductoPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoPedido " + productoPedidoCollectionOldProductoPedido + " since its pedido field is not nullable.");
                }
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its pedidoIdpedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoIdempleadoNew != null) {
                empleadoIdempleadoNew = em.getReference(empleadoIdempleadoNew.getClass(), empleadoIdempleadoNew.getIdempleado());
                pedido.setEmpleadoIdempleado(empleadoIdempleadoNew);
            }
            if (mesaNumeromesaNew != null) {
                mesaNumeromesaNew = em.getReference(mesaNumeromesaNew.getClass(), mesaNumeromesaNew.getNumeromesa());
                pedido.setMesaNumeromesa(mesaNumeromesaNew);
            }
            Collection<ProductoPedido> attachedProductoPedidoCollectionNew = new ArrayList<ProductoPedido>();
            for (ProductoPedido productoPedidoCollectionNewProductoPedidoToAttach : productoPedidoCollectionNew) {
                productoPedidoCollectionNewProductoPedidoToAttach = em.getReference(productoPedidoCollectionNewProductoPedidoToAttach.getClass(), productoPedidoCollectionNewProductoPedidoToAttach.getProductoPedidoPK());
                attachedProductoPedidoCollectionNew.add(productoPedidoCollectionNewProductoPedidoToAttach);
            }
            productoPedidoCollectionNew = attachedProductoPedidoCollectionNew;
            pedido.setProductoPedidoCollection(productoPedidoCollectionNew);
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getFacturaid());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            pedido.setFacturaCollection(facturaCollectionNew);
            pedido = em.merge(pedido);
            if (empleadoIdempleadoOld != null && !empleadoIdempleadoOld.equals(empleadoIdempleadoNew)) {
                empleadoIdempleadoOld.getPedidoCollection().remove(pedido);
                empleadoIdempleadoOld = em.merge(empleadoIdempleadoOld);
            }
            if (empleadoIdempleadoNew != null && !empleadoIdempleadoNew.equals(empleadoIdempleadoOld)) {
                empleadoIdempleadoNew.getPedidoCollection().add(pedido);
                empleadoIdempleadoNew = em.merge(empleadoIdempleadoNew);
            }
            if (mesaNumeromesaOld != null && !mesaNumeromesaOld.equals(mesaNumeromesaNew)) {
                mesaNumeromesaOld.getPedidoCollection().remove(pedido);
                mesaNumeromesaOld = em.merge(mesaNumeromesaOld);
            }
            if (mesaNumeromesaNew != null && !mesaNumeromesaNew.equals(mesaNumeromesaOld)) {
                mesaNumeromesaNew.getPedidoCollection().add(pedido);
                mesaNumeromesaNew = em.merge(mesaNumeromesaNew);
            }
            for (ProductoPedido productoPedidoCollectionNewProductoPedido : productoPedidoCollectionNew) {
                if (!productoPedidoCollectionOld.contains(productoPedidoCollectionNewProductoPedido)) {
                    Pedido oldPedidoOfProductoPedidoCollectionNewProductoPedido = productoPedidoCollectionNewProductoPedido.getPedido();
                    productoPedidoCollectionNewProductoPedido.setPedido(pedido);
                    productoPedidoCollectionNewProductoPedido = em.merge(productoPedidoCollectionNewProductoPedido);
                    if (oldPedidoOfProductoPedidoCollectionNewProductoPedido != null && !oldPedidoOfProductoPedidoCollectionNewProductoPedido.equals(pedido)) {
                        oldPedidoOfProductoPedidoCollectionNewProductoPedido.getProductoPedidoCollection().remove(productoPedidoCollectionNewProductoPedido);
                        oldPedidoOfProductoPedidoCollectionNewProductoPedido = em.merge(oldPedidoOfProductoPedidoCollectionNewProductoPedido);
                    }
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Pedido oldPedidoIdpedidoOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getPedidoIdpedido();
                    facturaCollectionNewFactura.setPedidoIdpedido(pedido);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldPedidoIdpedidoOfFacturaCollectionNewFactura != null && !oldPedidoIdpedidoOfFacturaCollectionNewFactura.equals(pedido)) {
                        oldPedidoIdpedidoOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldPedidoIdpedidoOfFacturaCollectionNewFactura = em.merge(oldPedidoIdpedidoOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getIdpedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdpedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoPedido> productoPedidoCollectionOrphanCheck = pedido.getProductoPedidoCollection();
            for (ProductoPedido productoPedidoCollectionOrphanCheckProductoPedido : productoPedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the ProductoPedido " + productoPedidoCollectionOrphanCheckProductoPedido + " in its productoPedidoCollection field has a non-nullable pedido field.");
            }
            Collection<Factura> facturaCollectionOrphanCheck = pedido.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable pedidoIdpedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado empleadoIdempleado = pedido.getEmpleadoIdempleado();
            if (empleadoIdempleado != null) {
                empleadoIdempleado.getPedidoCollection().remove(pedido);
                empleadoIdempleado = em.merge(empleadoIdempleado);
            }
            Mesa mesaNumeromesa = pedido.getMesaNumeromesa();
            if (mesaNumeromesa != null) {
                mesaNumeromesa.getPedidoCollection().remove(pedido);
                mesaNumeromesa = em.merge(mesaNumeromesa);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
