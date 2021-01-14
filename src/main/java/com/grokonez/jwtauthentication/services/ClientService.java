package com.grokonez.jwtauthentication.services;

import com.grokonez.jwtauthentication.model.Client;
import com.grokonez.jwtauthentication.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by AndreyK on 24.12.2020.
 */
@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    public List<Client> findWithOther( Long id) {
        return clientRepository.findWithOther( id);
    }

}
