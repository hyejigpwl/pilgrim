package com.lec.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;  // serialVersionUID 필드 추가

    private static final String UPLOAD_DIR = "Users/hyeji/upload/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("file");
        File file = new File(UPLOAD_DIR + fileName);

        System.out.println("Requested file: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());

        if (file.exists() && !file.isDirectory()) {
            String fileExtension = getFileExtension(fileName).toLowerCase();
            switch (fileExtension) {
                case "jpg":
                case "jpeg":
                    response.setContentType("image/jpeg");
                    break;
                case "png":
                    response.setContentType("image/png");
                    break;
                case "gif":
                    response.setContentType("image/gif");
                    break;
                default:
                    response.setContentType("application/octet-stream");
                    break;
            }

            FileInputStream fileInputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outputStream.close();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
