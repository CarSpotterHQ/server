package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.Task;
import com.carspotter.CarSpotter.model.dto.TaskRequestDto;
import com.carspotter.CarSpotter.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    @Transactional
    public Task saveTask(TaskRequestDto taskRequestDto) {
        //페널티 소분류 대분류 채워서 생성
        TaskRequestDto mockData = new TaskRequestDto(null, null, null, "name", "nickname");
        return taskRepository.save(Task.from(mockData));
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
