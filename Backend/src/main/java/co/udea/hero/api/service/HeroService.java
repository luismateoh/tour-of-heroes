package co.udea.hero.api.service;

import co.udea.hero.api.exception.BusinessException;
import co.udea.hero.api.model.Hero;
import co.udea.hero.api.repository.HeroRepository;
import co.udea.hero.api.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HeroService {

    private final Logger log = LoggerFactory.getLogger(HeroService.class);

    private HeroRepository heroRepository;
    private Messages messages;


    public HeroService(HeroRepository heroRepository, Messages messages){
        this.heroRepository = heroRepository;
        this.messages = messages;
    }

    public Hero getHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID:"+id);
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }
        return optionalHero.get();
    }

    public List<Hero> getHeroes(){
        List<Hero> heroesList = heroRepository.findAll();
        if(heroesList.isEmpty()){
            log.info("No se encontraron heroes");
        }else{
            log.info("Se encontraron "+heroesList.size()+" heroes");
        }
        return heroesList;
    }

    public Hero addHero(Hero hero){
        Optional<Hero> optionalHero = Optional.ofNullable(heroRepository.findByName(hero.getName()));
        if(optionalHero.isPresent()){
            log.info("Ya se encuentra un heroe con nombre:"+hero.getName());
            throw new BusinessException(messages.get("exception.data_duplicate_name.hero"));
        }
        log.info("Se creo un heroe con nombre:"+hero.getName());

        return heroRepository.save(new Hero(null,hero.getName()));
    }

    public Hero updateHero(Hero hero){
        Optional<Hero> optionalHero = Optional.ofNullable(heroRepository.findByName(hero.getName()));
        if(optionalHero.isPresent()){
            log.info("Ya se encuentra un heroe con nombre:"+hero.getName());
            throw new BusinessException(messages.get("exception.data_duplicate_name.hero"));
        }
        optionalHero = heroRepository.findById(hero.getId());
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID:"+hero.getId());
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }

        log.info("Se actualizo el heroe con ID:"+hero.getId());
        return heroRepository.save(hero);
    }

    public void deleteHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID:"+id);
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }

        heroRepository.delete(optionalHero.get());
        log.info("Se elimino el heroe con ID:"+id);
    }

    public Hero getHeroByName(String name){
        Hero hero = heroRepository.findByName(name);
        if(hero == null){
            log.info("No se encuentra un heroe con nombre:"+name);
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }
        log.info("Se encontro un heroe con nombre:"+name);
        return hero;
    }

}
