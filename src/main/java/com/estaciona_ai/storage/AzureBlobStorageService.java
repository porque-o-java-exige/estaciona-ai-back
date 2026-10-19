package com.estaciona_ai.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService implements StorageService {

    private final BlobContainerClient blobContainerClient;

    @Override
    public String upload(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo enviado não pode estar vazio.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        }

        // Gera o nome único na pasta informada
        String fileName = folder + "/" + UUID.randomUUID() + extension;
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        try {
            // 1. Envia os bytes da imagem
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            // 2. Mapeia e define o Content-Type correto para PNG, JPEG, JPG e WEBP
            String contentType = determineContentType(file.getContentType(), extension);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
            blobClient.setHttpHeaders(headers);

            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao realizar upload do arquivo para o Azure Storage", e);
        }
    }

    /**
     * Mapeia com precisão o MIME Type adequado para visualização direta no navegador.
     */
    private String determineContentType(String requestContentType, String extension) {
        // Se o Postman/Frontend enviou um Content-Type do tipo imagem válido, utiliza ele
        if (requestContentType != null && requestContentType.startsWith("image/")) {
            return requestContentType;
        }

        // Mapeamento explícito pela extensão do arquivo
        return switch (extension) {
            case ".png" -> "image/png";
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".webp" -> "image/webp";
            default -> "image/jpeg";
        };
    }

    @Override
    public void delete(String fileUrl) {
        if (fileUrl != null && !fileUrl.isBlank()) {
            try {
                String containerName = blobContainerClient.getBlobContainerName();
                int containerIndex = fileUrl.indexOf("/" + containerName + "/");
                if (containerIndex != -1) {
                    String blobName = fileUrl.substring(containerIndex + containerName.length() + 2);
                    BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
                    if (blobClient.exists()) {
                        blobClient.delete();
                    }
                }
            } catch (Exception e) {
                // Log de erro sem interromper o fluxo da aplicação
            }
        }
    }
}