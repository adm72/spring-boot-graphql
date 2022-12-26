package ru.jbru.springbootgraphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.jbru.springbootgraphql.model.PostEntity;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p where p.user.id = ?1")
    List<PostEntity> findByUserId(Long id);

}
