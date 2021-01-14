package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Client;
import com.grokonez.jwtauthentication.model.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
//    Optional<Client> findByName(String name);
    Optional<Client> findById(long id);
    Client findByName(String name);


    @Query("select c from Client c where c.id > :id")
    List<Client> findWithOther(@Param("id") Long id);


}