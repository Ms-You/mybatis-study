package com.mybatis.board.config.file;

import com.mybatis.board.common.ErrorCode;
import com.mybatis.board.common.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUploadUtil {
    private final Path rootDir = Paths.get("upload");

    public Path store(MultipartFile file) {
        try {
            if(!Files.exists(rootDir.toAbsolutePath().normalize())) {
                log.info("디렉토리가 존재하지 않습니다.");
                Files.createDirectories(rootDir.toAbsolutePath().normalize());
            }

            if(file.isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_EMPTY);
            }

            Path targetFile = this.rootDir.resolve(
                    Paths.get(UUID.randomUUID() + file.getOriginalFilename())
            ).normalize().toAbsolutePath();

            if(!targetFile.getParent().equals(this.rootDir.toAbsolutePath())) {
                throw new GlobalException(ErrorCode.FILE_PATH_ERROR);
            }

            file.transferTo(targetFile);

            return targetFile;

        } catch (Exception e) {
            log.error("파일 저장 중 에러 발생: {}", e.getMessage());
            throw new GlobalException(ErrorCode.FILE_STORE_ERROR);
        }
    }

    public Path upload(String filename) {
        return rootDir.resolve(filename);
    }

    public Resource uploadFile(String filename) {
        try {
            Path file = upload(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new GlobalException(ErrorCode.FILE_CANT_READ);
            }
        } catch (MalformedURLException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e);
            throw new GlobalException(ErrorCode.FILE_CANT_READ);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path file = upload(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                File f = resource.getFile();
                f.delete();
            }
        } catch (MalformedURLException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e);
            throw new GlobalException(ErrorCode.FILE_CANT_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
