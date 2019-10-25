package com.example.excelto.Repostory;

import com.example.excelto.Entity.DataAllEntity;
import com.example.excelto.Entity.DataTabEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataAllRepository extends JpaSpecificationExecutor<DataAllEntity>, PagingAndSortingRepository<DataAllEntity, Long> {
    @Query("select d.id from DataAllEntity d where d.dataTabEntity =?1")
    List<Long> findAllByDataTabEntity(DataTabEntity dataTabEntity);
}
