package work.idler.jilijili;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;
import work.idler.jilijili.model.entity.JiAdmin;
import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.service.JiAdminService;
import work.idler.jilijili.service.UserVideoService;
import work.idler.jilijili.service.VideoCategoryService;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;

@SpringBootTest
class JilijiliApplicationTests {

    @Autowired
    VideoCategoryService videoCategoryService;

    @Autowired
    JiAdminService jiAdminService;

    @Autowired
    UserVideoService userVideoService;

    @Test
    public void addCategory() {
        VideoCategory videoCategory = new VideoCategory();
        videoCategory.setName("首页");
        videoCategoryService.create(videoCategory);
    }

    @Test
    public void addAdmin() {
        JiAdmin jiAdmin = new JiAdmin();
        jiAdmin.setAccount("admin");
        jiAdmin.setEmail("iscolt@qq.com");
        jiAdmin.setPassword(DigestUtils.md5DigestAsHex("admin".getBytes()));
        jiAdminService.create(jiAdmin);
    }

    @Test
    public void randThree() {
        System.out.println(userVideoService.randThree());
//        System.out.println(userVideoService.listAll());
    }

    @Test
    public void othersVideo() {
        System.out.println(userVideoService.othersVideo(1));
    }

    /**
     * 邮件测试
     */
    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();//简单邮件
        //邮件设置
        message.setSubject("通知>>party");
        message.setText("今晚11点,享受papapa>>part!");

        message.setTo("iscolt@qq.com");
        message.setFrom("1329208516@qq.com");
        mailSender.send(message);
        System.out.println("成功");
    }

    @Test
    public void test02() throws Exception{
//        //创建一个复杂的消息邮件
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        //邮件设置
//        helper.setSubject("通知>>今晚party");
//        helper.setText("<b style='color:red'>今晚11点,享受papapa>>part!</b>",true);//开启html格式
//
//        helper.setTo("1250074822@qq.com");
//        helper.setFrom("1329208516@qq.com");
//
//        //上传文件
//        helper.addAttachment("1.jpg",new File("F:\\图片\\picture\\1.jpg"));
//        helper.addAttachment("2.jpg",new File("F:\\图片\\picture\\2.jpg"));
//        mailSender.send(mimeMessage);
//        System.out.println("成功");
    }



    @Test
    public void sendEmail() {
        String email = "iscolt@qq.com";
        SimpleMailMessage message = new SimpleMailMessage();//简单邮件
        //邮件设置
        message.setSubject("欢迎加入Jilijili大家族!");
        message.setText("您此次验证码是: " + createMsgCode());

        message.setTo(email);
        message.setFrom("1329208516@qq.com");
        System.out.println(message);

        mailSender.send(message);
        System.out.println("成功");
    }


    public static void sendMsgCode(String email) {
        SimpleMailMessage message = new SimpleMailMessage();//简单邮件
        //邮件设置
        message.setSubject("欢迎加入Jilijili大家族!");
        message.setText("您此次验证码是: " + createMsgCode());

        message.setTo(email);
        message.setFrom("1329208516@qq.com");
        System.out.println(message);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.send(message);
        System.out.println("成功");
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
