package br.com.fiap.brinquedos.controller;

import br.com.fiap.brinquedos.model.Brinquedo;
import br.com.fiap.brinquedos.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

    @Autowired
    private BrinquedoRepository repository;

    @GetMapping
    public List<EntityModel<Brinquedo>> getAll() {
        List<Brinquedo> brinquedos = repository.findAll();
        return brinquedos.stream()
                .map(brinquedo -> EntityModel.of(brinquedo,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getById(brinquedo.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getAll()).withRel("brinquedos")))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> getById(@PathVariable Long id) {
        Optional<Brinquedo> brinquedo = repository.findById(id);
        if (brinquedo.isPresent()) {
            EntityModel<Brinquedo> model = EntityModel.of(brinquedo.get(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getById(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getAll()).withRel("brinquedos"));
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<Brinquedo>> create(@RequestBody Brinquedo brinquedo) {
        Brinquedo saved = repository.save(brinquedo);
        EntityModel<Brinquedo> model = EntityModel.of(saved,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getById(saved.getId())).withSelfRel());
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> update(@PathVariable Long id, @RequestBody Brinquedo updated) {
        Optional<Brinquedo> existing = repository.findById(id);
        if (existing.isPresent()) {
            updated.setId(id);
            Brinquedo saved = repository.save(updated);
            EntityModel<Brinquedo> model = EntityModel.of(saved,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getById(id)).withSelfRel());
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> patch(@PathVariable Long id, @RequestBody Brinquedo partial) {
        Optional<Brinquedo> existing = repository.findById(id);
        if (existing.isPresent()) {
            Brinquedo brinquedo = existing.get();
            if (partial.getNome() != null) brinquedo.setNome(partial.getNome());
            if (partial.getTipo() != null) brinquedo.setTipo(partial.getTipo());
            if (partial.getClassificacao() != null) brinquedo.setClassificacao(partial.getClassificacao());
            if (partial.getTamanho() != null) brinquedo.setTamanho(partial.getTamanho());
            if (partial.getPreco() != null) brinquedo.setPreco(partial.getPreco());

            Brinquedo saved = repository.save(brinquedo);
            EntityModel<Brinquedo> model = EntityModel.of(saved,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class).getById(id)).withSelfRel());
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}