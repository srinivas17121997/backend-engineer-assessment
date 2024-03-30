package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final AccountRepository accountRepository;
  private final PaymentProvider paymentProvider;

  @Override
  @Transactional
  public Account saveAccount(Account account) {

    return accountRepository.save(account);
  }

  @Override
  public Account updateAccount(String accountId, Account account) {
    Optional<Account> response = accountRepository.findById(UUID.fromString(accountId));
    if (response.isPresent()) {
      Account update = response.get();

      if (StringUtils.hasLength(account.getFirstName())) {
        update.setFirstName(account.getFirstName());
      }
      if (StringUtils.hasLength(account.getLastName())) {
        update.setLastName(account.getLastName());
      }
      if (StringUtils.hasLength(account.getEmail())) {
        update.setEmail(account.getEmail());
      }
      return accountRepository.save(update);
    }
    return null;
  }

  @Override
  public Account createPaymentAccount(Account account) {
    String response =
        paymentProvider.createAccount(
            CreateAccount.builder()
                .email(account.getEmail())
                .userId(account.getId().toString())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build());
    account.setProviderId(response);
    return saveAccount(account);
  }
}
