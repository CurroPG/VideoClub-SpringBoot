package com.example.videoclub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Sube una imagen al bucket de Supabase Storage.
     * El nombre del archivo se genera automáticamente a partir del título de la película.
     */
    public void subirImagen(String tituloPelicula, MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            return; // No se ha seleccionado imagen, no hacemos nada
        }

        try {
            String nombreArchivo = generarNombreArchivo(tituloPelicula);
            String url = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + nombreArchivo;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + supabaseKey)
                    .header("Content-Type", archivo.getContentType())
                    // upsert = true para sobreescribir si ya existe
                    .header("x-upsert", "true")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(archivo.getBytes()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Error al subir imagen a Supabase: " + response.statusCode());
                System.err.println("Respuesta: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al subir imagen: " + e.getMessage());
        }
    }

    /**
     * Convierte el título de la película en un nombre de archivo seguro.
     * Ej: "El Padrino" -> "el-padrino.jpg"
     */
    private String generarNombreArchivo(String titulo) {
        // Normalizar caracteres acentuados (á -> a, é -> e, etc.)
        String normalizado = Normalizer.normalize(titulo, Normalizer.Form.NFD);
        normalizado = normalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return normalizado
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Quitar caracteres especiales
                .trim()
                .replaceAll("\\s+", "-")          // Espacios a guiones
                + ".jpg";
    }
}
