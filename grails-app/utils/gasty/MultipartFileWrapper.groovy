package gasty

import com.jasty.core.UploadedFile
import org.springframework.web.multipart.MultipartFile

class MultipartFileWrapper implements UploadedFile {

    private MultipartFile file

    MultipartFileWrapper(MultipartFile file) {
        this.file = file
    }

    @Override
    String getName() {
        file.name
    }

    @Override
    String getOriginalFilename() {
        file.originalFilename
    }

    @Override
    String getContentType() {
        file.contentType
    }

    @Override
    boolean isEmpty() {
        file.empty
    }

    @Override
    long getSize() {
        file.size
    }

    @Override
    byte[] getBytes() {
        file.bytes
    }

    @Override
    InputStream getInputStream() {
        file.inputStream
    }
}
