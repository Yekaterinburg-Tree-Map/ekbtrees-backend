package ru.ekbtreeshelp.admin;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDataRepository extends PagingAndSortingRepository<UserData, Date> {
    List<UserData> findAllByDate(Date date, SpringDataWebProperties.Pageable pageable);
}
