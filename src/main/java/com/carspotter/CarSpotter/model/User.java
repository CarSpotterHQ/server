package com.carspotter.CarSpotter.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="tp_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 순차적으로 생성되도록 설정
    private Long id;
    private String name;
    private String email;

}
