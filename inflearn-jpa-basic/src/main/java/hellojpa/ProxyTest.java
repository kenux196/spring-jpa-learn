package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : ProxyTest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-04
 * @author skyun
 * @version 1.0.0
 **/
public class ProxyTest {

    public static void proxyInitSample(EntityManager em) {
        System.out.println("------ proxy 초기화 테스트 ------");
        Member member = new Member();
        member.setName("kenux");
        member.setCreateBy("kenux");
        member.setCreatedDate(LocalDateTime.now());
        em.persist(member);

        Long id = member.getId();

        em.flush();
        em.clear();
        System.out.println("------------------------");

        Member findMember = em.getReference(Member.class, id);
        System.out.println("findMember.getName() = " + findMember.getName());
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
//            proxyInitSample(em);

            lazyAndEagerTest(em);
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void lazyAndEagerTest(EntityManager em) {
        Team team = new Team();
        team.setName("teamA");
        em.persist(team);
        
        Member member = new Member();
        member.setName("member1");
        member.changeTeam(team);
        em.persist(member);
        
        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, 1L);
        System.out.println("==============================");
        Team findTeam = findMember.getTeam(); // proxy 객체
        System.out.println("findTeam.getClass().getName() = " + findTeam.getClass().getName());
        System.out.println("******************************");
        System.out.println("findTeam.getName() = " + findTeam.getName()); // 실제 사용하는 시점에 조회해서 프록시 초기화 (Lazy)


    }
}