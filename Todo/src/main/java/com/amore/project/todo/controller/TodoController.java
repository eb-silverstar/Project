package com.amore.project.todo.controller;

import com.amore.project.common.ApiResponseEntity;
import com.amore.project.common.Message;
import com.amore.project.todo.service.TodoService;
import com.amore.project.todo.vo.TodoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * Todo 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponseEntity> getTodoList() {
        List<TodoVO> todoList = todoService.getTodoList();

        ApiResponseEntity message = new ApiResponseEntity(Message.I0001, todoList, null, null);

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * 특정 담당자, 특정 날짜의 Todo 목록 조회
     *
     * @param userId
     * @param date
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseEntity> searchTodoList(@RequestParam int userId, @RequestParam String date) {
        List<TodoVO> todoList = todoService.searchTodoList(userId, date);

        ApiResponseEntity message = new ApiResponseEntity(Message.I0001, todoList, null, null);

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * Todo 생성
     *
     * @param todo
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponseEntity> createTodo(@RequestBody TodoVO todo) {
        ApiResponseEntity message = null;

        Map<String, String> result = todoService.validCreateTodo(todo);

        if(Message.I0001.equals(result.get("status"))) {
            TodoVO resultTodo = todoService.createTodo(todo);
            message = new ApiResponseEntity(Message.I0001, resultTodo, null, null);

        } else {
            message = new ApiResponseEntity(result.get("status"), null, result.get("errorCode"), result.get("errorMessage"));
        }

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * Todo 수정
     *
     * @param todo
     * @return
     */
    @PostMapping("/modify")
    public ResponseEntity<ApiResponseEntity> modifyTodo(@RequestBody TodoVO todo) {
        ApiResponseEntity message = null;

        Map<String, String> result = todoService.validModifyTodo(todo);

        if(Message.I0001.equals(result.get("status"))) {
            TodoVO resultTodo = todoService.modifyTodo(todo);
            message = new ApiResponseEntity(Message.I0001, resultTodo, null, null);

        } else {
            message = new ApiResponseEntity(result.get("status"), null, result.get("errorCode"), result.get("errorMessage"));
        }

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * Todo 위임
     *
     * @param todo
     * @return
     */
    @PostMapping("/assign")
    public ResponseEntity<ApiResponseEntity> assignTodo(@RequestBody TodoVO todo) {
        ApiResponseEntity message = null;

        Map<String, String> result = todoService.validAssignTodo(todo);

        if(Message.I0001.equals(result.get("status"))) {
            TodoVO resultTodo = todoService.assignTodo(todo);
            message = new ApiResponseEntity(Message.I0001, resultTodo, null, null);

        } else {
            message = new ApiResponseEntity(result.get("status"), null, result.get("errorCode"), result.get("errorMessage"));
        }

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * Todo 위임 취소
     *
     * @param todo
     * @return
     */
    @PostMapping("/assign/cancel")
    public ResponseEntity<ApiResponseEntity> cancelAssignTodo(@RequestBody TodoVO todo) {
        ApiResponseEntity message = null;

        Map<String, String> result = todoService.validCancelAssignTodo(todo);

        if(Message.I0001.equals(result.get("status"))) {
            int id = todoService.cancelAssignTodo(todo);
            message = new ApiResponseEntity(Message.I0001, id, null, null);

        } else {
            message = new ApiResponseEntity(result.get("status"), null, result.get("errorCode"), result.get("errorMessage"));
        }

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

    /**
     * Todo 삭제
     *
     * @param todo
     * @return
     */
    @PostMapping("/remove")
    public ResponseEntity<ApiResponseEntity> removeTodo(@RequestBody TodoVO todo) {
        ApiResponseEntity message = null;

        Map<String, String> result = todoService.validRemoveTodo(todo);

        if(Message.I0001.equals(result.get("status"))) {
            int id = todoService.removeTodo(todo);
            message = new ApiResponseEntity(Message.I0001, id, null, null);

        } else {
            message = new ApiResponseEntity(result.get("status"), null, result.get("errorCode"), result.get("errorMessage"));
        }

        return new ResponseEntity<ApiResponseEntity>(message, HttpStatus.OK);
    }

}
