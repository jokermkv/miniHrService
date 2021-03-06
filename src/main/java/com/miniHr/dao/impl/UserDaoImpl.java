package com.miniHr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.miniHr.dao.CustomerRowMapper;
import com.miniHr.dao.UserDao;
import com.miniHr.entity.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate template;
    
    @Override
    public User getUserById(User user) {
        String sql = "SELECT * FROM USER_INFO WHERE OPEN_ID=:openId";
        return template.queryForObject(sql, new BeanPropertySqlParameterSource(user), CustomerRowMapper.newInstance(User.class));
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO USER_INFO(OPEN_ID,SEX,NAME,PHONE,AGE,INDUSTRY,WORKTIME,EDUCATION,MAJOR,LEVEL,COMPANY_ID,UPDATE_DT) VALUES(:openId,:sex,:name,:phone,:age,:industry,:workTime,:education,:major,:level,:companyId,CURRENT_TIMESTAMP)";
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(sql, new BeanPropertySqlParameterSource(user), holder);
        user.setId(holder.getKey().intValue());
        return user;
    }

    @Override
    public int updateUserLevelInfoById(User user) {
        String sql = "UPDATE USER_INFO SET LEVEL=:level WHERE OPEN_ID=:openId";
        return template.update(sql, new BeanPropertySqlParameterSource(user));
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    @Override
    public void updateUserPhone(User user) {
        String sql = "UPDATE USER_INFO SET NAME=:name, PHONE=:phone WHERE OPEN_ID=:openId";
        template.update(sql, new BeanPropertySqlParameterSource(user));
    }

	@Override
	public int updateUserInfo(User user) {
		String sql="UPDATE USER_INFO SET SEX=:sex, NAME=:name, PHONE=:phone, AGE=:age, INDUSTRY=:industry,WORKTIME=:workTime, EDUCATION=:education, MAJOR=:major WHERE OPEN_ID=:openId";
		return template.update(sql, new BeanPropertySqlParameterSource(user));
	}
}
