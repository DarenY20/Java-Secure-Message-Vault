package com.securevault.dao;

import com.securevault.model.Message;

import javax.persistence.*;
import java.util.List;

public class MessageDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SecureVaultPU");


    public void save(Message message) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Message> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Message findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(Message message) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Message m = em.find(Message.class, message.getId());
            if (m != null) {
                em.remove(m);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
