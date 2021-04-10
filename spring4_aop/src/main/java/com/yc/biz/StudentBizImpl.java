package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * /**
 *
 * @program: testspring
 * @description:
 * @author: 作者
 * @create: 2021-04-04 15:59
 */
public class StudentBizImpl implements StudentBiz {
    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    public StudentBizImpl(){

    }

    @Autowired
    public void setStudentDao(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    public int add(String name){
        System.out.println("==========业务层=========");
        System.out.println("用户名是否重名");
        int result =studentDao.add(name);
        System.out.println("==========业务层操作结束=========");
        return result;
    }



    public void update(String name) {
        System.out.println("==========业务层=========");
        System.out.println("用户名是否重名");
        studentDao.update(name);
        System.out.println("==========业务层操作结束=========");
    }

    @Override
    public void find(String name) {

    }
}
