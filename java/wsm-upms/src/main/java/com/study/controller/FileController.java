package com.study.controller;

import com.study.exception.MyRuntimeException;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Api(description = "文件相关操作控制器")
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${my-config.upload-folder}")
    private String uploadFolder;

    @ApiOperation("多文件上传")
    @PostMapping("/multipartFileUpload")
    public ResultView multipartFileUpload(@RequestParam("file") MultipartFile[] files,
                                          @ApiParam("指定上传文件夹（用于分类）") @RequestParam(required = false) String folder,
                                          @ApiParam("指定文件后缀名 如:jpg/png") @RequestParam String suffix) {

        if (files.length == 0) {
            return ResultView.error(ResultEnum.CODE_9);
        }
        String[] suffixs = suffix.split("/");
        folder = StringUtils.isEmpty(folder) ? "/" : ("/" + folder + "/");
        String fileNames = "";
        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            //判断文件是否为空
            if (file.isEmpty()) {
                return ResultView.error("第" + (i + 1) + "个文件为空");
            }

            //判断文件后缀名是否符合要求
            String filename = file.getOriginalFilename();
            String fileNameSuffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (Stream.of(suffixs).filter(x -> x.toLowerCase().equals(fileNameSuffix.toLowerCase())).count() == 0) {
                return ResultView.error(ResultEnum.CODE_10);
            }

            //判断文件大小是否符合要求
            int size = (int) file.getSize();
            if (size > 1024 * 1024 * 100) {
                return ResultView.error("请上传小于100MB大小的文件！");
            }

            String newFileName = CreateUtil.id() + "." + fileNameSuffix;
            fileNames += "," + folder + newFileName;

            //判断文件夹是否存在,不存在创建文件夹
            File targetFile = new File(uploadFolder + folder + newFileName);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            try {
                //将上传文件写到服务器上指定的文件
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileNames = fileNames.substring(1);
        return ResultView.success(fileNames);
    }

    @ApiOperation("文件下载")
    @GetMapping("fileDownload")
    public ResultView fileDownload(HttpServletRequest request, HttpServletResponse response, @ApiParam("文件存储路径") @RequestParam String fileName) {

        File file = new File(uploadFolder + fileName);
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        if (!file.exists()) {
            throw new MyRuntimeException(ResultView.error(ResultEnum.CODE_11));
        }

        try {
            //判断浏览器是否为火狐
            String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (UserAgent != null && UserAgent.indexOf("firefox") != -1) {
                // 火狐浏览器 设置编码new String(realName.getBytes("GB2312"), "ISO-8859-1");
                fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");//encode编码UTF-8 解决大多数中文乱码
                fileName = fileName.replace("+", "%20");//encode后替换空格  解决空格问题
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response.setContentType("application/force-download");//设置强制下载
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);//设置文件名

        byte[] buff = new byte[1024];// 用来存储每次读取到的字节数组
        //创建输入流（读文件）输出流（写文件）
        BufferedInputStream bis = null;
        OutputStream os = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            os = response.getOutputStream();

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultView.success();
    }

    @ApiOperation("多文件删除")
    @PostMapping("/multipartFileDelete")
    public ResultView multipartFileDelete(@ApiParam("文件存储路径") @RequestParam String fileNames) {

        String[] files = fileNames.split(",");
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        //用stream流方式删除文件
        Stream.of(files).forEach(file -> {
            boolean b = new File(uploadFolder + file).delete();
            if (!b) {
                atomicBoolean.set(false);
                System.out.println("文件删除失败！");
            }
        });
        if (!atomicBoolean.get()) {
            return ResultView.error();
        }

        return ResultView.success();
    }

}
