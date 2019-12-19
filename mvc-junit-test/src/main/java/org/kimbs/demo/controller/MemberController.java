package org.kimbs.demo.controller;

import org.kimbs.demo.model.Member;
import org.kimbs.demo.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/member")
    public List<Member> members() {
        return memberService.findAll();
    }

    @GetMapping(value = "/member/{id}")
    public ResponseEntity<Member> selectById(@PathVariable Long id) {
        final Member selected = memberService.findById(id);

        return ResponseEntity.ok(selected);
    }

    @PostMapping(value = "/member")
    public ResponseEntity<Member> create(@RequestBody final Member member, final UriComponentsBuilder uriComponentsBuilder) {
        Member created = memberService.create(member);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/api/member/{id}").buildAndExpand(created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/member/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody final Member member) {
        Member updated = memberService.updateById(id, member);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(value = "/member/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteById(id);

        return ResponseEntity.accepted().build();
    }
}
