package hellojpa;

import hellojpa.domain.Album;
import hellojpa.domain.Member;
import hellojpa.domain.Movie;
import hellojpa.domain.Team;
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
//            memberTest(em);

            inheritanceTest(em);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void inheritanceTest(EntityManager em) {
        Movie movie = new Movie();
        movie.setName("주라기 공원");
        movie.setActor("영구");
        movie.setDirector("땡칠이");
        movie.setPrice(10000);

        em.persist(movie);

        Album album = new Album();
        album.setName("자우림 8집");
        album.setPrice(9000);
        album.setArtist("자우림");
        em.persist(album);
    }

    private static void memberTest(EntityManager em) {
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
    }
}
