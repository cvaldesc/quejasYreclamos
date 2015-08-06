/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.modelo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import quejasYreclamos.modelo.entidades.Usuarios;
import quejasYreclamos.modelo.entidades.Respuestas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import quejasYreclamos.modelo.dao.exceptions.IllegalOrphanException;
import quejasYreclamos.modelo.dao.exceptions.NonexistentEntityException;
import quejasYreclamos.modelo.entidades.Peticiones;

/**
 *
 * @author cvaldes
 */
public class PeticionesJpaController implements Serializable {

    public PeticionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peticiones peticiones) {
        if (peticiones.getRespuestasCollection() == null) {
            peticiones.setRespuestasCollection(new ArrayList<Respuestas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuariosid = peticiones.getUsuariosid();
            if (usuariosid != null) {
                usuariosid = em.getReference(usuariosid.getClass(), usuariosid.getUsuariosId());
                peticiones.setUsuariosid(usuariosid);
            }
            Collection<Respuestas> attachedRespuestasCollection = new ArrayList<Respuestas>();
            for (Respuestas respuestasCollectionRespuestasToAttach : peticiones.getRespuestasCollection()) {
                respuestasCollectionRespuestasToAttach = em.getReference(respuestasCollectionRespuestasToAttach.getClass(), respuestasCollectionRespuestasToAttach.getRespuestasId());
                attachedRespuestasCollection.add(respuestasCollectionRespuestasToAttach);
            }
            peticiones.setRespuestasCollection(attachedRespuestasCollection);
            em.persist(peticiones);
            if (usuariosid != null) {
                usuariosid.getPeticionesCollection().add(peticiones);
                usuariosid = em.merge(usuariosid);
            }
            for (Respuestas respuestasCollectionRespuestas : peticiones.getRespuestasCollection()) {
                Peticiones oldPeticionesidOfRespuestasCollectionRespuestas = respuestasCollectionRespuestas.getPeticionesid();
                respuestasCollectionRespuestas.setPeticionesid(peticiones);
                respuestasCollectionRespuestas = em.merge(respuestasCollectionRespuestas);
                if (oldPeticionesidOfRespuestasCollectionRespuestas != null) {
                    oldPeticionesidOfRespuestasCollectionRespuestas.getRespuestasCollection().remove(respuestasCollectionRespuestas);
                    oldPeticionesidOfRespuestasCollectionRespuestas = em.merge(oldPeticionesidOfRespuestasCollectionRespuestas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peticiones peticiones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticiones persistentPeticiones = em.find(Peticiones.class, peticiones.getPeticionesId());
            Usuarios usuariosidOld = persistentPeticiones.getUsuariosid();
            Usuarios usuariosidNew = peticiones.getUsuariosid();
            Collection<Respuestas> respuestasCollectionOld = persistentPeticiones.getRespuestasCollection();
            Collection<Respuestas> respuestasCollectionNew = peticiones.getRespuestasCollection();
            List<String> illegalOrphanMessages = null;
            for (Respuestas respuestasCollectionOldRespuestas : respuestasCollectionOld) {
                if (!respuestasCollectionNew.contains(respuestasCollectionOldRespuestas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuestas " + respuestasCollectionOldRespuestas + " since its peticionesid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuariosidNew != null) {
                usuariosidNew = em.getReference(usuariosidNew.getClass(), usuariosidNew.getUsuariosId());
                peticiones.setUsuariosid(usuariosidNew);
            }
            Collection<Respuestas> attachedRespuestasCollectionNew = new ArrayList<Respuestas>();
            for (Respuestas respuestasCollectionNewRespuestasToAttach : respuestasCollectionNew) {
                respuestasCollectionNewRespuestasToAttach = em.getReference(respuestasCollectionNewRespuestasToAttach.getClass(), respuestasCollectionNewRespuestasToAttach.getRespuestasId());
                attachedRespuestasCollectionNew.add(respuestasCollectionNewRespuestasToAttach);
            }
            respuestasCollectionNew = attachedRespuestasCollectionNew;
            peticiones.setRespuestasCollection(respuestasCollectionNew);
            peticiones = em.merge(peticiones);
            if (usuariosidOld != null && !usuariosidOld.equals(usuariosidNew)) {
                usuariosidOld.getPeticionesCollection().remove(peticiones);
                usuariosidOld = em.merge(usuariosidOld);
            }
            if (usuariosidNew != null && !usuariosidNew.equals(usuariosidOld)) {
                usuariosidNew.getPeticionesCollection().add(peticiones);
                usuariosidNew = em.merge(usuariosidNew);
            }
            for (Respuestas respuestasCollectionNewRespuestas : respuestasCollectionNew) {
                if (!respuestasCollectionOld.contains(respuestasCollectionNewRespuestas)) {
                    Peticiones oldPeticionesidOfRespuestasCollectionNewRespuestas = respuestasCollectionNewRespuestas.getPeticionesid();
                    respuestasCollectionNewRespuestas.setPeticionesid(peticiones);
                    respuestasCollectionNewRespuestas = em.merge(respuestasCollectionNewRespuestas);
                    if (oldPeticionesidOfRespuestasCollectionNewRespuestas != null && !oldPeticionesidOfRespuestasCollectionNewRespuestas.equals(peticiones)) {
                        oldPeticionesidOfRespuestasCollectionNewRespuestas.getRespuestasCollection().remove(respuestasCollectionNewRespuestas);
                        oldPeticionesidOfRespuestasCollectionNewRespuestas = em.merge(oldPeticionesidOfRespuestasCollectionNewRespuestas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peticiones.getPeticionesId();
                if (findPeticiones(id) == null) {
                    throw new NonexistentEntityException("The peticiones with id " + id + " no longer exists.");
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
            Peticiones peticiones;
            try {
                peticiones = em.getReference(Peticiones.class, id);
                peticiones.getPeticionesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peticiones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Respuestas> respuestasCollectionOrphanCheck = peticiones.getRespuestasCollection();
            for (Respuestas respuestasCollectionOrphanCheckRespuestas : respuestasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Peticiones (" + peticiones + ") cannot be destroyed since the Respuestas " + respuestasCollectionOrphanCheckRespuestas + " in its respuestasCollection field has a non-nullable peticionesid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios usuariosid = peticiones.getUsuariosid();
            if (usuariosid != null) {
                usuariosid.getPeticionesCollection().remove(peticiones);
                usuariosid = em.merge(usuariosid);
            }
            em.remove(peticiones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peticiones> findPeticionesEntities() {
        return findPeticionesEntities(true, -1, -1);
    }

    public List<Peticiones> findPeticionesEntities(int maxResults, int firstResult) {
        return findPeticionesEntities(false, maxResults, firstResult);
    }

    private List<Peticiones> findPeticionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peticiones.class));
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

    public Peticiones findPeticiones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peticiones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeticionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peticiones> rt = cq.from(Peticiones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
