package org.kimbs.demo.unittests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Member 테스트코드들")
@SelectClasses({MemberControllerTests.class, MemberServiceTests.class})
public class MemberTestSuites {
    // TODO: [Test Suite]: 특정 Layer 혹은 특정 테스트 메서드들만 모아서 실행시키고 싶을 때 사용
}
