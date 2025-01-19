package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.InvitationTask;
import com.carspotter.CarSpotter.model.Task;
import com.carspotter.CarSpotter.model.TaskOrder;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.model.dto.TaskRequestDto;
import com.carspotter.CarSpotter.repository.InvitationRepository;
import com.carspotter.CarSpotter.repository.InvitationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final InvitationTaskRepository invitationTaskRepository;
    private final TaskService taskService;
    private final S3Uploader s3Uploader;

    public List<Invitation> getInvitations() {
        return invitationRepository.findAll();
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
        return invitationRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CATEGORY));
    }

    @Transactional
    public Invitation saveRewardCard(String uuid, Integer order, Optional<MultipartFile> multipartFile) throws IOException {

        Invitation invitation = findByUUID(uuid);
        TaskOrder taskOrder = TaskOrder.values()[order];

        InvitationTask invitationTask = invitation.getInvitationTasks()
                .stream()
                .filter(task -> task.getTaskOrder() == taskOrder)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NO_TASK));

        Task task = invitationTask.getTask();

        if (multipartFile.isPresent() && !multipartFile.get().isEmpty()) {
            String uploadImg = s3Uploader.upload(multipartFile.get());
            task.updateTask(uploadImg);
            return invitationRepository.save(invitation);

            //TODO : 목표 달성 이후에 Invitation 완료 처리
        } else {
            throw new IllegalArgumentException("MultipartFile is empty or not present.");
        }
    }

    @Transactional
    public Invitation saveInvitation(String uuid, Integer order, TaskRequestDto taskRequestDto) {
        Invitation invitation = findByUUID(uuid);
        Task task = taskService.saveTask(taskRequestDto);
        setTaskOrder(invitation, task, TaskOrder.values()[order]);
        return invitation;
    }
}
