package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			Client client = new Client();
			client.setLastName("Morel");
			client.setFirstName("Melba");
			client.setEmail("melba@mindhub.com");
			client.setPassword(passwordEncoder.encode("melba"));
			clientRepository.save(client);


			Client client1 = new Client();
			client1.setLastName("Gonzalez");
			client1.setFirstName("Luca");
			client1.setEmail("lgonzalez@mindhub.com");
			client1.setPassword(passwordEncoder.encode("gonzalez"));
			clientRepository.save(client1);

			Client clientAdm = new Client("admin", "admin","admin@gmail.com", passwordEncoder.encode("1111"));
			clientRepository.save(clientAdm);

			Account account1 = new Account("VIN001", LocalDate.now(),5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);

			Account account3 = new Account("VIN003", LocalDate.now(), 40000.00);
			client1.addAccount(account3);
			accountRepository.save(account3);

			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 659900.00);
			client1.addAccount(account4);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 20000.00, "Deposito n° 23435");
			account4.addTransactions(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 4000.00, "Deposito n° 23232");
			account4.addTransactions(transaction2);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 6000.00, "Deposito n° 23436");
			account4.addTransactions(transaction3);
			transactionRepository.save(transaction3);

			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -3000.00, "Nota de débito n°000013-2 ");
			account4.addTransactions(transaction5);
			transactionRepository.save(transaction5);

			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -8000.00, "Nota de débito n°000013-2 ");
			account1.addTransactions(transaction6);
			transactionRepository.save(transaction6);

			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -10000.00, "Nota de débito n°000013-2 ");
			account3.addTransactions(transaction7);
			transactionRepository.save(transaction7);

			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 10000.00, "Nota de Crédito n°003535-8 ");
			account3.addTransactions(transaction8);
			transactionRepository.save(transaction8);

			Transaction transaction9 = new Transaction(TransactionType.CREDIT, 34000.00, "Nota de Crédito n°023423-4 ");
			account3.addTransactions(transaction9);
			transactionRepository.save(transaction9);

			List<Integer> paymentsHipot= List.of(12,24,36,48,60);
			List<Integer> paymentsPers= List.of(6,12,24);
			List<Integer> paymentsAuto= List.of(6,12,24,36);

			Loan loanHip = new Loan("Hipotecario", 500000.00, paymentsHipot);
			loanRepository.save(loanHip);

			Loan loanPers = new Loan("Personal",10000.00,paymentsPers);
			loanRepository.save(loanPers);

			Loan loanAuto = new Loan("Automotriz", 300000.00, paymentsAuto);
			loanRepository.save(loanAuto);

			ClientLoan clientLoan1 = new ClientLoan(60, 40000.00, client,loanHip);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(12, 50000.00,client, loanPers);
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(24, 100000.00, client1, loanPers);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(36, 200000.00, client1, loanAuto);
			clientLoanRepository.save(clientLoan4);

			Card cardMelba = new Card(client.getFirstName()+" "+client.getLastName(),CardType.DEBIT,CardColor.GOLDEN,324234980,221,LocalDate.now(),LocalDate.now().plusYears(5));
			client.addCard(cardMelba);
			cardRepository.save(cardMelba);

			Card cardMelba1 = new Card(client.getFirstName()+" "+client.getLastName(),CardType.CREDIT,CardColor.TITANIUM,98798667,987,LocalDate.now(),LocalDate.now().plusYears(5));
			client.addCard(cardMelba1);
			cardRepository.save(cardMelba1);

		};
	}
}
