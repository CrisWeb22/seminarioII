
package seminariofinal.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import seminariofinal.logica.Instituto;
import seminariofinal.persistencia.exceptions.NonexistentEntityException;


public class InstitutoJpaController implements Serializable {

    public InstitutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public InstitutoJpaController(){
        emf = Persistence.createEntityManagerFactory("SeminarioFinalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Instituto instituto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(instituto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Instituto instituto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            instituto = em.merge(instituto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = instituto.getIdInsti();
                if (findInstituto(id) == null) {
                    throw new NonexistentEntityException("The instituto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituto instituto;
            try {
                instituto = em.getReference(Instituto.class, id);
                instituto.getIdInsti();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The instituto with id " + id + " no longer exists.", enfe);
            }
            em.remove(instituto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Instituto> findInstitutoEntities() {
        return findInstitutoEntities(true, -1, -1);
    }

    public List<Instituto> findInstitutoEntities(int maxResults, int firstResult) {
        return findInstitutoEntities(false, maxResults, firstResult);
    }

    private List<Instituto> findInstitutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Instituto.class));
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

    public Instituto findInstituto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Instituto.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstitutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Instituto> rt = cq.from(Instituto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
