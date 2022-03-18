package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements  MemberRepository {

    // 공유되는 변수일때는 ConcurrentHashMap사용
    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    // 실무에서는 공유문제로 어텀롱? 사용
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 이름이 같은것 하나라도 나올 시 member반환 없다면 optional로 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
