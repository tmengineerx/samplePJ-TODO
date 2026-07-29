package sample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.service.dto.PageResult;
import sample.common.exception.TaskNotFoundException;

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
	@Transactional(readOnly = true)
	public Task findOwnedTask(Long id, String username) {
		Task task = taskMapper.findById(id, username);
		if (task == null) {
			throw new TaskNotFoundException(id);
		}
		return task;
	}

	// INSERT
	@Transactional
	public void create(Task task) {
		taskMapper.insert(task);
	}

	// UP DATE
	@Transactional
	public void updateOwnedTask(Task task) {
		int updated = taskMapper.update(task);
		if (updated == 0) {
			throw new TaskNotFoundException(task.getId());
		}
	}

	// DELETE
	@Transactional
	public void deleteOwnedTask(Long id, String username) {
		int deleted = taskMapper.delete(id, username);
		if (deleted == 0) {
			throw new TaskNotFoundException(id);
		}
	}
}