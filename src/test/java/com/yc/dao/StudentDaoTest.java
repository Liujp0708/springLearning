package com.yc.dao;

import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;

public class StudentDaoTest extends TestCase {
    private StudentDao StudentDao;
    private StudentBizImpl studentBizImpl;

    @Override
    public void setUp() throws Exception {
        StudentDao = new StudentDaoJpaImpl();
        studentBizImpl = new StudentBizImpl();
        studentBizImpl.setStudentDao(StudentDao);
    }

    public void testAdd() {
        StudentDao.add("张三");
    }

    public void testUpdate() {
        StudentDao.update("张三");
    }

    public void testBizAdd(){
        studentBizImpl.add("张三");
    }
}