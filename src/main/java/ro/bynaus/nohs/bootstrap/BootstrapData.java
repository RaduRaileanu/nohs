package ro.bynaus.nohs.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.repositories.ServiceRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner{
    private final ServiceRepository serviceRepository;

    @Override
    public void run(String... args) throws Exception {
        long count = serviceRepository.count();
        System.out.println(count);
    }
}
