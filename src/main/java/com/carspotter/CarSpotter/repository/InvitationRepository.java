package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByUuid(String uuid);

    List<Invitation> findByEndTimeBeforeAndStatus(LocalDateTime now, InvitationStatus invitationStatus);
}
