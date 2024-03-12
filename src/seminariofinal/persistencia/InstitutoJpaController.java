
package seminariofinal.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import seminariofinal.logica.Asignatura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import seminariofinal.logica.Docente;
import seminariofinal.logica.CargoDocente;
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
        if (instituto.getListaAsignaturas() == null) {
            instituto.setListaAsignaturas(new ArrayList<Asignatura>());
        }
        if (instituto.getListaDocentes() == null) {
            instituto.setListaDocentes(new ArrayList<Docente>());
        }
        if (instituto.getListaCargosDocentes() == null) {
            instituto.setListaCargosDocentes(new ArrayList<CargoDocente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignatura> attachedListaAsignaturas = new ArrayList<Asignatura>();
            for (Asignatura listaAsignaturasAsignaturaToAttach : instituto.getListaAsignaturas()) {
                listaAsignaturasAsignaturaToAttach = em.getReference(listaAsignaturasAsignaturaToAttach.getClass(), listaAsignaturasAsignaturaToAttach.getIdAsignatura());
                attachedListaAsignaturas.add(listaAsignaturasAsignaturaToAttach);
            }
            instituto.setListaAsignaturas(attachedListaAsignaturas);
            List<Docente> attachedListaDocentes = new ArrayList<Docente>();
            for (Docente listaDocentesDocenteToAttach : instituto.getListaDocentes()) {
                listaDocentesDocenteToAttach = em.getReference(listaDocentesDocenteToAttach.getClass(), listaDocentesDocenteToAttach.getLegajoDocente());
                attachedListaDocentes.add(listaDocentesDocenteToAttach);
            }
            instituto.setListaDocentes(attachedListaDocentes);
            List<CargoDocente> attachedListaCargosDocentes = new ArrayList<CargoDocente>();
            for (CargoDocente listaCargosDocentesCargoDocenteToAttach : instituto.getListaCargosDocentes()) {
                listaCargosDocentesCargoDocenteToAttach = em.getReference(listaCargosDocentesCargoDocenteToAttach.getClass(), listaCargosDocentesCargoDocenteToAttach.getIdCargoDocente());
                attachedListaCargosDocentes.add(listaCargosDocentesCargoDocenteToAttach);
            }
            instituto.setListaCargosDocentes(attachedListaCargosDocentes);
            em.persist(instituto);
            for (Asignatura listaAsignaturasAsignatura : instituto.getListaAsignaturas()) {
                Instituto oldInstiOfListaAsignaturasAsignatura = listaAsignaturasAsignatura.getInsti();
                listaAsignaturasAsignatura.setInsti(instituto);
                listaAsignaturasAsignatura = em.merge(listaAsignaturasAsignatura);
                if (oldInstiOfListaAsignaturasAsignatura != null) {
                    oldInstiOfListaAsignaturasAsignatura.getListaAsignaturas().remove(listaAsignaturasAsignatura);
                    oldInstiOfListaAsignaturasAsignatura = em.merge(oldInstiOfListaAsignaturasAsignatura);
                }
            }
            for (Docente listaDocentesDocente : instituto.getListaDocentes()) {
                Instituto oldInstiOfListaDocentesDocente = listaDocentesDocente.getInsti();
                listaDocentesDocente.setInsti(instituto);
                listaDocentesDocente = em.merge(listaDocentesDocente);
                if (oldInstiOfListaDocentesDocente != null) {
                    oldInstiOfListaDocentesDocente.getListaDocentes().remove(listaDocentesDocente);
                    oldInstiOfListaDocentesDocente = em.merge(oldInstiOfListaDocentesDocente);
                }
            }
            for (CargoDocente listaCargosDocentesCargoDocente : instituto.getListaCargosDocentes()) {
                Instituto oldInstiOfListaCargosDocentesCargoDocente = listaCargosDocentesCargoDocente.getInsti();
                listaCargosDocentesCargoDocente.setInsti(instituto);
                listaCargosDocentesCargoDocente = em.merge(listaCargosDocentesCargoDocente);
                if (oldInstiOfListaCargosDocentesCargoDocente != null) {
                    oldInstiOfListaCargosDocentesCargoDocente.getListaCargosDocentes().remove(listaCargosDocentesCargoDocente);
                    oldInstiOfListaCargosDocentesCargoDocente = em.merge(oldInstiOfListaCargosDocentesCargoDocente);
                }
            }
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
            Instituto persistentInstituto = em.find(Instituto.class, instituto.getIdInsti());
            List<Asignatura> listaAsignaturasOld = persistentInstituto.getListaAsignaturas();
            List<Asignatura> listaAsignaturasNew = instituto.getListaAsignaturas();
            List<Docente> listaDocentesOld = persistentInstituto.getListaDocentes();
            List<Docente> listaDocentesNew = instituto.getListaDocentes();
            List<CargoDocente> listaCargosDocentesOld = persistentInstituto.getListaCargosDocentes();
            List<CargoDocente> listaCargosDocentesNew = instituto.getListaCargosDocentes();
            List<Asignatura> attachedListaAsignaturasNew = new ArrayList<Asignatura>();
            for (Asignatura listaAsignaturasNewAsignaturaToAttach : listaAsignaturasNew) {
                listaAsignaturasNewAsignaturaToAttach = em.getReference(listaAsignaturasNewAsignaturaToAttach.getClass(), listaAsignaturasNewAsignaturaToAttach.getIdAsignatura());
                attachedListaAsignaturasNew.add(listaAsignaturasNewAsignaturaToAttach);
            }
            listaAsignaturasNew = attachedListaAsignaturasNew;
            instituto.setListaAsignaturas(listaAsignaturasNew);
            List<Docente> attachedListaDocentesNew = new ArrayList<Docente>();
            for (Docente listaDocentesNewDocenteToAttach : listaDocentesNew) {
                listaDocentesNewDocenteToAttach = em.getReference(listaDocentesNewDocenteToAttach.getClass(), listaDocentesNewDocenteToAttach.getLegajoDocente());
                attachedListaDocentesNew.add(listaDocentesNewDocenteToAttach);
            }
            listaDocentesNew = attachedListaDocentesNew;
            instituto.setListaDocentes(listaDocentesNew);
            List<CargoDocente> attachedListaCargosDocentesNew = new ArrayList<CargoDocente>();
            for (CargoDocente listaCargosDocentesNewCargoDocenteToAttach : listaCargosDocentesNew) {
                listaCargosDocentesNewCargoDocenteToAttach = em.getReference(listaCargosDocentesNewCargoDocenteToAttach.getClass(), listaCargosDocentesNewCargoDocenteToAttach.getIdCargoDocente());
                attachedListaCargosDocentesNew.add(listaCargosDocentesNewCargoDocenteToAttach);
            }
            listaCargosDocentesNew = attachedListaCargosDocentesNew;
            instituto.setListaCargosDocentes(listaCargosDocentesNew);
            instituto = em.merge(instituto);
            for (Asignatura listaAsignaturasOldAsignatura : listaAsignaturasOld) {
                if (!listaAsignaturasNew.contains(listaAsignaturasOldAsignatura)) {
                    listaAsignaturasOldAsignatura.setInsti(null);
                    listaAsignaturasOldAsignatura = em.merge(listaAsignaturasOldAsignatura);
                }
            }
            for (Asignatura listaAsignaturasNewAsignatura : listaAsignaturasNew) {
                if (!listaAsignaturasOld.contains(listaAsignaturasNewAsignatura)) {
                    Instituto oldInstiOfListaAsignaturasNewAsignatura = listaAsignaturasNewAsignatura.getInsti();
                    listaAsignaturasNewAsignatura.setInsti(instituto);
                    listaAsignaturasNewAsignatura = em.merge(listaAsignaturasNewAsignatura);
                    if (oldInstiOfListaAsignaturasNewAsignatura != null && !oldInstiOfListaAsignaturasNewAsignatura.equals(instituto)) {
                        oldInstiOfListaAsignaturasNewAsignatura.getListaAsignaturas().remove(listaAsignaturasNewAsignatura);
                        oldInstiOfListaAsignaturasNewAsignatura = em.merge(oldInstiOfListaAsignaturasNewAsignatura);
                    }
                }
            }
            for (Docente listaDocentesOldDocente : listaDocentesOld) {
                if (!listaDocentesNew.contains(listaDocentesOldDocente)) {
                    listaDocentesOldDocente.setInsti(null);
                    listaDocentesOldDocente = em.merge(listaDocentesOldDocente);
                }
            }
            for (Docente listaDocentesNewDocente : listaDocentesNew) {
                if (!listaDocentesOld.contains(listaDocentesNewDocente)) {
                    Instituto oldInstiOfListaDocentesNewDocente = listaDocentesNewDocente.getInsti();
                    listaDocentesNewDocente.setInsti(instituto);
                    listaDocentesNewDocente = em.merge(listaDocentesNewDocente);
                    if (oldInstiOfListaDocentesNewDocente != null && !oldInstiOfListaDocentesNewDocente.equals(instituto)) {
                        oldInstiOfListaDocentesNewDocente.getListaDocentes().remove(listaDocentesNewDocente);
                        oldInstiOfListaDocentesNewDocente = em.merge(oldInstiOfListaDocentesNewDocente);
                    }
                }
            }
            for (CargoDocente listaCargosDocentesOldCargoDocente : listaCargosDocentesOld) {
                if (!listaCargosDocentesNew.contains(listaCargosDocentesOldCargoDocente)) {
                    listaCargosDocentesOldCargoDocente.setInsti(null);
                    listaCargosDocentesOldCargoDocente = em.merge(listaCargosDocentesOldCargoDocente);
                }
            }
            for (CargoDocente listaCargosDocentesNewCargoDocente : listaCargosDocentesNew) {
                if (!listaCargosDocentesOld.contains(listaCargosDocentesNewCargoDocente)) {
                    Instituto oldInstiOfListaCargosDocentesNewCargoDocente = listaCargosDocentesNewCargoDocente.getInsti();
                    listaCargosDocentesNewCargoDocente.setInsti(instituto);
                    listaCargosDocentesNewCargoDocente = em.merge(listaCargosDocentesNewCargoDocente);
                    if (oldInstiOfListaCargosDocentesNewCargoDocente != null && !oldInstiOfListaCargosDocentesNewCargoDocente.equals(instituto)) {
                        oldInstiOfListaCargosDocentesNewCargoDocente.getListaCargosDocentes().remove(listaCargosDocentesNewCargoDocente);
                        oldInstiOfListaCargosDocentesNewCargoDocente = em.merge(oldInstiOfListaCargosDocentesNewCargoDocente);
                    }
                }
            }
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
            List<Asignatura> listaAsignaturas = instituto.getListaAsignaturas();
            for (Asignatura listaAsignaturasAsignatura : listaAsignaturas) {
                listaAsignaturasAsignatura.setInsti(null);
                listaAsignaturasAsignatura = em.merge(listaAsignaturasAsignatura);
            }
            List<Docente> listaDocentes = instituto.getListaDocentes();
            for (Docente listaDocentesDocente : listaDocentes) {
                listaDocentesDocente.setInsti(null);
                listaDocentesDocente = em.merge(listaDocentesDocente);
            }
            List<CargoDocente> listaCargosDocentes = instituto.getListaCargosDocentes();
            for (CargoDocente listaCargosDocentesCargoDocente : listaCargosDocentes) {
                listaCargosDocentesCargoDocente.setInsti(null);
                listaCargosDocentesCargoDocente = em.merge(listaCargosDocentesCargoDocente);
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
