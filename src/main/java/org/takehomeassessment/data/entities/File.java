package org.takehomeassessment.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "videos")
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String fileId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    private String fileSize;
}

