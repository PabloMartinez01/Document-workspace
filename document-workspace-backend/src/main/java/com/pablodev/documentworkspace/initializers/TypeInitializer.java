package com.pablodev.documentworkspace.initializers;

import com.pablodev.documentworkspace.model.Type;
import com.pablodev.documentworkspace.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class TypeInitializer implements CommandLineRunner {

    private static final List<String> types = List.of("word", "slide", "cell", "pdf", "other");
    private final TypeRepository typeRepository;

    @Override
    @Transactional
    public void run(String... args) {
        types.stream()
                .map(Type::new)
                .forEach(this::insertTypeIfNotExists);
    }

    public void insertTypeIfNotExists(Type type) {
        typeRepository.findByName(type.getName())
                .orElseGet(() -> typeRepository.save(type));
    }

}
