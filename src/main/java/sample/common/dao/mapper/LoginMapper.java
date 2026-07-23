package sample.common.dao.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.common.dao.entity.Login;

@Mapper
public interface LoginMapper {
	// 一覧取得
	Login findByUsername(
			@Param("username") String username
	);
	
	// 登録
	int insert(Login login);
}