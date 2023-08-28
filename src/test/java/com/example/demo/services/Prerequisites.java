package com.example.demo.services;

import com.example.demo.persistence.entities.Chat;
import com.example.demo.persistence.entities.Message;
import com.example.demo.persistence.entities.User;
import com.example.demo.persistence.repository.ChatRepository;
import com.example.demo.persistence.repository.MessageRepository;
import com.example.demo.persistence.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class Prerequisites {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    Random random = new Random();

    public void prepareDB(){
        List<User> users = prepareUsers();
        List<Chat> chats = prepareChats();
        List<Message> messages = prepareMessages();

        users = userRepository.saveAll(users);
        chats = chatRepository.saveAll(chats);

        link2(users, chats, messages);

        userRepository.saveAll(users);
        chatRepository.saveAll(chats);
        messageRepository.saveAll(messages);
    }

    public void turnDownDB(){
        userRepository.deleteAll();
        chatRepository.deleteAll();
        messageRepository.deleteAll();
    }

    public void link2(List<User> users, List<Chat> chats, List<Message> messages){
        int k = 0;

        for (int j = 0; j < chats.size(); j++) {
            for(int i = k; i < users.size(); i++) {
                users.get(i).getChats().add(chats.get(j));
                chats.get(j).getUsers().add(users.get(i));
            }
            k++;
        }

        List<UserChatComb> userChatCombs = users.stream()
                .flatMap(user ->
                        user.getChats().stream()
                                .map(chat ->  UserChatComb.builder()
                                        .user(user)
                                        .chat(chat)
                                        .build()))
                .collect(Collectors.toList());


        messages.stream().forEach(message ->  {

            UserChatComb comb = getRandomElementFromList(userChatCombs);

            comb.user.getMessagesSent().add(message);
            comb.chat.getMessages().add(message);
            message.setChat(comb.chat);
            message.setSender(comb.user);

            comb = getRandomElementFromList(userChatCombs);
            comb.user.getMessagesSent().add(message);
            comb.chat.getMessages().add(message);
            message.setChat(comb.chat);
            message.setSender(comb.user);
        });
    }

    public <T> int getRandomNuberWithinList(List<T> list){
        return random.nextInt(list.size());
    }

    public <T> T getRandomElementFromList(List<T> list) {
        return list.get(random.ints(0, list.size() - 1)
                .findFirst()
                .getAsInt());
    }

    public List<User> prepareUsers(){
        return LongStream.range(1,11).boxed()
            .map(this::createUser)
            .collect(Collectors.toList());
    }

    public User createUser(Long id){
        return User.builder()
                .id(id)
                .chats(new HashSet<>())
                .messagesSent(new ArrayList<>())
                .build();
    }

    public List<Chat> prepareChats(){
        return LongStream.range(1,3).boxed()
                .map(this::createChat)
                .collect(Collectors.toList());
    }

    public Chat createChat(Long id){
        return Chat.builder()
                .id(id)
                .users(new HashSet<>())
                .messages(new ArrayList<>())
                .build();
    }

    public List<Message> prepareMessages(){
        return LongStream.range(1,21).boxed()
                .map(this::createMessage)
                .collect(Collectors.toList());
    }

    public Message createMessage(Long id){
        return Message.builder()
                .id(id)
                .build();
    }
}
