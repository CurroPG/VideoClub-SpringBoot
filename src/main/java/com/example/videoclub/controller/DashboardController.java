package com.example.videoclub.controller;

import com.example.videoclub.repository.AlquilerRepository;
import com.example.videoclub.repository.ClienteRepository;
import com.example.videoclub.repository.PeliculaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ClienteRepository clienteRepository;
    private final PeliculaRepository peliculaRepository;
    private final AlquilerRepository alquilerRepository;

    public DashboardController(ClienteRepository clienteRepository,PeliculaRepository peliculaRepository,AlquilerRepository alquilerRepository) {
        this.clienteRepository = clienteRepository;
        this.peliculaRepository = peliculaRepository;
        this.alquilerRepository = alquilerRepository;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String verDashboard(Model model) {
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("totalPeliculas", peliculaRepository.count());
        model.addAttribute("totalAlquileres", alquilerRepository.count());
        // Enviamos la lista de todas las películas para el catálogo visual
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "dashboard";
    }
}