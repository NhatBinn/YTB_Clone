package com.work.youtobe.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.youtobe.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
