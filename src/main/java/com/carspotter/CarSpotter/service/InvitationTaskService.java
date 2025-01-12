package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.InvitationTask;
import com.carspotter.CarSpotter.repository.InvitationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvitationTaskService {

    private final InvitationTaskRepository invitationTaskRepository;


    public List<InvitationTask> getAllInvitationTasks() {
        return invitationTaskRepository.findAll();
    }

    public InvitationTask getInvitationTaskById(Integer id) {
        return invitationTaskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("InvitationTask not found with id: " + id));
    }

    public InvitationTask saveInvitationTask(InvitationTask invitationTask) {
        return invitationTaskRepository.save(invitationTask);
    }

    public void deleteInvitationTask(Integer id) {
        invitationTaskRepository.deleteById(id);
    }
}
