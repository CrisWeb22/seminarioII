
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
import seminariofinal.logica.Asignatura;
import seminariofinal.logica.Docente;
import seminariofinal.logica.Instituto;
import seminariofinal.persistencia.exceptions.NonexistentEntityException;


public class AsignaturaJpaController implements Serializable {

    public AsignaturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public AsignaturaJpaController(){
        emf = Persistence.createEntityManagerFactory("SeminarioFinalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignatura asignatura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente = asignatura.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getLegajoDocente());
                asignatura.setDocente(docente);
            }
            Instituto insti = asignatura.getInsti();
            if (insti != null) {
                insti = em.getReference(insti.getClass(), insti.getIdInsti());
                asignatura.setInsti(insti);
            }
            em.persist(asignatura);
            if (docente != null) {
                docente.getListaAsignaturas().add(asignatura);
                docente = em.merge(docente);
            }
            if (insti != null) {
                insti.getListaAsignaturas().add(asignatura);
                insti = em.merge(insti);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignatura asignatura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignatura persistentAsignatura = em.find(Asignatura.class, asignatura.getIdAsignatura());
            Docente docenteOld = persistentAsignatura.getDocente();
            Docente docenteNew = asignatura.getDocente();
            Instituto instiOld = persistentAsignatura.getInsti();
            Instituto instiNew = asignatura.getInsti();
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getLegajoDocente());
                asignatura.setDocente(docenteNew);
            }
            if (instiNew != null) {
                instiNew = em.getReference(instiNew.getClass(), instiNew.getIdInsti());
                asignatura.setInsti(instiNew);
            }
            asignatura = em.merge(asignatura);
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                docenteOld.getListaAsignaturas().remove(asignatura);
                docenteOld = em.merge(docenteOld);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                docenteNew.getListaAsignaturas().add(asignatura);
                docenteNew = em.merge(docenteNew);
            }
            if (instiOld != null && !instiOld.equals(instiNew)) {
                instiOld.getListaAsignaturas().remove(asignatura);
                instiOld = em.merge(instiOld);
            }
            if (instiNew != null && !instiNew.equals(instiOld)) {
                instiNew.getListaAsignaturas().add(asignatura);
                instiNew = em.merge(instiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = asignatura.getIdAsignatura();
                if (findAsignatura(id) == null) {
                    throw new NonexistentEntityException("The asignatura with id " + id + " no longer exists.");
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
            Asignatura asignatura;
            try {
                asignatura = em.getReference(Asignatura.class, id);
                asignatura.getIdAsignatura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignatura with id " + id + " no longer exists.", enfe);
            }
            Docente docente = asignatura.getDocente();
            if (docente != null) {
                docente.getListaAsignaturas().remove(asignatura);
                docente = em.merge(docente);
            }
            Instituto insti = asignatura.getInsti();
            if (insti != null) {
                insti.getListaAsignaturas().remove(asignatura);
                insti = em.merge(insti);
            }
            em.remove(asignatura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignatura> findAsignaturaEntities() {
        return findAsignaturaEntities(true, -1, -1);
    }

    public List<Asignatura> findAsignaturaEntities(int maxResults, int firstResult) {
        return findAsignaturaEntities(false, maxResults, firstResult);
    }

    private List<Asignatura> findAsignaturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignatura.class));
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

    public Asignatura findAsignatura(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignatura.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignaturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignatura> rt = cq.from(Asignatura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
