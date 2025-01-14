package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    Invitation findByUuid(String uuid);
}
