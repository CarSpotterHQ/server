package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.InvitationTask;
import com.carspotter.CarSpotter.model.Task;
import com.carspotter.CarSpotter.model.TaskOrder;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.repository.InvitationRepository;
import com.carspotter.CarSpotter.repository.InvitationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final InvitationTaskRepository invitationTaskRepository;
    private final TaskService taskService;


    public List<Invitation> getInvitations() {
        return invitationRepository.findAll();
    }

    public Invitation getInvitationById(Long id) {
        return invitationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invitation not found with id: " + id));
    }

    @Transactional
    public Invitation saveInvitation(InvitationRequestDto requestDto) {
        Invitation invitation = invitationRepository.save(Invitation.from(requestDto));
        Task task = taskService.saveTask(requestDto.getTaskRequestDto());
        setTaskOrder(invitation, task, TaskOrder.OWNER);
        return invitation;
    }

    @Transactional
    public void setTaskOrder(Invitation invitation, Task task, TaskOrder order) {
        InvitationTask e = invitationTaskRepository.save(new InvitationTask(invitation, task, order));
        invitation.addTask(e);
        task.addTask(e);
    }

    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }

    public Invitation findByUUID(String uuid) {
        return invitationRepository.findByUuid(uuid);
    }
}
