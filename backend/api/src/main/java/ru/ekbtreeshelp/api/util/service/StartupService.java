package ru.ekbtreeshelp.api.util.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class StartupService {

    private final SpeciesTreeRepository speciesTreeRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createDefaultSpecies();
    }

    private void createDefaultSpecies() {
        Set.of("Ель", "Кедр", "Лиственница", "Пихта", "Сосна", "Кипарисовик", "Можжевельник", "Туя", "Акация",
                "Береза", "Барбарис", "Боярышник", "Вишня", "Вяз", "Груша", "Дерен", "Дуб", "Жимолость", "Ива",
                "Ирга", "Калина", "Клен", "Кизильник", "Липа", "Ольха", "Орех", "Осина", "Рябина", "Слива",
                "Сирень", "Спирея", "Тополь", "Черемуха", "Чубушник", "Яблоня", "Ясень", "Другое",
                "Невозможно определить")
                .forEach(speciesTitle -> {
                    if (!speciesTreeRepository.existsByTitle(speciesTitle)) {
                        speciesTreeRepository.save(new SpeciesTree(speciesTitle));
                    }
                });
    }

}
