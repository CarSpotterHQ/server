package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.InvitationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationTaskRepository extends JpaRepository<InvitationTask, Long> {
    List<InvitationTask> findByInvitationId(Long invitationId);
}
