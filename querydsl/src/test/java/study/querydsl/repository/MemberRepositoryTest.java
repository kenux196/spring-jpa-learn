package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Project;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    
    @BeforeEach
    public void init() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        Project project3 = new Project("project3");
        Project project4 = new Project("project4");
        project1.assignToTeam(teamA);
        project2.assignToTeam(teamA);
        project3.assignToTeam(teamB);
        project4.assignToTeam(teamB);
        em.persist(project1);
        em.persist(project2);
        em.persist(project3);
        em.persist(project4);

        em.flush();
        em.clear();
    }

    @Test
    public void basicTest() {
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> members = memberRepository.findAll();
        assertThat(members).containsExactly(member);

        List<Member> member1 = memberRepository.findByUsername("member1");
        assertThat(member1).containsExactly(member);
        assertThat(member1.get(0).getUsername()).isEqualTo(member.getUsername());
        assertThat(member1.get(0).getAge()).isEqualTo(member.getAge());
    }

    @Test
    public void searchTest() {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberRepository.search(condition);
        assertThat(result).extracting("username").containsExactly("member4");
    }
    
    @Test
//    @Commit
    public void lazyLoadingTest() {
        List<Member> findMembers = memberRepository.findAll();
        Member member = findMembers.get(0);
        System.out.println("============================================");
        System.out.println("member = " + member.getUsername());
        System.out.println("============================================");
        Team team = member.getTeam();
        System.out.println("team = " + team);
        System.out.println("team = " + team.getName());
        System.out.println("============================================");
        List<Project> projects = team.getProjects();
        System.out.println("projects count = " + projects.size());
        for (Project project : projects) {
            System.out.println("project = " + project.getName());
            System.out.println("project.getTeam().getName() = " + project.getTeam().getName());
        }
    }

    @Test
    public void fetchJoinTest() {
        List<Member> findMembers = memberRepository.findAllFetchJoin();
        Member member = findMembers.get(0);
        System.out.println("============================================");
        System.out.println("member = " + member.getUsername());
        System.out.println("============================================");
        Team team = member.getTeam();
        System.out.println("team = " + team);
        System.out.println("team = " + team.getName());
        System.out.println("============================================");
        List<Project> projects = team.getProjects();
        System.out.println("projects count = " + projects.size());
        for (Project project : projects) {
            System.out.println("project = " + project.getName());
            System.out.println("project.getTeam().getName() = " + project.getTeam().getName());
        }
    }
}