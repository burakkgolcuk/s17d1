package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workintech")
public class AnimalController {

    // properties -> Value örneği (görev gereği)
    @Value("${course.name}")
    private String courseName;

    @Value("${project.developer.fullname}")
    private String developerFullname;

    // Map<Integer, Animal>
    private final Map<Integer, Animal> animals = new ConcurrentHashMap<>();

    // İlk test "all animals" boş olmasın diye bir kayıt ekleyelim
    public AnimalController() {
        animals.put(100, new Animal(100, "default"));
    }

    // [GET] /workintech/animal  -> tüm value'ları List olarak döner
    @GetMapping("/animal")
    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }

    // [GET] /workintech/animal/{id} -> id varsa value döner
    @GetMapping("/animal/{id}")
    public Animal findById(@PathVariable Integer id) {
        return animals.get(id);
    }

    // [POST] /workintech/animal -> body: {id, name} ekler ve döner
    @PostMapping("/animal")
    public Animal save(@RequestBody Animal animal) {
        animals.put(animal.getId(), animal);
        return animal;
    }

    // [PUT] /workintech/animal/{id} -> body'den aldığı id ile günceller ve döner
    @PutMapping("/animal/{id}")
    public Animal update(@PathVariable Integer id, @RequestBody Animal updated) {
        // İstenirse path id'yi güvenceye almak için set edebiliriz:
        // updated.setId(id);
        animals.put(updated.getId(), updated);
        return updated;
    }

    // [DELETE] /workintech/animal/{id} -> id'yi map'ten siler
    @DeleteMapping("/animal/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        animals.remove(id);
        return ResponseEntity.ok().build();
    }

    // (Opsiyonel) Properties’i görmek için küçük bir helper endpoint
    // @GetMapping("/info")
    // public Map<String, String> info() {
    //     return Map.of("course.name", courseName, "project.developer.fullname", developerFullname);
    // }
}
