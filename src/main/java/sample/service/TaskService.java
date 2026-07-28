package sample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.service.dto.PageResult;

@Service
public class TaskService {

	private final TaskMapper taskMapper;
	private static final int PAGE_SIZE = 10;

	public TaskService(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}

	@Transactional(readOnly = true)
	public PageResult<Task> findPage(String username, int requestedPage) {
		int totalCount = taskMapper.countByUsername(username);
		int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / PAGE_SIZE));

		int page = Math.min(Math.max(requestedPage, 1), totalPages);
		int offset = (page - 1) * PAGE_SIZE;

		List<Task> tasks = taskMapper.findByUsername(username, PAGE_SIZE, offset);
		return new PageResult<>(tasks, page, totalPages, totalCount);
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