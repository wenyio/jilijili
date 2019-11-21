package work.idler.jilijili.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.idler.jilijili.model.dto.JsonResult;
import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.service.JiUserService;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/11/4
 */
@RestController
@RequestMapping("/api/users")
public class JiUserController {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    HttpServletRequest req;

    // 临时缓存
    Map<String, String> codes = null;

    @Autowired
    private JiUserService jiUserService;

    @PostMapping("updateIcon/{uid}")
    @ResponseBody
    public JsonResult updateIcon(@PathVariable Integer uid, @RequestParam("file") MultipartFile file) { //1. 接受上传的文件  @RequestParam("file") MultipartFile file
        // 查看用户信息是否被上传
        JiUser user = jiUserService.getById(uid);
//        System.out.println(user);
        try {
            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
            String destFileName = req.getServletContext().getRealPath("") + "uploadIcon" + File.separator + fileName;
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            //6.输出文件名
//            System.out.println(destFile + ":" + fileName);
            // 更新用户信息 TODO 部署时需要更改
            user.setIcon("http://localhost:221/uploadIcon/" + fileName);
            JiUser jiUser = jiUserService.update(user);
            return new JsonResult(HttpStatus.OK.value(), "上传成功~", jiUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败, " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败, " + e.getMessage());
        }
    }

    /**
     * 修改信息
     *
     * @param user
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public JsonResult update(@RequestBody JiUser user) {
        if (user == null) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "没有信息可改~");
        }

        JiUser jiUser = jiUserService.update(user);

        if (jiUser == null) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "遇到点小问题~");
        }
        return new JsonResult(HttpStatus.OK.value(), "保存成功 ^_^ ~", jiUser);
    }

    /**
     * 登录
     *
     * @param jiUser
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public JsonResult login(@RequestBody JiUser jiUser) {

        if (jiUser == null) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "登录失败, 请传值!");
        }

        JiUser user = jiUserService.findByEmailAndPassword(jiUser.getEmail(), jiUser.getPassword());

        if (user == null) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "登录失败, 账号密码错误!");
        }

        return new JsonResult(HttpStatus.OK.value(), "登录成功!", user);
    }

    /**
     * 注册用户, 需要传入 邮件 密码 验证码
     * @param
     * @return
     */
    @PostMapping
    @ResponseBody
    public JsonResult register(@RequestBody JiUser user, @RequestParam String code) {
        String isCode = null;
        isCode =  String.valueOf(codes.get(user.getEmail()));
//        System.out.println(isCode + code + user);

        if (code.equals(isCode) || code == isCode) {
            user.setAccount("000");
            user.setUsername("仔无名");
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes())); // 再次加密入库
            user.setGrade(0);
            user.setIntegral(0);
            user.setIsUp(false);
            user.setSex(0);
            user.setIcon("https://static.hdslb.com/images/akari.jpg");
            user.setSignature("我太懒了, 我什么都不说~");
            JiUser jiUser = jiUserService.create(user);
            codes.remove(jiUser.getEmail());// 清楚临时缓存
            return new JsonResult(HttpStatus.OK.value(), "注册成功!", jiUser);
        } else if (user.getEmail().equals("")) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "注册失败, 无邮箱地址!");
        } else if (user.getPassword().equals("")) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "注册失败, 不能没有密码!");
        }
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "注册失败, 验证码错误!");
    }

    /**
     * 忘记密码
     * @param
     * @return
     */
    @PostMapping("forget")
    @ResponseBody
    public JsonResult forget(@RequestBody JiUser user, @RequestParam String code) {
        String isCode = null;
        isCode =  String.valueOf(codes.get(user.getEmail()));
        System.out.println(isCode + code + user);

        if (code.equals(isCode) || code == isCode) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            JiUser jiUser = jiUserService.findByEmail(user.getEmail()).get(0);
            jiUser.setPassword(user.getPassword());
            jiUser = jiUserService.update(jiUser);
            codes.remove(jiUser.getEmail());// 清楚临时缓存
            return new JsonResult(HttpStatus.OK.value(), "重置成功!", jiUser);
        } else if (user.getEmail().equals("")) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "重置失败, 无邮箱地址!");
        } else if (user.getPassword().equals("")) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "重置失败, 不能没有密码!");
        }
        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "重置失败, 验证码错误!");
    }

    @GetMapping("{id}")
    public JsonResult info(@PathVariable Integer id) {
        return new JsonResult(HttpStatus.OK.value(), jiUserService.getById(id));
    }

    /**
     * 检测邮箱是否被注册
     *
     * @param email
     * @return
     */
    @GetMapping("isRepeat/{email}")
    public JsonResult isRepeat(@PathVariable String email) {
        List<JiUser> jiUsers = jiUserService.findByEmail(email);

        if (jiUsers == null || jiUsers.isEmpty()) {
            return new JsonResult(HttpStatus.OK.value(), "可以注册的邮箱! ");
        }

        return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该邮箱已经注册过, 可以尝试找回密码! ");
    }

    /**
     * 发送验证码用于注册
     * @param email
     * @return
     */
    @GetMapping("sendMsgCode/{email}")
    public JsonResult sentMsgCode(@PathVariable String email) {
        String code = createMsgCode();

        try {
            SimpleMailMessage message = new SimpleMailMessage();//简单邮件
            //邮件设置
            message.setSubject(email + "你好! 欢迎加入Jilijili大家族!");
            message.setText("您此次验证码是: " + code + ", 有效期5分钟!");

            message.setTo(email);
            message.setFrom("1329208516@qq.com");
            System.out.println(message);

            mailSender.send(message);
        } catch (Exception e) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "邮件发送失败, 服务器错误! ");
        }

        // 请求成功
        // TODO 可以存redis缓存
        codes = new HashMap<>();
        codes.remove("email");
        codes.put(email, code); // 存入map

        //以秒为单位，即在没有活动5分钟后
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                codes.remove(email);
            }
        }, 5*60*1000);

        return new JsonResult(HttpStatus.OK.value(), "邮件发送成功! 有效期5分钟!");
    }

    /**
     * 生成随机的6位数验证码
     *
     * @return
     */
    private static String createMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }
}
