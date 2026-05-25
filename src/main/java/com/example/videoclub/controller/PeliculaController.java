package com.example.videoclub.controller;

import com.example.videoclub.entity.Pelicula;
import com.example.videoclub.repository.PeliculaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaRepository peliculaRepository;

    public PeliculaController(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @GetMapping
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "peliculas/PeliculaListingView";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("accion", "Añadir");
        return "peliculas/PeliculaFormView";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@Valid @ModelAttribute("pelicula") Pelicula pelicula,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Añadir");
            return "peliculas/PeliculaFormView";
        }
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada: " + id));
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("accion", "Editar");
        return "peliculas/PeliculaFormView";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
                                @Valid @ModelAttribute("pelicula") Pelicula pelicula,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Editar");
            return "peliculas/PeliculaFormView";
        }
        pelicula.setId((int) (long) id);
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        peliculaRepository.deleteById(id);
        return "redirect:/peliculas";
    }
}
