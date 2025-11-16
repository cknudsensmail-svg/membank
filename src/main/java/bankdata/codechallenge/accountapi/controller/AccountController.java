package bankdata.codechallenge.accountapi.controller;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bankdata.codechallenge.accountapi.model.Account;
import bankdata.codechallenge.accountapi.model.PiggyBankAccount;
import bankdata.codechallenge.accountapi.model.PiggyBankAccountAPITransfer;
import bankdata.codechallenge.accountapi.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	private static final Logger LOG = LogManager.getLogger();
	private final AccountService accountService;
	
	@Value("${access.token}")
	private String secrectToken;
		
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/createaccount")
	public ResponseEntity<String> createAccount(@RequestHeader("X-Auth-Token") String token, @RequestBody PiggyBankAccount acc) {
		if(!secrectToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		try {
			accountService.create(acc);
			return new ResponseEntity<>("account created", HttpStatus.CREATED);
		} catch (Exception all) {
			LOG.warn("createAccount failure: {}", all.getMessage(), all);			
			return new ResponseEntity<>("Failure during createAccout: " + all.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/list")
    public ResponseEntity<Collection<Account>> getAllAccounts(@RequestHeader("X-Auth-Token") String token) {
		if(!secrectToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }
	
	@PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestHeader("X-Auth-Token") String token, @RequestBody PiggyBankAccountAPITransfer accountAPITransfer) {
		if(!secrectToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
				
		try {
			accountService.transfer(accountAPITransfer);
			return new ResponseEntity<>("transfer done", HttpStatus.CREATED);
		} catch (Exception all) {
			LOG.warn("transfer failure: {}", all.getMessage(), all);			
			return new ResponseEntity<>("Failure during transfer: " + all.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
}
