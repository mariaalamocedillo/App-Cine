package es.mariaac.cinema.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer
{

  @PersistenceUnit
  private EntityManagerFactory emf;

  @Produces // you can also make this @RequestScoped
  public EntityManager create() {return emf.createEntityManager();}

  public void close(@Disposes EntityManager em)
  {
    if (em.isOpen())
    {
      em.close();
    }
  }
}
