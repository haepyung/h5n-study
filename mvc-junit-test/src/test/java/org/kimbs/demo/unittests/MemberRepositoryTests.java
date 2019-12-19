package org.kimbs.demo.unittests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("멤버 레파지토리에 아무 데이터가 없을 경우 테스트")
    public void testRepositoryIsEmpty() {
        /* arrange */

        /* act */
        List<Member> members = repository.findAll();

        /* assert */
        assertEquals(0, members.size());
    }

    @Test
    @DisplayName("멤버 레파지토리에 멤버 한 명 추가할 경우 테스트")
    public void testStoreAMember() {
        /* arrange */
        Member member = new Member();
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        /* act */
        Member actual = repository.saveAndFlush(member);

        /* assert = then*/
        assertEquals(member, actual);
        assertEquals(member.getId(), actual.getId());
        assertEquals(member.getEmail(), actual.getEmail());
        assertEquals(member.getName(), actual.getName());
        assertEquals(member.getScore(), actual.getScore());
    }

    @Test
    @DisplayName("멤버 레파지토리에서 모든 멤버 조회할 경우 테스트")
    public void testFindAllMembers() {
        /* arrange */
        Member member1 = new Member();
        member1.setScore(100);
        member1.setName("김병수");
        member1.setEmail("kbs0711@humuson.com");

        Member member2 = new Member();
        member2.setScore(77);
        member2.setName("홍길동");
        member2.setEmail("hong@hong.hong");

        entityManager.persist(member1);
        entityManager.persist(member2);

        /* act */
        List<Member> actual = repository.findAll();

        /* assert */
        assertAll("members",
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(member1, actual.get(0)),
                () -> assertEquals(member2, actual.get(1))
        );
    }
}