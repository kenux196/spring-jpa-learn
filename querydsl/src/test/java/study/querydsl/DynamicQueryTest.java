package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;

@SpringBootTest
@Transactional
public class DynamicQueryTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

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
    }

    /**
     * 동적 쿼리 - BooleanBuilder
     */
    @Test
    public void dynamicQuery_BooleanBuilder() {
        String name = "member1";
        Integer age = 10;

        List<Member> result = searchMember1(name, age);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String name, Integer age) {
        BooleanBuilder builder = new BooleanBuilder();
        if (name != null) {
            builder.and(member.username.eq(name));
        }

        if (age != null) {
            builder.and(member.age.eq(age));
        }

        return queryFactory.selectFrom(member)
                .where(builder)
                .fetch();
    }

    /**
     * 동적 쿼리 - Where 다중 파라미터 사용
     */
    @Test
    public void dynamicQuery_where() {
        String name = "member1";
        Integer age = 10;

        List<Member> result = searchMember2(name, age);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String name, Integer age) {
        return queryFactory.selectFrom(member)
//                .where(useranameEq(name), ageEq(age))
                .where(allEq(name, age))
                .fetch();
    }

    private BooleanExpression useranameEq(String name) {
        return name != null ? member.username.eq(name) : null;
    }

    private BooleanExpression ageEq(Integer age) {
        return age != null ? member.age.eq(age) : null;
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return useranameEq(usernameCond).and(ageEq(ageCond));
    }

    /**
     * 수정, 삭제 벌크 연산
     * 쿼리 한번으로 대량 데이터 수정
     * 변경 감지 사용되는 것이 아님 - 변경 감지는 건 by 건으로 처리
     * 벌크 연산은 한번에 처리 - 단 DB에 직접 실행되는 것이어서, 영속성 컨텍스트의 데이터와 불일치
     * 따라서 벌크 연산 이후에는 영속성 컨텍스트를 clear(초기화) 할 필요가 있다.
     */
    @Test
    public void bulk() {
        long count = queryFactory.update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();
        System.out.println("count = " + count);

        long execute = queryFactory.update(member)
                .set(member.age, member.age.add(1))
                .execute();
        System.out.println("execute = " + execute);

        long execute1 = queryFactory.delete(member)
                .where(member.age.gt(18))
                .execute();
    }

    @Test
    public void bulkAfterInit() {
        long count = queryFactory.update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        List<Member> beforeResult = queryFactory.selectFrom(member).fetch();
        for (Member member1 : beforeResult) {
            System.out.println("member1 = " + member1);
        }

        // 벌크 연산 후 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        List<Member> result = queryFactory.selectFrom(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
    }
}
