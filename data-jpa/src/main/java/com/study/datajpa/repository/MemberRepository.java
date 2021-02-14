package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import com.study.datajpa.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(name = "Member.findByUsername")  // 이 부분 막으면, namedquery 먼저 찾고, 없으면 쿼리 메소드 실행 // namedquery는 실무에서 거의 사용하지 않음.
    List<Member> findByUsername(@Param("username") String username);


    // 실무에서 많이 사용하는 방식. 리포지토리 메소드에 쿼리 정의
    // Application 로딩 시점에 파싱해서 문법 오류 검증 가능
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 단순히 값하나 조회하기
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 조회하기
    @Query("select new com.study.datajpa.dto.MemberDto(m.id, m.username, t.name)" +
            " from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // Collection 바인딩 - Collection 타입으로 in 절 지원 => 실무에서 자주 사용함.
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    /**
     * 페이징과 정렬
     */
//    Page<Member> findByUsername(String name, Pageable pageable); // count 쿼리 적용
//    Slice<Member> findByUsername(String name, Pageable pageable); // count 쿼리 없이
//    List<Member> findByUsername(String name, Pageable pageable); // count 쿼리 사용 안함.
//    List<Member> findByUsername(String name, Sort sort);

    Page<Member> findByAge(int age, Pageable pageable);

    // 페이징에서 카운트 쿼리 분리 : 실무에서 필요할 수 있음.
    @Query(value = "select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);

    /**
     * 벌크 연산
     * 벌크 연산 후, 영속성 컨텍스트에서 조회 같은 동작이 동일한 트랜잭션 내에서 해야 할 경우
     * 영속성 컨텍스트를 flush / clear 해줘야한다.
     * 그 이유는, 벌크 연산은 영속성 컨텍스트를 거치지 않고, 바로 DB 업데이트 쿼리가 나가기 때문에,
     * 영속성 컨텍스트와 DB 간의 데이터 불일치가 생기기 때문에, flush / clear가 필요하다.
     * spring data jpa 는 이 기능을 clearAutomatically 옵션으로 제공한다.
     * 그리고 spring data jpa에서 벌크 연산을 하려면, @Modifying 어노테이션 사용 필수.
     */
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    /**
     * @EntityGraph
     * 스프링 데이터 JPA에서 페치 조인은 편리하게 사용할 수 있도록 해 준다.
     * 간단한 조회에는 사용하면 편하지만, 복잡한 쿼리가 필요한 경우에는 결국 JPQL을 사용하게 된다.
     */
    // JPQL 사용 페치 조인
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // EntityGraph 사용
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL + Entity graph
    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    // 메서드 이름으로 쿼리에서 특히 편리
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);
}
