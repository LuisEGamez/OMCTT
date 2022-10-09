package com.omctt.repository;


import com.omctt.entity.ToDo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

    boolean existsByTitle(String title);


    List<ToDo> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query(value = "SELECT * FROM omc.todos t LEFT JOIN omc.users u ON t.user_id=u.id WHERE u.username=?1", nativeQuery = true)
    List<ToDo> findByUsername(String username, Pageable pageable);
}
