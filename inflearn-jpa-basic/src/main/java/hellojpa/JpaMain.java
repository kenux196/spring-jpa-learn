package hellojpa;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Slf4j
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Team team = new Team();
            team.setName("TEAM_SPRING");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("hello JAVA");
            member1.changeTeam(team);
            em.persist(member1);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, 1L);
            log.info("----------------------------------");
            List<Member> members = findTeam.getMembers();
            members.forEach(member -> log.info("m = " + member.getName()));
            log.info("----------------------------------");
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
