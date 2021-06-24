package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.entity.SpeciesTree;
import ru.ekbtreeshelp.api.repository.SpeciesTreeRepository;

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
                "Сирень", "Спирея", "Тополь", "Черемуха", "Чубушник", "Яблоня", "Ясень")
                .forEach(speciesTitle -> {
                    if (!speciesTreeRepository.existsByTitle(speciesTitle)) {
                        speciesTreeRepository.save(new SpeciesTree(speciesTitle));
                    }
                });
    }

}
