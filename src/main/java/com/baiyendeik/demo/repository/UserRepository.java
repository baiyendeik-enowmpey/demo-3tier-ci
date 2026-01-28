package com.baiyendeik.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baiyendeik.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
