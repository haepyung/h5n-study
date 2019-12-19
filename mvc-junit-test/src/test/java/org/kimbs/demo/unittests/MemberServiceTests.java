package org.kimbs.demo.unittests;

import org.junit.jupiter.api.*;
import org.kimbs.demo.exception.MemberNotFoundException;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.MemberRepository;
import org.kimbs.demo.service.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MemberServiceTests {

    @Mock
    private MemberRepository repository;

    @InjectMocks
    private MemberService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회할 경우 예외 발생 테스트")
    public void testThrowMemberNotFoundException() {
        /* arrange */

        /* act */

        /* assert */
        assertThrows(MemberNotFoundException.class, () -> {
            service.findById(1234L);
        });
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 findAll() 테스트")
    public void testFindAllMember() {
        /* arrange */
        Member member = new Member();
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        List<Member> list = Arrays.asList(member);

        when(repository.findAll()).thenReturn(list);

        /* act */
        List<Member> actual = service.findAll();

        /* assert */
        assertEquals(1, actual.size());
        assertEquals("김병수", actual.get(0).getName());
        assertEquals("kbs0711@humuson.com", actual.get(0).getEmail());

        assertIterableEquals(list, actual);
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 findById(Long) 테스트")
    public void testFindById() {
        /* arrange */
        Member member = new Member();
        member.setId(1234L);
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        when(repository.findById(member.getId())).thenReturn(Optional.of(member));

        /* act */
        Member actual = service.findById(1234L);

        /* assert */
        assertEquals(member, actual);
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 updateById(Long, Member) 테스트")
    public void testUpdateById() {
        /* arrange */
        Member member = new Member();
        member.setId(1234L);
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        when(repository.saveAndFlush(member)).thenReturn(member);

        member.setEmail("modify@humuson.com");

        when(repository.findById(member.getId())).thenReturn(Optional.of(member));

        /* act */
        Member actual = service.updateById(member.getId(), member);

        /* assert */
        assertEquals(member, actual);
        assertEquals(member.getScore(), actual.getScore());
    }
}
