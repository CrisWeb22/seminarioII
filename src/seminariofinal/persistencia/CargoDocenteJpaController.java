
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
import seminariofinal.logica.CargoDocente;
import seminariofinal.persistencia.exceptions.NonexistentEntityException;

public class CargoDocenteJpaController implements Serializable {

    public CargoDocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CargoDocenteJpaController(){
        emf = Persistence.createEntityManagerFactory("SeminarioFinalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargoDocente cargoDocente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cargoDocente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargoDocente cargoDocente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cargoDocente = em.merge(cargoDocente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cargoDocente.getIdCargoDocente();
                if (findCargoDocente(id) == null) {
                    throw new NonexistentEntityException("The cargoDocente with id " + id + " no longer exists.");
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
            CargoDocente cargoDocente;
            try {
                cargoDocente = em.getReference(CargoDocente.class, id);
                cargoDocente.getIdCargoDocente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargoDocente with id " + id + " no longer exists.", enfe);
            }
            em.remove(cargoDocente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargoDocente> findCargoDocenteEntities() {
        return findCargoDocenteEntities(true, -1, -1);
    }

    public List<CargoDocente> findCargoDocenteEntities(int maxResults, int firstResult) {
        return findCargoDocenteEntities(false, maxResults, firstResult);
    }

    private List<CargoDocente> findCargoDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargoDocente.class));
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

    public CargoDocente findCargoDocente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargoDocente.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargoDocente> rt = cq.from(CargoDocente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
