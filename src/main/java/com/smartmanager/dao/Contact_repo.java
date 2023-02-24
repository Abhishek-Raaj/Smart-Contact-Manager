package com.smartmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmanager.entities.Contact;

public interface Contact_repo extends JpaRepository<Contact,Integer> {

	public Page<Contact> findByUser_id(int id,Pageable pageable);
}
