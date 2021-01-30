package hellojpa;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Slf4j
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("inflearnJpaBasic");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Team team = new Team();
            team.setName("TEAM_SPRING");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("hello JAVA");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("hello JPA");
            member2.setTeam(team);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("hello C#");
            em.persist(member3);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, 1L);
            log.info("name = " + findMember.getName() + " team = " + findMember.getTeam().getName());


            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
