package com.example.demo.persistence.entities;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private @Id @GeneratedValue Long id;

    @ManyToOne
    @JoinColumn(name="senderId", nullable=false)
    private User sender;

    @Column(name="value")
    private String value;

    @ManyToOne()
    @JoinColumn(name = "chat")
    private Chat chat;
}