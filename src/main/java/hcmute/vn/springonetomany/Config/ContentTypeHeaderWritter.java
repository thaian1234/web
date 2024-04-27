package hcmute.vn.springonetomany.Config;

import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentTypeHeaderWritter implements HeaderWriter {
    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
    }
}
