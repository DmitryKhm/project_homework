package ru.khmelevskoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.khmelevskoy.entity.Account;
import ru.khmelevskoy.entity.Category;
import ru.khmelevskoy.entity.Transaction;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface ServiceTransactionRepository extends JpaRepository<Transaction, Long>,ServiceTransactionRepositoryCustom {

    @Query("select s from Transaction s where s.accountFrom =:account or s.accountTo =:account")
    List<Transaction> getAllByAccountFromOrAccountTo(Account account);

    @Query("select s from Transaction s where s.date =:date and s.categories =:category")
    List<Transaction> getAllByDateAndCategory(Date date, Category category);
}