package com.example.demo;

import com.example.demo.persistence.entities.Chat;
import com.example.demo.persistence.entities.Message;
import com.example.demo.persistence.entities.User;
import com.example.demo.persistence.repository.ChatRepository;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.services.Prerequisites;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAutoConfiguration
class DemoApplicationTests {

	@Autowired
	private Prerequisites prerequisites;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	public void before() {
		prerequisites.prepareDB();
	}

	@AfterAll
	public void after() {
	}

	@Test
	@Transactional
	void contextLoads() {
		List<Chat> chats = new ArrayList<>();

		chatRepository.findAll().stream()
				.forEach(chat -> {
					var ids = chat.getUsers().stream()
							.map(user -> user.getId())
							.collect(Collectors.toList());

					Optional<Chat> foundChat = chatRepository.getChat(ids, ids.size());
					Set<Message> elements = new HashSet<>();

					if(foundChat.isPresent() && foundChat.get().getMessages().stream()
							.filter(elem -> !elements.add(elem))
							.toList().size() > 0){
						fail("There are duplicated messages");
					}
				});
	}

}
