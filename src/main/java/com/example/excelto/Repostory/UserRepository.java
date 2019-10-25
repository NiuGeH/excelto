package com.example.excelto.Repostory;

import com.example.excelto.Entity.userEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<userEntity>, PagingAndSortingRepository<userEntity, Long> {


    List<userEntity> findAllByUsernameAndPassword(String username,String password);

}