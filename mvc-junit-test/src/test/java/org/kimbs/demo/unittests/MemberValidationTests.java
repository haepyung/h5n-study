package org.kimbs.demo.unittests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kimbs.demo.model.Member;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MemberValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("멤버 필드에 정상적인 값만 있을 경우 테스트")
    public void testNormalMember() {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setEmail("kbs0711@humuson.com");
        member.setName("kimbs");
        member.setScore(100);

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(0, violations.size());
    }

    @Test
    @DisplayName("멤버 score 필드에 Max 값보다 큰 값을 입력한 경우 테스트")
    public void shouldRaiseErrorWhenTheScoreGreaterThanMaxValue() {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setEmail("kbs0711@humuson.com");
        member.setName("kimbs");
        member.setScore(10000000);

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(1, violations.size());

        String actual = validator.validateProperty(member, "score").iterator().next().getMessage();
        String expected = "Should be score field value less than 100";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("멤버 email 필드에 잘못된 이메일 형식의 값을 입력한 경우 테스트")
    public void shouldRaiseErrorWhenYouEnteredWrongEmailFormat() {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setEmail("wrong email format");
        member.setName("wrong");
        member.setScore(0);

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(1, violations.size());

        String actual = validator.validateProperty(member, "email").iterator().next().getMessage();
        String expected = "Wrong email format";
        assertEquals(expected, actual);
    }
}
