package hello;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
  
  @PersistenceContext
  private EntityManager em;
  
  @Transactional
  public List<Person> getAll() {
    List<Person> result = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    return result;
  }
  
  @Transactional
  public void add(Person p) {
    em.persist(p);
  }
}
