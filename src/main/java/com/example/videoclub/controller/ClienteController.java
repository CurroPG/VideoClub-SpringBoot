package com.example.videoclub.controller;

import com.example.videoclub.entity.Cliente;
import com.example.videoclub.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "Clientelistingview";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("accion", "Añadir");
        return "Clienteformview";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@Valid @ModelAttribute("cliente") Cliente cliente,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Añadir");
            return "Clienteformview";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("accion", "Editar");
        return "Clienteformview";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicion(@PathVariable Long id,
                                @Valid @ModelAttribute("cliente") Cliente cliente,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("accion", "Editar");
            return "Clienteformview";
        }
        cliente.setId((int) (long) id);
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}
