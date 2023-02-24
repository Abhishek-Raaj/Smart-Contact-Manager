package com.smartmanager.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smartmanager.entities.User;



public interface Repository extends JpaRepository<User, Integer>
{
   public User findByEmail(String name);
}
     