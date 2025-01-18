package com.carspotter.CarSpotter.model;

import com.carspotter.CarSpotter.model.dto.TaskRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tp_task")
@Getter
@DynamicUpdate
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 할일 ID

    @Column(nullable = false)
    private String name; // 할일 이름

    @Column(nullable = false)
    private String nickname; //닉네임

    @Column(name = "certification_photo")
    private String certificationPhoto; // 인증사진

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitationTask> invitationTasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id")
    private MajorCategory majorCategory; // 대분류 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id")
    private MinorCategory minorCategory; // 소분류 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty; // 벌칙 ID

    public static Task from(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        //생성데이터 채우기
        task.name = taskRequestDto.getName();
        task.nickname = taskRequestDto.getNickname();
        return task;
    }

    public void addTask(InvitationTask e) {
        Optional.ofNullable(this.invitationTasks).orElseGet(()
                -> this.invitationTasks = new ArrayList<>()).add(e);
    }

    public Task updateTask(String certificationPhoto){
        this.certificationPhoto = certificationPhoto;
        return this;
    }

}
