package com.engineersveedu.backend.config;

import com.engineersveedu.backend.model.CommunityPost;
import com.engineersveedu.backend.model.Contractor;
import com.engineersveedu.backend.model.User;
import com.engineersveedu.backend.repository.CommunityPostRepository;
import com.engineersveedu.backend.repository.ContractorRepository;
import com.engineersveedu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private CommunityPostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
        seedContractors();
        seedCommunityPosts();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            String defaultPassword = hashPassword("password");
            
            // Seed a client user
            User client = new User("client@example.com", defaultPassword, "client");
            userRepository.save(client);

            // Seed a builder user
            User builder = new User("builder@engineersveedu.com", defaultPassword, "builder");
            userRepository.save(builder);

            System.out.println("Seeded default users (client@example.com and builder@engineersveedu.com) with password: password");
        }
    }

    private void seedContractors() {
        if (contractorRepository.count() == 0) {
            Contractor c1 = new Contractor(
                "Rajesh Kumar", 
                "Residential", 
                4.8, 
                "Chennai", 
                12, 
                true, 
                "Experienced structural engineer specializing in modern residential villas and multi-family units in Chennai region."
            );
            Contractor c2 = new Contractor(
                "Priya Sharma", 
                "Commercial", 
                4.5, 
                "Coimbatore", 
                8, 
                true, 
                "Commercial buildout expert with a track record of delivering retail and office spaces on time."
            );
            Contractor c3 = new Contractor(
                "Vikram Singh", 
                "Renovation", 
                4.9, 
                "Madurai", 
                15, 
                false, 
                "Specialist in restoring heritage properties and modernizing kitchens/exteriors with custom structural designs."
            );
            contractorRepository.save(c1);
            contractorRepository.save(c2);
            contractorRepository.save(c3);

            System.out.println("Seeded initial contractors data");
        }
    }

    private void seedCommunityPosts() {
        if (postRepository.count() == 0) {
            CommunityPost p1 = new CommunityPost(
                "John D.", 
                "Just completed our dream home with Engineers Veedu! Highly recommended.", 
                12, 
                2, 
                "2 days ago", 
                false
            );
            CommunityPost p2 = new CommunityPost(
                "Sarah M.", 
                "Anyone have recommendations for kitchen tile suppliers in Coimbatore?", 
                5, 
                8, 
                "5 days ago", 
                false
            );
            CommunityPost p3 = new CommunityPost(
                "Engineers Veedu Team", 
                "New blog post: 'Top 5 Sustainable Building Materials for 2026'. Check it out on our website!", 
                45, 
                0, 
                "1 week ago", 
                true
            );
            postRepository.save(p1);
            postRepository.save(p2);
            postRepository.save(p3);

            System.out.println("Seeded community posts feed");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
