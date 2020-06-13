package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.IUserMapper;
import cn.aynu.java2.weibo.rabbit.AvatarProvider;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.utils.MailUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;

@Service
public class UserServiceImpl implements IUserService {

    private final String s = System.getProperty("user.dir");
    private final String avatarPath= s+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"img";
    private final String avatarTargetPath=s+File.separator+"target"+File.separator+"classes"+File.separator+"static"+File.separator+"img";
    @Autowired
    IUserMapper userDao;

    @Resource
    AvatarProvider provider;

    @Override
    public User findUserByLogin(User user) {
       return userDao.selectUserByLogin(user);
    }


    @Override
    public User selectUserById(String id) {
        return userDao.selectUserById(id);
    }

    @Override
    public void addUser(User user)  {
        String eamilMsg="欢迎注册微博,"+"<a href='http://localhost:8080/weibo/user/activeUser?activeCode="+user.getActiveCode()+"'>点此激活</a>"+"到登陆页面";
        try {
            MailUtil.sendMail(user.getEmail(),eamilMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        userDao.insertUser(user);
    }

    @Override
    public void activeUser(User user) {
        userDao.activeUser(user);
    }

    @Override
    public User findUserByEmail(User user) {
        return userDao.selectUserByEmail(user);
    }

    @Override
    @Async
    public void updateAvatar(MultipartFile avatar, String id) throws IOException {
        File avatarFile = new File(avatarPath+File.separator+"avatar",id+"-"+avatar.getOriginalFilename());
        File targetAvatarFile = new File(avatarTargetPath+File.separator+"avatar", id+"-"+avatar.getOriginalFilename());
        if(!avatarFile.exists()){
            avatarFile.mkdirs();
        }else{
            avatarFile.delete();
        }
        avatar.transferTo(avatarFile);
        FileUtils.copyFile(avatarFile, targetAvatarFile);
        provider.updateAvatar("/img/avatar/"+avatarFile.getName(),id);
    }

}
