package sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;

@Service
public class TaskService {
	
	private final TaskMapper taskMapper;
	
	public TaskService(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}
	
	// SELECT 複数件
	public List<Task> findByUsername(String username) {
		return taskMapper.findByUsername(username);
	}
	
	// SELECT 1件
	public Task findById(Long id, String username) {
		return taskMapper.findById(id, username);
	}
	
	// INSERT
	public int insert(Task task) {
		return taskMapper.insert(task);
	}
	
	// UP DATE
	public int update(Task task) {
		return taskMapper.update(task);
	}
	
	// DELETE
	public int delete(Long id, String username) {
		return taskMapper.delete(id, username);
	}
}