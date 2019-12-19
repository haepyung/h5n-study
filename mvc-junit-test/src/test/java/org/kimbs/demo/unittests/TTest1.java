package org.kimbs.demo.unittests;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.exception.MemberNotFoundException;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.MemberRepository;
import org.kimbs.demo.service.MemberService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class TTest1 {

	@Mock
	private MemberRepository memberRepository;
	private MemberService service;
	
	
	@BeforeEach
	void setup() {
		service = new MemberService(memberRepository);
	}
	
	@Test
	void testCreateAMember()
	{
		// arrange
		Member member = new Member(1L,"park", "park@gmail.com", 99);
		
		
		Mockito.when(memberRepository.saveAndFlush(member)).thenReturn(member);
		
		// act
		Member act1 = service.create(member);
				
				
		// assert
		Assertions.assertEquals("park", act1.getName());
		
	}
	
	@Test
	void testFindAll()
	{
		// arrange
		Member member = new Member(1L,"park", "park@gmail.com", 99);
		Member member2 = new Member(1L,"lee", "lee@gmail.com", 88);
		
		
		Mockito.when(memberRepository.findAll()).thenReturn(java.util.Arrays.asList(member, member2));
		
		// act
		List<Member> db = service.findAll();
		
		// assert
		assertThat(db).hasSize(2).contains(member, member2);
		
		Mockito.verify(memberRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	void testFindByIdException()
	{
		// arrange
		Long id = 4444L;
		
		Mockito.when(memberRepository.findById(id)).thenReturn(Optional.empty());
		
		// act
		Assertions.assertThrows(MemberNotFoundException.class, () -> service.findById(id));
		
		// assert
	}
	
	
	
	
}