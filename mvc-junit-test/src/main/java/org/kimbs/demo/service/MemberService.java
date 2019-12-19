package org.kimbs.demo.service;

import org.kimbs.demo.exception.MemberNotFoundException;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member create(Member member) {
        return memberRepository.saveAndFlush(member);
    }

    public Member updateById(Long id, Member member) {
        Member updated = this.findById(id);

        updated.setEmail(member.getEmail());
        updated.setName(member.getName());
        updated.setScore(member.getScore());

        return memberRepository.saveAndFlush(updated);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
    }

    public void deleteById(Long id) {
        Member deleted = this.findById(id);
        memberRepository.deleteById(deleted.getId());
    }
}
