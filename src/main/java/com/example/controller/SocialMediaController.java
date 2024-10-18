package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService aService, MessageService mService) {
        accountService = aService;
        messageService = mService;
    }
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account newAccount) {
        if (newAccount.getUsername()=="" || newAccount.getPassword().length()<4)
            return ResponseEntity.status(400).body(null);
        Account registeredAccount = accountService.createAccount(newAccount);
        if (registeredAccount==null)
            return ResponseEntity.status(409).body(null);
        return ResponseEntity.ok().body(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.loginAccount(account);
        if (loginAccount==null || !account.getPassword().equals(loginAccount.getPassword()))
            return ResponseEntity.status(401).body(null);
        return ResponseEntity.ok().body(loginAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        String text = message.getMessageText();
        if (text=="" || text.length()>255 || 
            accountService.getAccountByID(message.getPostedBy())==null)
                return ResponseEntity.status(400).body(null);
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok().body(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int messageId) {
        return ResponseEntity.ok().body(messageService.getMessageByID(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable int messageId) {
        if (messageService.getMessageByID(messageId)!=null) {
            messageService.deleteMessageByID(messageId);
            return ResponseEntity.ok().body(1);
        }
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByID(@PathVariable int messageId, 
    @RequestBody Map<String,String> messageText) {
        String text = messageText.get("messageText");
        if (text=="" || text.length()>255)
            return ResponseEntity.status(400).body(null);
        Message newMessage = messageService.updateMessageByID(messageId, text);
        if (newMessage==null)
            return ResponseEntity.status(400).body(null);
        return ResponseEntity.ok().body(1);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok().body(messageService.getAllMessagesByUser(accountId));
    }
}
