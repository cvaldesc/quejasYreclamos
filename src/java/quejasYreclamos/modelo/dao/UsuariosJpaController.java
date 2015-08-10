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
import quejasYreclamos.modelo.entidades.Respuestas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import quejasYreclamos.modelo.dao.exceptions.IllegalOrphanException;
import quejasYreclamos.modelo.dao.exceptions.NonexistentEntityException;
import quejasYreclamos.modelo.entidades.Peticiones;
import quejasYreclamos.modelo.entidades.Usuarios;

/**
 *
 * @author cvaldes
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getRespuestasCollection() == null) {
            usuarios.setRespuestasCollection(new ArrayList<Respuestas>());
        }
        if (usuarios.getPeticionesCollection() == null) {
            usuarios.setPeticionesCollection(new ArrayList<Peticiones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Respuestas> attachedRespuestasCollection = new ArrayList<Respuestas>();
            for (Respuestas respuestasCollectionRespuestasToAttach : usuarios.getRespuestasCollection()) {
                respuestasCollectionRespuestasToAttach = em.getReference(respuestasCollectionRespuestasToAttach.getClass(), respuestasCollectionRespuestasToAttach.getRespuestasId());
                attachedRespuestasCollection.add(respuestasCollectionRespuestasToAttach);
            }
            usuarios.setRespuestasCollection(attachedRespuestasCollection);
            Collection<Peticiones> attachedPeticionesCollection = new ArrayList<Peticiones>();
            for (Peticiones peticionesCollectionPeticionesToAttach : usuarios.getPeticionesCollection()) {
                peticionesCollectionPeticionesToAttach = em.getReference(peticionesCollectionPeticionesToAttach.getClass(), peticionesCollectionPeticionesToAttach.getPeticionesId());
                attachedPeticionesCollection.add(peticionesCollectionPeticionesToAttach);
            }
            usuarios.setPeticionesCollection(attachedPeticionesCollection);
            em.persist(usuarios);
            for (Respuestas respuestasCollectionRespuestas : usuarios.getRespuestasCollection()) {
                Usuarios oldUsuariosidOfRespuestasCollectionRespuestas = respuestasCollectionRespuestas.getUsuariosid();
                respuestasCollectionRespuestas.setUsuariosid(usuarios);
                respuestasCollectionRespuestas = em.merge(respuestasCollectionRespuestas);
                if (oldUsuariosidOfRespuestasCollectionRespuestas != null) {
                    oldUsuariosidOfRespuestasCollectionRespuestas.getRespuestasCollection().remove(respuestasCollectionRespuestas);
                    oldUsuariosidOfRespuestasCollectionRespuestas = em.merge(oldUsuariosidOfRespuestasCollectionRespuestas);
                }
            }
            for (Peticiones peticionesCollectionPeticiones : usuarios.getPeticionesCollection()) {
                Usuarios oldUsuariosidOfPeticionesCollectionPeticiones = peticionesCollectionPeticiones.getUsuariosid();
                peticionesCollectionPeticiones.setUsuariosid(usuarios);
                peticionesCollectionPeticiones = em.merge(peticionesCollectionPeticiones);
                if (oldUsuariosidOfPeticionesCollectionPeticiones != null) {
                    oldUsuariosidOfPeticionesCollectionPeticiones.getPeticionesCollection().remove(peticionesCollectionPeticiones);
                    oldUsuariosidOfPeticionesCollectionPeticiones = em.merge(oldUsuariosidOfPeticionesCollectionPeticiones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getUsuariosId());
            Collection<Respuestas> respuestasCollectionOld = persistentUsuarios.getRespuestasCollection();
            Collection<Respuestas> respuestasCollectionNew = usuarios.getRespuestasCollection();
            Collection<Peticiones> peticionesCollectionOld = persistentUsuarios.getPeticionesCollection();
            Collection<Peticiones> peticionesCollectionNew = usuarios.getPeticionesCollection();
            respuestasCollectionOld = (respuestasCollectionOld == null) ? new LinkedList<Respuestas>() : respuestasCollectionOld ;
            respuestasCollectionNew = (respuestasCollectionNew == null) ? new LinkedList<Respuestas>() : respuestasCollectionNew ;
            peticionesCollectionOld =(peticionesCollectionOld == null) ? new LinkedList<Peticiones>() : peticionesCollectionOld ;
            peticionesCollectionNew = (peticionesCollectionNew == null) ? new LinkedList<Peticiones>() : peticionesCollectionNew ;
            
            List<String> illegalOrphanMessages = null;
            for (Respuestas respuestasCollectionOldRespuestas : respuestasCollectionOld) {
                if (!respuestasCollectionNew.contains(respuestasCollectionOldRespuestas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuestas " + respuestasCollectionOldRespuestas + " since its usuariosid field is not nullable.");
                }
            }
            for (Peticiones peticionesCollectionOldPeticiones : peticionesCollectionOld) {
                if (!peticionesCollectionNew.contains(peticionesCollectionOldPeticiones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Peticiones " + peticionesCollectionOldPeticiones + " since its usuariosid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Respuestas> attachedRespuestasCollectionNew = new ArrayList<Respuestas>();
            for (Respuestas respuestasCollectionNewRespuestasToAttach : respuestasCollectionNew) {
                respuestasCollectionNewRespuestasToAttach = em.getReference(respuestasCollectionNewRespuestasToAttach.getClass(), respuestasCollectionNewRespuestasToAttach.getRespuestasId());
                attachedRespuestasCollectionNew.add(respuestasCollectionNewRespuestasToAttach);
            }
            respuestasCollectionNew = attachedRespuestasCollectionNew;
            usuarios.setRespuestasCollection(respuestasCollectionNew);
            Collection<Peticiones> attachedPeticionesCollectionNew = new ArrayList<Peticiones>();
            for (Peticiones peticionesCollectionNewPeticionesToAttach : peticionesCollectionNew) {
                peticionesCollectionNewPeticionesToAttach = em.getReference(peticionesCollectionNewPeticionesToAttach.getClass(), peticionesCollectionNewPeticionesToAttach.getPeticionesId());
                attachedPeticionesCollectionNew.add(peticionesCollectionNewPeticionesToAttach);
            }
            peticionesCollectionNew = attachedPeticionesCollectionNew;
            usuarios.setPeticionesCollection(peticionesCollectionNew);
            usuarios = em.merge(usuarios);
            for (Respuestas respuestasCollectionNewRespuestas : respuestasCollectionNew) {
                if (!respuestasCollectionOld.contains(respuestasCollectionNewRespuestas)) {
                    Usuarios oldUsuariosidOfRespuestasCollectionNewRespuestas = respuestasCollectionNewRespuestas.getUsuariosid();
                    respuestasCollectionNewRespuestas.setUsuariosid(usuarios);
                    respuestasCollectionNewRespuestas = em.merge(respuestasCollectionNewRespuestas);
                    if (oldUsuariosidOfRespuestasCollectionNewRespuestas != null && !oldUsuariosidOfRespuestasCollectionNewRespuestas.equals(usuarios)) {
                        oldUsuariosidOfRespuestasCollectionNewRespuestas.getRespuestasCollection().remove(respuestasCollectionNewRespuestas);
                        oldUsuariosidOfRespuestasCollectionNewRespuestas = em.merge(oldUsuariosidOfRespuestasCollectionNewRespuestas);
                    }
                }
            }
            for (Peticiones peticionesCollectionNewPeticiones : peticionesCollectionNew) {
                if (!peticionesCollectionOld.contains(peticionesCollectionNewPeticiones)) {
                    Usuarios oldUsuariosidOfPeticionesCollectionNewPeticiones = peticionesCollectionNewPeticiones.getUsuariosid();
                    peticionesCollectionNewPeticiones.setUsuariosid(usuarios);
                    peticionesCollectionNewPeticiones = em.merge(peticionesCollectionNewPeticiones);
                    if (oldUsuariosidOfPeticionesCollectionNewPeticiones != null && !oldUsuariosidOfPeticionesCollectionNewPeticiones.equals(usuarios)) {
                        oldUsuariosidOfPeticionesCollectionNewPeticiones.getPeticionesCollection().remove(peticionesCollectionNewPeticiones);
                        oldUsuariosidOfPeticionesCollectionNewPeticiones = em.merge(oldUsuariosidOfPeticionesCollectionNewPeticiones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getUsuariosId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getUsuariosId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Respuestas> respuestasCollectionOrphanCheck = usuarios.getRespuestasCollection();
            for (Respuestas respuestasCollectionOrphanCheckRespuestas : respuestasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Respuestas " + respuestasCollectionOrphanCheckRespuestas + " in its respuestasCollection field has a non-nullable usuariosid field.");
            }
            Collection<Peticiones> peticionesCollectionOrphanCheck = usuarios.getPeticionesCollection();
            for (Peticiones peticionesCollectionOrphanCheckPeticiones : peticionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Peticiones " + peticionesCollectionOrphanCheckPeticiones + " in its peticionesCollection field has a non-nullable usuariosid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public int nextId(){
        String select = "SELECT MAX(u.usuariosId) FROM Usuarios u";
        Query consulta = getEntityManager().createQuery(select);
        Integer result = (Integer) consulta.getSingleResult();
        return result.intValue()+1;
    }
   /* public int GetUserFind(Integer iD){
        
    }*/
}
