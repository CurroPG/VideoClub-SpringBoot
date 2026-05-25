package com.example.videoclub.controller;

import com.example.videoclub.entity.Alquiler;
import java.math.BigDecimal;
import com.example.videoclub.entity.Cliente;
import com.example.videoclub.entity.Pelicula;
import com.example.videoclub.repository.AlquilerRepository;
import com.example.videoclub.repository.ClienteRepository;
import com.example.videoclub.repository.PeliculaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;
    private final PeliculaRepository peliculaRepository;

    public AlquilerController(AlquilerRepository alquilerRepository, ClienteRepository clienteRepository, PeliculaRepository peliculaRepository) {
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
        this.peliculaRepository = peliculaRepository;
    }

    @GetMapping
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerRepository.findAll());
        return "alquileres/AlquilerListingView";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("alquiler", new Alquiler());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        model.addAttribute("accion", "Crear");
        return "alquileres/AlquilerFormView";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@Valid @ModelAttribute("alquiler") Alquiler alquiler, BindingResult result, @RequestParam("clienteId") Long clienteId,
                            @RequestParam("peliculaId") Long peliculaId,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("peliculas", peliculaRepository.findAll());
            model.addAttribute("accion", "Crear");
            return "alquileres/AlquilerFormView";
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada"));

        alquiler.setCliente(cliente);
        alquiler.setPelicula(pelicula);

        // Calcular precio total si hay fecha devolución
        if (alquiler.getFechaDevolucion() != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(
                    alquiler.getFechaAlquiler(), alquiler.getFechaDevolucion());
            if (dias < 1) dias = 1;
            BigDecimal precioTotalCalculado = BigDecimal.valueOf(pelicula.getPrecio()).multiply(BigDecimal.valueOf(dias));
            alquiler.setPrecioTotal(precioTotalCalculado);
        }

        alquilerRepository.save(alquiler);
        return "redirect:/alquileres";
    }

    @PostMapping("/{id}/devolver")
    public String marcarDevuelta(@PathVariable Long id) {
        alquilerRepository.marcarComoDevuelta(id);
        return "redirect:/alquileres";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        alquilerRepository.deleteById(id);
        return "redirect:/alquileres";
    }
}
