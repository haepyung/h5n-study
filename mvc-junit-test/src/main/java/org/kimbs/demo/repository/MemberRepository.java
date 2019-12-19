package org.kimbs.demo.repository;

import java.util.List;
import java.util.Optional;

import org.kimbs.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	 List<Member> findByName(String name);
	    List<Member> findAll();
	    <S extends Member> S saveAndFlush(S var1);
	    Optional<Member> findById(Long var1);
	    void deleteById(Long var1);
}
