package co.udea.hero.api.controller;

import co.udea.hero.api.model.Hero;
import co.udea.hero.api.service.HeroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Busca un hero por su id",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> getHero(@PathVariable Integer id){
        log.info("Rest request buscar heroe por id: "+ id);
        return ResponseEntity.ok(heroService.getHero(id));
    }

    @GetMapping("")
    @ApiOperation(value = "Buscar todos los heroes",  response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroes encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<List<Hero>> getHeroes(){
        log.info("Rest request buscar heroes");
        return ResponseEntity.ok(heroService.getHeroes());
    }

    //searchHeroes (term) ) - @RequestMapping("buscar")
    @GetMapping("/?name={name}")
    @ApiOperation(value = "Buscar heroes por nombre", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroes encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> searchHeroes(@PathVariable String name){
        log.info("Rest request buscar heroe por nombre: "+ name);
        return ResponseEntity.ok(heroService.getHeroByName(name));
    }

    //updateHero (hero) ) - @RequestMapping("actualizar")
    @PutMapping()
    @ApiOperation(value = "Actualizar un heroe", response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe actualizado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> updateHero(@RequestBody Hero hero){
        log.info("Rest request actualizar heroe: "+ hero.getName());
        return ResponseEntity.ok(heroService.updateHero(hero));
    }


    //addHero (hero) ) - @RequestMapping("crear")
    @PostMapping()
    @ApiOperation(value = "Crear un heroe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe creado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> addHero(@RequestBody Hero hero){
        log.info("Rest request crear heroe: "+ hero.getName());
        return ResponseEntity.ok(heroService.addHero(hero));
    }


    //deleteHero (hero) - @RequestMapping("borrar")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un heroe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe eliminado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public void deleteHero(@PathVariable Integer id){
        heroService.deleteHero(id);
        log.info("Rest request eliminar heroe: "+ id);
    }
}
