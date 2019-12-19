package org.kimbs.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private StringBuilder defaultUrl = new StringBuilder();

    @BeforeEach
    public void init() {
        defaultUrl.append("http://localhost:").append(port).append("/api/member");
    }

    @AfterEach
    public void tearDown() {
        defaultUrl.setLength(0);
    }

    @Test
    @DisplayName("통합테스트 FindAll()")
    public void testFindAll() {
        /* arrange */

        /* act */
        ResponseEntity<List<Member>> members = this.restTemplate.exchange(
                defaultUrl.toString()
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<List<Member>>() {}
        );

        /* assert */
        assertEquals(HttpStatus.OK, members.getStatusCode());
        assertEquals(Collections.emptyList(), members.getBody());
    }

    @Test
    @DisplayName("통합테스트 createMember")
    public void testCreate() {
        /* arrange */
        Member member = new Member();
        member.setName("kbs");
        member.setEmail("kbs@humuson.com");
        member.setScore(100);

        /* act */
        ResponseEntity<Member> responseEntity = this.restTemplate.postForEntity(
                defaultUrl.toString()
                , member
                , Member.class
        );

        /* assert */
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertEquals("/api/member/1", responseEntity.getHeaders().getLocation().getPath()),
                () -> assertEquals(member.getName(), responseEntity.getBody().getName()),
                () -> assertEquals(member.getScore(), responseEntity.getBody().getScore()),
                () -> assertEquals(member.getEmail(), responseEntity.getBody().getEmail())
        );
    }

    @Test
    @DisplayName("통합테스트 NotFound")
    public void testNotFound() {
        /* arrange */

        /* act */

        /* assert */
        ResponseEntity<Void> deleteResponse = this.restTemplate.exchange (
                defaultUrl.append("/123456789").toString()
                , HttpMethod.DELETE
                , null
                , Void.class
        );

        /* assert */
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }
}