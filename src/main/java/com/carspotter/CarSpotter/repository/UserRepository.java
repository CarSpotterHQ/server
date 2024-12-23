package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
