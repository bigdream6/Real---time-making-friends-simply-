package cn.geng;

import cn.geng.controller.RoomController;
import cn.geng.dao.RecordDao;
import cn.geng.dao.RoomDao;
import cn.geng.dao.UserDao;
import cn.geng.dao.UserInforDao;
import cn.geng.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendApplicationTests {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomController roomController;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInforDao userInforDao;
    @Test
    void contextLoads() {
        Integer uId = 2;




    }

}
