package com.example.demo.services;

import com.example.demo.persistence.entities.Chat;
import com.example.demo.persistence.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class UserChatComb{
    User user;
    Chat chat;
}