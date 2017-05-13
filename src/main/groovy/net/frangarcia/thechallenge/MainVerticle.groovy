package net.frangarcia.thechallenge;

import io.vertx.core.AbstractVerticle
import net.frangarcia.thechallenge.model.Challenge

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class MainVerticle extends AbstractVerticle {

  static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")

  @Override
  void start() {
    println "Starting..."

    vertx.deployVerticle("groovy:net.frangarcia.thechallenge.service.web.WebVerticle")
    vertx.deployVerticle("groovy:net.frangarcia.thechallenge.service.voting.VotingVerticle")

    EntityManager em = emf.createEntityManager()

    try {
      em.getTransaction().begin()
      em.persist(new Challenge(name:"Test 1", startDate: new Date(), endDate: new Date() + 1))
      em.getTransaction().commit()

    } catch (Exception e) {
      em.getTransaction().rollback()
      println e
    } finally {
      em.close()
    }
  }

}
