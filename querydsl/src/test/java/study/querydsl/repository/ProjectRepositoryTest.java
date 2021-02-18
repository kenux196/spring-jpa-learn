package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Project;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class ProjectRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void basicTest() {
        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        Project project3 = new Project("project3");
        Project project4 = new Project("project4");
        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);
        projectRepository.save(project4);

        List<Project> findProject = projectRepository.findAll();

        Assertions.assertThat(findProject.get(1)).extracting("name").isEqualTo("project2");
    }

}