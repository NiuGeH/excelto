package com.example.excelto.Repostory;

import com.example.excelto.Entity.DataTabEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTabRepository extends JpaSpecificationExecutor<DataTabEntity>, PagingAndSortingRepository<DataTabEntity, Long> {

    List<DataTabEntity> findAllByDateTime(String dateTime);
}
