package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.shared.cash.dto.CashMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;
    private final CashMemberRepository cashMemberRepository;

    public Wallet createCashMember(CashMemberDto member) {
        // getReferenceById : 영속성 컨텍스트에 있는 엔티티를 참조
        // 즉, Select 쿼리가 실행되지 않음
        CashMember _member = cashMemberRepository.getReferenceById(member.getId());

        return walletRepository.save(new Wallet(_member));
    }
}
