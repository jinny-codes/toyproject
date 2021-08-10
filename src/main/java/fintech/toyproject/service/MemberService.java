package fintech.toyproject.service;

import fintech.toyproject.dto.MemberDto;
import fintech.toyproject.entitiy.Member;
import fintech.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).
                orElseThrow(()->new UsernameNotFoundException(email));
    }

    public Long save(MemberDto memberDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        memberDto.setPassword(encoder.encode(memberDto.getPassword()));

        return memberRepository.save(Member.builder()
            .email(memberDto.getEmail())
            .password(memberDto.getPassword())
            .name(memberDto.getName())
            .auth(memberDto.getAuth())
            .balance(memberDto.getBalance()).build()).getSeq();
    }
}
