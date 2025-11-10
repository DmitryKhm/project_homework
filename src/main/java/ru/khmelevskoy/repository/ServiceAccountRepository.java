package ru.khmelevskoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.khmelevskoy.entity.Account;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServiceAccountRepository extends JpaRepository<Account, Long>,ServiceAccountRepositoryCustom {

    List<Account> getAllByUserId(Long userId);

    Account getAccountByIdAndUserId(Long accountId, Long userId);

    @Modifying
    @Query("delete from Account a where a.id =:accountId and a.user.id =:userId")
    void deleteAccountByIdAndUserId(Long accountId, Long userId);
}