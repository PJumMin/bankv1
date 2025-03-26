package com.metacoding.bankv1.account;


import com.metacoding.bankv1.account.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO saveDTO, int userId) {
        accountRepository.save(saveDTO.getNumber(), saveDTO.getPassword(), saveDTO.getBalance(), userId);
    }

    public List<Account> 나의계좌목록(Integer userId) {
        return accountRepository.findAllUserId(userId);
    }

    @Transactional
    public void 계좌이체(AccountRequest.TransferDTO transferDTO, int userId) {
        // 1. 출금 계좌 조회, 없으면 RuntimeException
        Account withdrawAccount = accountRepository.findByNumber(transferDTO.getWithdrawNumber());
        if (withdrawAccount == null) throw new RuntimeException("출금계좌가 존재하지 않습니다.");

        // 2. 입금 계좌 조회, 없으면 RuntimeException
        Account depositAccount = accountRepository.findByNumber(transferDTO.getDepositNumber());
        if (withdrawAccount == null) throw new RuntimeException("입금계좌가 존재하지 않습니다.");

        // 3. 출금 계좌에 잔액 검사
        if (withdrawAccount.getBalance() < transferDTO.getAmount()) {
            throw new RuntimeException("출금 계좌의 잔액 : " + withdrawAccount.getBalance() + ", 이체하려는 금액 : " + transferDTO.getAmount());
        }

        // 4. 출금 비밀번호 확인해서 동일한지 확인
        if (!(withdrawAccount.getPassword().equals(transferDTO.getWithdrawPassword()))) {
            throw new RuntimeException("출금 계좌 비밀번호가 틀렸습니다.");
        }

        // 5. 출금 계좌의 주인이 맞는지 확인(로그인한 유저가)
        if (!(withdrawAccount.getUserId().equals(userId))) {
            throw new RuntimeException("출금계좌의 권한이 없습니다.");
        }
        // 6. Account Update 출금계좌
        int withdrawbalance = withdrawAccount.getBalance();
        withdrawbalance = withdrawbalance - transferDTO.getAmount();
        accountRepository.updateBynumber(withdrawbalance, withdrawAccount.getPassword(), withdrawAccount.getNumber());

        // 6. Account Update 입금계좌
        int depositbalance = depositAccount.getBalance();
        depositbalance = depositbalance + transferDTO.getAmount();
        accountRepository.updateBynumber(depositbalance, depositAccount.getPassword(), depositAccount.getNumber());

        // 7. History Save
        historyRepository.save(transferDTO.getWithdrawNumber(), transferDTO.getDepositNumber(), transferDTO.getAmount(), withdrawbalance);


    }
}
