package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.MinorCategory;
import com.carspotter.CarSpotter.model.Penalty;
import com.carspotter.CarSpotter.model.Task;
import com.carspotter.CarSpotter.model.dto.TaskRequestDto;
import com.carspotter.CarSpotter.repository.MajorCategoryRepository;
import com.carspotter.CarSpotter.repository.MinorCategoryRepository;
import com.carspotter.CarSpotter.repository.PenaltyRepository;
import com.carspotter.CarSpotter.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final MajorCategoryRepository majorCategoryRepository;
    private final MinorCategoryRepository minorCategoryRepository;
    private final PenaltyRepository penaltyRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    @Transactional
    public Task saveTask(TaskRequestDto taskRequestDto) {

        // 패널티가 없다면 새롭게 penalty 생성
        Penalty penalty = Optional.ofNullable(taskRequestDto.getPenaltyId())
                .flatMap(penaltyRepository::findById)
                .orElseGet(() -> penaltyRepository.save(Penalty.builder()
                        .name(Optional.ofNullable(taskRequestDto.getPenaltyName())
                                .orElseThrow(() -> new CustomException(ErrorCode.NO_PENALTY)))
                        .isSecret(true)
                        .build()));

        if(taskRequestDto.getMajorCategoryId() == null || taskRequestDto.getMinorCategoryId() == null) {
            return taskRepository.save(Task
                    .builder()
                    .name(taskRequestDto.getName())
                    .nickname(taskRequestDto.getNickname())
                    .penalty(penalty)
                    .build());

        }else{
            MajorCategory majorCategory = majorCategoryRepository.findById(taskRequestDto.getMajorCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NO_CATEGORY));

            MinorCategory minorCategory = minorCategoryRepository.findById(taskRequestDto.getMinorCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NO_CATEGORY));

            return taskRepository.save(Task
                    .builder()
                    .name(taskRequestDto.getName())
                    .nickname(taskRequestDto.getNickname())
                    .majorCategory(majorCategory)
                    .minorCategory(minorCategory)
                    .penalty(penalty)
                    .build());
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
