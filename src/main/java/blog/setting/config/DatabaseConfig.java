package blog.setting.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class DatabaseConfig {
    @PersistenceContext
    //@PersistenceContext는 JPA(Java Persistence API)에서 영속성 컨텍스트를 주입받기 위한 어노테이션
    //영속성 컨텍스트는 엔티티(Entity) 객체와 데이터베이스 간의 관리. 이를 통해 엔티티 객체의 상태 변화를 추적하고,
    // 데이터베이스에 변경을 반영할 수 있습니다.
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactoryConfig() {
        return new JPAQueryFactory(em);
    }
}
