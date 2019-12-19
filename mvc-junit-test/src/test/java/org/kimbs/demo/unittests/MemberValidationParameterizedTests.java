package org.kimbs.demo.unittests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kimbs.demo.model.Member;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MemberValidationParameterizedTests {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("멤버 필드에 정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("normalMembers")
    void testNormalMembers(String name, String email, int score) {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setScore(score);
        member.setName(name);
        member.setEmail(email);

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(0, violations.size());
    }

    static Stream<Arguments> normalMembers() {
        return Stream.of(
                Arguments.of("Kim", "Kim@test.test", 100),
                Arguments.of("Lee", "Lee@test.test", 10),
                Arguments.of("Park", "Park@test.test", 77),
                Arguments.of("Choi", "Choi@test.test", 91),
                Arguments.of("Yang", "Yang@test.est", 11),
                Arguments.of("Test", "Test@test.est", 0)
        );
    }

    @DisplayName("멤버 필드에 비정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("abnormalMembers")
    void testAbnormalMembers(Member member) {
        /* arrange */

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertTrue(violations.size() > 0);
    }

    static Stream<Arguments> abnormalMembers() {
        return Stream.of(
                Arguments.of(new Member(1L, "Kim", "Kim", 111111)),
                Arguments.of(new Member(2L, "Lee", "Lee@test.test", -9999)),
                Arguments.of(new Member(3L, "Park", "Park", 9999)),
                Arguments.of(new Member(4L, "Choi", "Choi@test.test", 1234567890)),
                Arguments.of(new Member(5L, "Yang", "YangTest.est", -1234567890)),
                Arguments.of(new Member(6L, "Test", "Test@test,est", 777777777))
        );
    }

    @DisplayName("멤버 이메일 필드에 비정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("wrongEmailFormatMembers")
    void testWrongEmailFormatMembers(Member member) {
        /* arrange */

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(1, violations.size());

        String actual = validator.validateProperty(member, "email").iterator().next().getMessage();
        String expected = "Wrong email format";
        assertEquals(expected, actual);
    }

    static Stream<Arguments> wrongEmailFormatMembers() {
        return Stream.of(
                Arguments.of(new Member(1L, "Kim", "Kim", 100)),
                Arguments.of(new Member(2L, "Lee", "Lee@test,test", 100))
        );
    }
}
