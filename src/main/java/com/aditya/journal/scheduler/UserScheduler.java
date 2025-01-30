package com.aditya.journal.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aditya.journal.api.response.AppCache;
import com.aditya.journal.entities.JournalEntity;
import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.enums.Sentiment;
import com.aditya.journal.model.SentimentData;
import com.aditya.journal.repositry.JournalEntryRepo;
import com.aditya.journal.repositry.UserEntryRepo;
import com.aditya.journal.repositry.UserEntryRepoImpl;
import com.aditya.journal.service.EmailService;
import com.aditya.journal.service.SentimentAnalysisService;

@Component
public class UserScheduler {

    @Autowired
    private SentimentAnalysisService saService;

    @Autowired
    private UserEntryRepoImpl userRepo;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;
    
    @Scheduled(cron = "0 0/1 0 ? * *")
    public void fetchUsersAndSendSaMail(){
        List<UserEntity> users = userRepo.getUsersForSA();
        for(UserEntity user:users){
            List<JournalEntity> journals = user.getJournalEntries();
            List<Sentiment> sentiments = journals.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0 ) +1);
                }
            }

            Sentiment mostfreqSentiment = null;
            int maxCount = 0;

            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostfreqSentiment = entry.getKey();
                }
            }
            if(mostfreqSentiment!=null)
           {try {
               SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostfreqSentiment).build();
               kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
            
           } catch (Exception e) {
                emailService.sendEmail(user.getEmail(), "Sentiment for 7 days", mostfreqSentiment.toString());
           }
           }
        }
    }

    @Scheduled(cron = "0 0/30 * 1/1 * ?")
    public void clearCache(){
        appCache.init();
    }
}
