package com.myshop.runner;

import com.myshop.dto.Product;
import com.myshop.dto.User;
import com.myshop.repository.ProductRepository;
import com.myshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<Product> products = new ArrayList<>();
    {
        products.add(new Product("Airpods Wireless Bluetooth Headphones","/images/airpods.jpg","Bluetooth technology lets you connect it with compatible devices wirelessly High-quality AAC audio offers immersive listening experience Built-in microphone allows you to take calls while working",
                "Apple","Electronics",89.99,10,4.5, 12));

        products.add(new Product("iPhone 11 Pro 256GB Memory","/images/phone.jpg","Introducing the iPhone 11 Pro. A transformative triple-camera system that adds tons of capability without complexity. An unprecedented leap in battery life",
                "Apple","Electronics",599.99,7,4.0, 8));

        products.add(new Product("Cannon EOS 80D DSLR Camera","/images/camera.jpg","Characterized by versatile imaging specs, the Canon EOS 80D further clarifies itself using a pair of robust focusing systems and an intuitive design",
                "Cannon","Electronics",929.99,5,3.0,12));

        products.add(new Product("Sony Playstation 4 Pro White Version","/images/playstation.jpg","The ultimate home entertainment center starts with PlayStation. Whether you are into gaming, HD movies, television, music",
                "Sony","Electronics",399.99,11,5.0,12));

        products.add(new Product("Logitech G-Series Gaming Mouse","/images/mouse.jpg","Get a better handle on your games with this Logitech LIGHTSYNC gaming mouse. The six programmable buttons allow customization for a smooth playing experience",
                "Logitech","Electronics",49.99,7,3.5,10));

        products.add(new Product("Amazon Echo Dot 3rd Generation","/images/alexa.jpg","Meet Echo Dot - Our most popular smart speaker with a fabric design. It is our most compact smart speaker that fits perfectly into small space",
                "Amazon","Electronics",29.99,0,4.0,12));

    }

    private List<User> users = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {

        users.add(new User("Admin",passwordEncoder.encode("12345"),"admin@test.com",true));
        users.add(new User("Mickey Mouse",passwordEncoder.encode("12345"),"mm@test.com",false));
        users.add(new User("Bugs Bunny",passwordEncoder.encode("12345"),"bb@test.com",false));

        userRepository.deleteAll();
        userRepository.insert(users);
        productRepository.deleteAll();
        products.forEach(p->p.setUser(users.get(0)));
        productRepository.insert(products);

    }
}
