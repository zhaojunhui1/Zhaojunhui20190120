package com.bawei.myapplication.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bawei.zhaojunhui20190120.bean.TextBean;

import com.bawei.myapplication.greendao.TextBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig textBeanDaoConfig;

    private final TextBeanDao textBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        textBeanDaoConfig = daoConfigMap.get(TextBeanDao.class).clone();
        textBeanDaoConfig.initIdentityScope(type);

        textBeanDao = new TextBeanDao(textBeanDaoConfig, this);

        registerDao(TextBean.class, textBeanDao);
    }
    
    public void clear() {
        textBeanDaoConfig.clearIdentityScope();
    }

    public TextBeanDao getTextBeanDao() {
        return textBeanDao;
    }

}
