package hellojpa;


import hellojpa.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 복합키, 식별관계, 비식별관계 테스트
 */
public class IdentifyingMappingTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {

//            UsingIdClass(em);

            Parent parent = new Parent();
            ParentId parentId = new ParentId("myId1", "myId2"); // parentId를 직접 생성해서 사용
            parent.setId(parentId);
            parent.setName("ParentName");
            em.persist(parent);
            System.out.println("==========================================");

            parentId = new ParentId("myId1", "myId2");
            Parent findParent = em.find(Parent.class, parentId); // 조회 코드도 식별자 클래스 parentId를 직접 사용
            System.out.println("findParent.getName() = " + findParent.getName());


            Query queryForEmbeddedId = em.createQuery("select p.id.id1, p.id.id2 from Parent p"); // @EmbeddedId
//            Query queryForIdClass = em.createQuery("select p.id1, p.id2 from Parent p");// @IdClass


            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

//    private static void UsingIdClass(EntityManager em) {
//        System.out.println("========== 복합키 : @IdClass 사용 ");
//        Parent parent = new Parent();
//        parent.setId1("myId1");
//        parent.setId2("myId2");
//        parent.setName("parentName");
//        em.persist(parent);
//
//        em.flush();
//        em.clear();
//
//        System.out.println("=========================================");
//
//        ParentId parentId = new ParentId("myId1", "myId2");
//        Parent findParent = em.find(Parent.class, parentId);
//        System.out.println("findParent = " + findParent.getName());
//
//        System.out.println("=========================================");
//        Child child = new Child();
//        child.setId("childId1");
//        child.setName("I'm child");
//        child.setParent(findParent);
//        em.persist(child);
//
//        System.out.println("child.getParent().getName() = " + child.getParent().getName());
//    }
}