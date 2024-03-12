
package seminariofinal.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import seminariofinal.logica.Instituto;
import seminariofinal.logica.Asignatura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import seminariofinal.logica.Docente;
import seminariofinal.persistencia.exceptions.NonexistentEntityException;


public class DocenteJpaController implements Serializable {

    public DocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public DocenteJpaController(){
        emf = Persistence.createEntityManagerFactory("SeminarioFinalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docente docente) {
        if (docente.getListaAsignaturas() == null) {
            docente.setListaAsignaturas(new ArrayList<Asignatura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instituto insti = docente.getInsti();
            if (insti != null) {
                insti = em.getReference(insti.getClass(), insti.getIdInsti());
                docente.setInsti(insti);
            }
            List<Asignatura> attachedListaAsignaturas = new ArrayList<Asignatura>();
            for (Asignatura listaAsignaturasAsignaturaToAttach : docente.getListaAsignaturas()) {
                listaAsignaturasAsignaturaToAttach = em.getReference(listaAsignaturasAsignaturaToAttach.getClass(), listaAsignaturasAsignaturaToAttach.getIdAsignatura());
                attachedListaAsignaturas.add(listaAsignaturasAsignaturaToAttach);
            }
            docente.setListaAsignaturas(attachedListaAsignaturas);
            em.persist(docente);
            if (insti != null) {
                insti.getListaDocentes().add(docente);
                insti = em.merge(insti);
            }
            for (Asignatura listaAsignaturasAsignatura : docente.getListaAsignaturas()) {
                Docente oldDocenteOfListaAsignaturasAsignatura = listaAsignaturasAsignatura.getDocente();
                listaAsignaturasAsignatura.setDocente(docente);
                listaAsignaturasAsignatura = em.merge(listaAsignaturasAsignatura);
                if (oldDocenteOfListaAsignaturasAsignatura != null) {
                    oldDocenteOfListaAsignaturasAsignatura.getListaAsignaturas().remove(listaAsignaturasAsignatura);
                    oldDocenteOfListaAsignaturasAsignatura = em.merge(oldDocenteOfListaAsignaturasAsignatura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente persistentDocente = em.find(Docente.class, docente.getLegajoDocente());
            Instituto instiOld = persistentDocente.getInsti();
            Instituto instiNew = docente.getInsti();
            List<Asignatura> listaAsignaturasOld = persistentDocente.getListaAsignaturas();
            List<Asignatura> listaAsignaturasNew = docente.getListaAsignaturas();
            if (instiNew != null) {
                instiNew = em.getReference(instiNew.getClass(), instiNew.getIdInsti());
                docente.setInsti(instiNew);
            }
            List<Asignatura> attachedListaAsignaturasNew = new ArrayList<Asignatura>();
            for (Asignatura listaAsignaturasNewAsignaturaToAttach : listaAsignaturasNew) {
                listaAsignaturasNewAsignaturaToAttach = em.getReference(listaAsignaturasNewAsignaturaToAttach.getClass(), listaAsignaturasNewAsignaturaToAttach.getIdAsignatura());
                attachedListaAsignaturasNew.add(listaAsignaturasNewAsignaturaToAttach);
            }
            listaAsignaturasNew = attachedListaAsignaturasNew;
            docente.setListaAsignaturas(listaAsignaturasNew);
            docente = em.merge(docente);
            if (instiOld != null && !instiOld.equals(instiNew)) {
                instiOld.getListaDocentes().remove(docente);
                instiOld = em.merge(instiOld);
            }
            if (instiNew != null && !instiNew.equals(instiOld)) {
                instiNew.getListaDocentes().add(docente);
                instiNew = em.merge(instiNew);
            }
            for (Asignatura listaAsignaturasOldAsignatura : listaAsignaturasOld) {
                if (!listaAsignaturasNew.contains(listaAsignaturasOldAsignatura)) {
                    listaAsignaturasOldAsignatura.setDocente(null);
                    listaAsignaturasOldAsignatura = em.merge(listaAsignaturasOldAsignatura);
                }
            }
            for (Asignatura listaAsignaturasNewAsignatura : listaAsignaturasNew) {
                if (!listaAsignaturasOld.contains(listaAsignaturasNewAsignatura)) {
                    Docente oldDocenteOfListaAsignaturasNewAsignatura = listaAsignaturasNewAsignatura.getDocente();
                    listaAsignaturasNewAsignatura.setDocente(docente);
                    listaAsignaturasNewAsignatura = em.merge(listaAsignaturasNewAsignatura);
                    if (oldDocenteOfListaAsignaturasNewAsignatura != null && !oldDocenteOfListaAsignaturasNewAsignatura.equals(docente)) {
                        oldDocenteOfListaAsignaturasNewAsignatura.getListaAsignaturas().remove(listaAsignaturasNewAsignatura);
                        oldDocenteOfListaAsignaturasNewAsignatura = em.merge(oldDocenteOfListaAsignaturasNewAsignatura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = docente.getLegajoDocente();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
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
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getLegajoDocente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            Instituto insti = docente.getInsti();
            if (insti != null) {
                insti.getListaDocentes().remove(docente);
                insti = em.merge(insti);
            }
            List<Asignatura> listaAsignaturas = docente.getListaAsignaturas();
            for (Asignatura listaAsignaturasAsignatura : listaAsignaturas) {
                listaAsignaturasAsignatura.setDocente(null);
                listaAsignaturasAsignatura = em.merge(listaAsignaturasAsignatura);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
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

    public Docente findDocente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
