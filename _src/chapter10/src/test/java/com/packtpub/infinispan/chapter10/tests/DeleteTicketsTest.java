package com.packtpub.infinispan.chapter10.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.junit.Test;

import com.packtpub.infinispan.chapter10.domain.Ticket;

public class DeleteTicketsTest {
  
  private static final String PERSISTENCE_UNIT_NAME_WITH_JTA = "ogm-jpa-without-jta";
  private static final Log logger = LoggerFactory.make();
  
  private EntityManagerFactory factory = null;
  
  @Test
  public void deleteAllInstances() {
    logger.info("Using Hibernate Search to find a Ticket and then remove it!");
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    Session session = em.unwrap(Session.class);
    Query qall = session.createQuery("from Ticket");
    List<Ticket> list = qall.list();
    for (Ticket ticket : list) {
      em.remove(ticket);
    }
    em.getTransaction().commit();
    em.close();
  }
  
  public synchronized EntityManager getEntityManager() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME_WITH_JTA);
    }
    return factory.createEntityManager();
  }
}
