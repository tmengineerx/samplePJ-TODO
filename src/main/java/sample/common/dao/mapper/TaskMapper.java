package sample.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sample.common.dao.entity.Task;

@Mapper
public interface TaskMapper {
	// 一覧取得
	List<Task> findByUsername(
			@Param("username") String username,
			@Param("limit") int limit,
			@Param("offset") int offset
	);
	
	int countByUsername(@Param("username") String username);
	
	// 1件取得
	Task findById(
			@Param("id") Long id,
			@Param("username") String username
	);
	
	// 登録
	int insert(Task task);
	
	// 更新
	int update(Task task);
	
	// 削除
	int delete(
			@Param("id") Long id,
			@Param("username") String username
	);
}