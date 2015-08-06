/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.modelo.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import quejasYreclamos.modelo.dao.exceptions.NonexistentEntityException;
import quejasYreclamos.modelo.entidades.Usuarios;
import quejasYreclamos.modelo.entidades.Peticiones;
import quejasYreclamos.modelo.entidades.Respuestas;

/**
 *
 * @author cvaldes
 */
public class RespuestasJpaController implements Serializable {

    public RespuestasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuestas respuestas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuariosid = respuestas.getUsuariosid();
            if (usuariosid != null) {
                usuariosid = em.getReference(usuariosid.getClass(), usuariosid.getUsuariosId());
                respuestas.setUsuariosid(usuariosid);
            }
            Peticiones peticionesid = respuestas.getPeticionesid();
            if (peticionesid != null) {
                peticionesid = em.getReference(peticionesid.getClass(), peticionesid.getPeticionesId());
                respuestas.setPeticionesid(peticionesid);
            }
            em.persist(respuestas);
            if (usuariosid != null) {
                usuariosid.getRespuestasCollection().add(respuestas);
                usuariosid = em.merge(usuariosid);
            }
            if (peticionesid != null) {
                peticionesid.getRespuestasCollection().add(respuestas);
                peticionesid = em.merge(peticionesid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuestas respuestas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuestas persistentRespuestas = em.find(Respuestas.class, respuestas.getRespuestasId());
            Usuarios usuariosidOld = persistentRespuestas.getUsuariosid();
            Usuarios usuariosidNew = respuestas.getUsuariosid();
            Peticiones peticionesidOld = persistentRespuestas.getPeticionesid();
            Peticiones peticionesidNew = respuestas.getPeticionesid();
            if (usuariosidNew != null) {
                usuariosidNew = em.getReference(usuariosidNew.getClass(), usuariosidNew.getUsuariosId());
                respuestas.setUsuariosid(usuariosidNew);
            }
            if (peticionesidNew != null) {
                peticionesidNew = em.getReference(peticionesidNew.getClass(), peticionesidNew.getPeticionesId());
                respuestas.setPeticionesid(peticionesidNew);
            }
            respuestas = em.merge(respuestas);
            if (usuariosidOld != null && !usuariosidOld.equals(usuariosidNew)) {
                usuariosidOld.getRespuestasCollection().remove(respuestas);
                usuariosidOld = em.merge(usuariosidOld);
            }
            if (usuariosidNew != null && !usuariosidNew.equals(usuariosidOld)) {
                usuariosidNew.getRespuestasCollection().add(respuestas);
                usuariosidNew = em.merge(usuariosidNew);
            }
            if (peticionesidOld != null && !peticionesidOld.equals(peticionesidNew)) {
                peticionesidOld.getRespuestasCollection().remove(respuestas);
                peticionesidOld = em.merge(peticionesidOld);
            }
            if (peticionesidNew != null && !peticionesidNew.equals(peticionesidOld)) {
                peticionesidNew.getRespuestasCollection().add(respuestas);
                peticionesidNew = em.merge(peticionesidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuestas.getRespuestasId();
                if (findRespuestas(id) == null) {
                    throw new NonexistentEntityException("The respuestas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuestas respuestas;
            try {
                respuestas = em.getReference(Respuestas.class, id);
                respuestas.getRespuestasId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestas with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuariosid = respuestas.getUsuariosid();
            if (usuariosid != null) {
                usuariosid.getRespuestasCollection().remove(respuestas);
                usuariosid = em.merge(usuariosid);
            }
            Peticiones peticionesid = respuestas.getPeticionesid();
            if (peticionesid != null) {
                peticionesid.getRespuestasCollection().remove(respuestas);
                peticionesid = em.merge(peticionesid);
            }
            em.remove(respuestas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Respuestas> findRespuestasEntities() {
        return findRespuestasEntities(true, -1, -1);
    }

    public List<Respuestas> findRespuestasEntities(int maxResults, int firstResult) {
        return findRespuestasEntities(false, maxResults, firstResult);
    }

    private List<Respuestas> findRespuestasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuestas.class));
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

    public Respuestas findRespuestas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuestas.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuestas> rt = cq.from(Respuestas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
