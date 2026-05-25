package com.example.videoclub.controller;

import com.example.videoclub.entity.Pelicula;
import com.example.videoclub.repository.PeliculaRepository;
import com.example.videoclub.service.SupabaseStorageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaRepository peliculaRepository;
    private final SupabaseStorageService storageService;

    public PeliculaController(PeliculaRepository peliculaRepository, SupabaseStorageService storageService) {
        this.peliculaRepository = peliculaRepository;
        this.storageService = storageService;
    }

    @GetMapping
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "Peliculalistingview";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("accion", "Añadir");
        return "Peliculaformview";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@Valid @ModelAttribute("pelicula") Pelicula pelicula,
                            BindingResult result,
                            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Añadir");
            return "Peliculaformview";
        }
        peliculaRepository.save(pelicula);
        // Subir imagen al bucket de Supabase tras guardar la película
        if (imagen != null && !imagen.isEmpty()) {
            storageService.subirImagen(pelicula.getTitulo(), imagen);
        }
        return "redirect:/peliculas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada: " + id));
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("accion", "Editar");
        return "Peliculaformview";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
                                @Valid @ModelAttribute("pelicula") Pelicula pelicula,
                                BindingResult result,
                                @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Editar");
            return "Peliculaformview";
        }
        pelicula.setId((int) (long) id);
        peliculaRepository.save(pelicula);
        // Subir/actualizar imagen en Supabase
        if (imagen != null && !imagen.isEmpty()) {
            storageService.subirImagen(pelicula.getTitulo(), imagen);
        }
        return "redirect:/peliculas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        peliculaRepository.deleteById(id);
        return "redirect:/peliculas";
    }
}
