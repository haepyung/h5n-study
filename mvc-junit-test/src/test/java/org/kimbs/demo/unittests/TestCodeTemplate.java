package org.kimbs.demo.unittests;

import org.junit.jupiter.api.*;

public class TestCodeTemplate {

    @BeforeAll
    public static void setUp() {
        // TODO: 제일 처음 초기화 되어야 하는 내용 작성 ( 최초 1번 실행 )
    }

    @BeforeEach
    public void init() {
        // TODO: 테스트 메서드가 시작될 때 마다 초기화 해야하는 내용 작성 ( @Test 갯수만큼 실행 )
    }

    @AfterEach
    public void tearDown() {
        // TODO: 테스트 메서드가 종료될 때 마다 해제되어야 하는 내용 작성 ( @Test 갯수만큼 실행 )
    }

    @AfterAll
    public static void cleanUp() {
        // TODO: 모든 테스트 메서드가 종료되고 난 후에 자원 해제 ( 마지막 1번 실행 )
    }

    /*
     * @Test : 해당 메서드를 테스트 메서드로 선언.
     * @DisplayName : 메서드 이름 대신에 보여질 이름.
     * public void test() {
     *   - 3A(Arrange, Act, Assert)
     *   - Arrange : 테스트에 필요한 데이터, 상태 등을 설정.
     *   - Act : 실제 테스트하고자 하는 기능을 실행.
     *   - Assert : 예상하는 상태 혹은 값 등과 실제 실행한 결과값을 비교.
     */
    @Test
    @DisplayName("template")
    public void test() {
        // arrange

        // act

        // assert
        Assertions.assertTrue(true);
    }
}
