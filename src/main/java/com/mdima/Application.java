package com.mdima;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;

@EnableJSONDoc
@EnableCaching
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/*    @Bean
    CommandLineRunner init(final BookingRepository bookingRepository) {
        return args -> {
            final HashSet<FlightBooking> channels = new HashSet<>();
            channels.add(new FlightBooking("Moscow", 1, 99));
            channels.add(new FlightBooking("New York", 2, 250));
            channels.add(new FlightBooking("Florida", 2, 199));

            bookingRepository.save(channels);
        };
    }*/

    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager("bookings");
    }


}