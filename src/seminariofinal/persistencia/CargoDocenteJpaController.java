
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
import seminariofinal.logica.Instituto;
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
            Instituto insti = cargoDocente.getInsti();
            if (insti != null) {
                insti = em.getReference(insti.getClass(), insti.getIdInsti());
                cargoDocente.setInsti(insti);
            }
            em.persist(cargoDocente);
            if (insti != null) {
                insti.getListaCargosDocentes().add(cargoDocente);
                insti = em.merge(insti);
            }
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
            CargoDocente persistentCargoDocente = em.find(CargoDocente.class, cargoDocente.getIdCargoDocente());
            Instituto instiOld = persistentCargoDocente.getInsti();
            Instituto instiNew = cargoDocente.getInsti();
            if (instiNew != null) {
                instiNew = em.getReference(instiNew.getClass(), instiNew.getIdInsti());
                cargoDocente.setInsti(instiNew);
            }
            cargoDocente = em.merge(cargoDocente);
            if (instiOld != null && !instiOld.equals(instiNew)) {
                instiOld.getListaCargosDocentes().remove(cargoDocente);
                instiOld = em.merge(instiOld);
            }
            if (instiNew != null && !instiNew.equals(instiOld)) {
                instiNew.getListaCargosDocentes().add(cargoDocente);
                instiNew = em.merge(instiNew);
            }
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
            Instituto insti = cargoDocente.getInsti();
            if (insti != null) {
                insti.getListaCargosDocentes().remove(cargoDocente);
                insti = em.merge(insti);
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
