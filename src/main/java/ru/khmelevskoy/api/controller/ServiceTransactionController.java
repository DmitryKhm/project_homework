package ru.khmelevskoy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.khmelevskoy.api.converter.ServiceTransactionToResponseConverter;
import ru.khmelevskoy.api.json.ReportRequest;
import ru.khmelevskoy.api.json.TransactionRequest;
import ru.khmelevskoy.api.json.TransactionRequestAccId;
import ru.khmelevskoy.api.json.TransactionResponse;
import ru.khmelevskoy.dto.TransactionDTO;
import ru.khmelevskoy.dto.UserDTO;
import ru.khmelevskoy.securitry.CustomUserDetails;
import ru.khmelevskoy.service.ServiceTransaction;
import ru.khmelevskoy.service.ServiceUser;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ServiceTransactionController extends Controller {

    private final ServiceTransaction serviceTransaction;

    private final ServiceTransactionToResponseConverter converter;

    private final ServiceUser serviceUser;

    @GetMapping("/find-transactions")
    public ResponseEntity<List<TransactionResponse>> findAllTransaction(@RequestBody @Valid TransactionRequestAccId request
    ) {
        Long userId = currentUser().getId();
        List<TransactionDTO> transactions = serviceTransaction.getTransactions(userId, request.getAccId());
        return ok(transactions.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
    }

    @GetMapping("/report")
    public ResponseEntity<List<TransactionResponse>> getReport(@RequestBody @Valid ReportRequest request) throws ParseException {
        Long userId = currentUser().getId();
        String dateString = request.getTransactionDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(dateString);
        List<TransactionDTO> transactions = serviceTransaction.findReport(userId, request.getCategoryId(), date);
        return ok(transactions.stream()
                .map(converter::convert)
                .collect(Collectors.toList()));
    }

    @PostMapping("/insert-transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request) throws ParseException {
        String dateString = request.getTransactionDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(dateString);
        serviceTransaction.createTransaction(request.getToAccId(),
                request.getFromAccId(), request.getTransactionValue(), date);
        return status(HttpStatus.ACCEPTED).build();
    }

    private UserDTO currentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return serviceUser.getUserById(user.getId());
    }
}