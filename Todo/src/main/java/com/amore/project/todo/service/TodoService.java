package com.amore.project.todo.service;

import com.amore.project.common.Message;
import com.amore.project.profile.service.ProfileService;
import com.amore.project.todo.TodoComparator;
import com.amore.project.todo.vo.TodoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final ProfileService profileService;

    private List<TodoVO> todoRepository;

    @PostConstruct
    public void init() {
        todoRepository = new ArrayList<>();
    }

    /**
     * Todo 목록 조회
     *
     * @return todoRepository
     */
    public List<TodoVO> getTodoList() {
        for(TodoVO vo : todoRepository) {
            vo.setManager(profileService.getProfile(vo.getManager().getUserId()));
        }

        return todoRepository;
    }

    /**
     * 특정 담당자, 특정 날짜의 Todo 목록 조회
     *
     * @param userId
     * @param date
     * @return todoList
     */
    public List<TodoVO> searchTodoList(int userId, String date) {
        return searchTodoList(userId, date, null);
    }

    /**
     * 특정 담당자, 특정 날짜, 특정 중요도의  Todo 목록 조회
     *
     * @param userId
     * @param date
     * @param rank
     * @return todoList
     */
    private List<TodoVO> searchTodoList(int userId, String date, String rank) {
        List<TodoVO> todoList = new ArrayList<>();

        for(TodoVO vo : todoRepository) {
            if (vo.getManager().getUserId() == userId && vo.getDate().equals(date) &&
                    (rank == null || vo.getRank().equals(rank))) {
                vo.setManager(profileService.getProfile(vo.getManager().getUserId()));
                todoList.add(vo);
            }
        }

        Collections.sort(todoList, new TodoComparator());

        return todoList;
    }

    /**
     * Todo 내용 조회
     *
     * @param id
     * @return todo
     */
    private TodoVO getTodo(int id) {
        TodoVO todo = null;

        Optional<TodoVO> option = todoRepository.stream().filter(i -> i.getId() == id).findFirst();
        if(option.isPresent()) {
            todo = option.get();
        }

        return todo;
    }

    /**
     *  Todo 생성 전 유효성 검사
     *
     * @param todo
     * @return result
     */
    public Map<String, String> validCreateTodo(TodoVO todo){
        Map<String, String> result = new HashMap<>();
        result.put("status", Message.I0002);

        // 필수 정보 체크
        if(todo == null) {
            result.put("errorCode", "E0001");
            result.put("errorMessage", Message.E0001);

        } else if(todo.getTask() == null || "".equals(todo.getTask().trim())) {
            result.put("errorCode", "E0002");
            result.put("errorMessage", Message.E0002);

        } else if(todo.getManager() == null || todo.getManager().getUserId() < 1) {
            result.put("errorCode", "E0003");
            result.put("errorMessage", Message.E0003);

        } else if(todo.getDate() == null || "".equals(todo.getDate().trim())) {
            result.put("errorCode", "E0004");
            result.put("errorMessage", Message.E0004);

        // 데이터 유효성 체크
        } else if(!Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", todo.getDate())) {
            result.put("errorCode", "E0011");
            result.put("errorMessage", Message.E0011);

        } else if(profileService.getProfile(todo.getManager().getUserId()) == null) {
            result.put("errorCode", "E0012");
            result.put("errorMessage", Message.E0012);

        } else {
            result.put("status", Message.I0001);
        }

        return result;
    }

    /**
     * Todo 생성
     *
     * @param todo
     * @return todo
     */
    public TodoVO createTodo(TodoVO todo) {
        return createTodo(todo, 0);
    }

    /**
     * 위임 받은 Todo 생성
     *
     * @param todo
     * @return todo
     */
    public TodoVO createTodo(TodoVO todo, int originTodoId) {
        int nextId = 1;

        // 담당자 프로필 setting
        todo.setManager(profileService.getProfile(todo.getManager().getUserId()));

        // 상태 setting
        todo.setStatus(Message.I0011);

        // 신규 Todo
        if(originTodoId <= 0) {
            // 중요도 및 순서 setting
            List<TodoVO> originList = searchTodoList(todo.getManager().getUserId(), todo.getDate());

            if (originList.size() > 0) {
                TodoVO lastTodo = originList.get(originList.size() - 1);
                todo.setRank(lastTodo.getRank());
                todo.setOrder(lastTodo.getOrder() + 1);

            } else {
                todo.setRank(Message.I0023);
                todo.setOrder(0);
            }

        // 위임 받은 Todo
        } else {
            todo.setAssigner(profileService.getProfile(todo.getAssigner().getUserId()));
            todo.setOriginTodoId(originTodoId);
            todo.setRank(Message.I0022);

            // 중요도 및 순서 setting
            List<TodoVO> originList = searchTodoList(todo.getManager().getUserId(), todo.getDate(), todo.getRank());
            todo.setOrder(originList.size());
        }

        // id setting
        List<TodoVO> allTodoList = getTodoList();

        if(allTodoList.size() > 0) {
            nextId = allTodoList.get(allTodoList.size() - 1).getId() + 1;
        }

        todo.setId(nextId);

        // Todo Insert
        todoRepository.add(todo);

        return todo;
    }

    /**
     * Todo 수정 전 유효성 검사
     *
     * @param todo
     * @return result
     */
    public Map<String, String> validModifyTodo(TodoVO todo){
        Map<String, String> result = new HashMap<>();
        result.put("status", Message.I0002);

        List<String> rankList = Arrays.asList(Message.I0021, Message.I0022, Message.I0023, Message.I0024, Message.I0025);
        List<String> statusList = Arrays.asList(Message.I0011, Message.I0012, Message.I0013);

        // 필수 정보 체크
        if(todo == null) {
            result.put("errorCode", "E0001");
            result.put("errorMessage", Message.E0001);

        } else if(todo.getId() < 1) {
            result.put("errorCode", "E0007");
            result.put("errorMessage", Message.E0007);

        } else if(todo.getTask() == null || "".equals(todo.getTask().trim())) {
            result.put("errorCode", "E0002");
            result.put("errorMessage", Message.E0002);

        } else if(todo.getRank() == null || "".equals(todo.getRank().trim()) || todo.getOrder() < 0) {
            result.put("errorCode", "E0005");
            result.put("errorMessage", Message.E0005);

        } else if(todo.getStatus() == null || "".equals(todo.getStatus().trim())) {
            result.put("errorCode", "E0006");
            result.put("errorMessage", Message.E0006);

        // 신규 데이터 유효성 체크
        } else if(!rankList.contains(todo.getRank())) {
            result.put("errorCode", "E0013");
            result.put("errorMessage", Message.E0013);

        } else if(!statusList.contains(todo.getStatus())) {
            result.put("errorCode", "E0014");
            result.put("errorMessage", Message.E0014);

        // 기존 데이터 유효성 체크
        } else {
            TodoVO originTodo = getTodo(todo.getId());

            if(originTodo == null) {
                result.put("errorCode", "E0015");
                result.put("errorMessage", Message.E0015);

            } else if(Message.I0014.equals(originTodo.getStatus())) {
                result.put("errorCode", "E0016");
                result.put("errorMessage", Message.E0016);

            } else {
                result.put("status", Message.I0001);
            }
        }

        return result;
    }

    /**
     * Todo 수정
     *
     * @param todo
     * @return resultTodo
     */
    public TodoVO modifyTodo(TodoVO todo) {
        // 기존 Todo 데이터 조회
        TodoVO resultTodo = getTodo(todo.getId());

        // 업무 제목, 설명, 상태 수정
        resultTodo.setTask(todo.getTask());
        resultTodo.setDescription(todo.getDescription());
        resultTodo.setStatus(todo.getStatus());

        // 기존 Todo 데이터 재정렬 및 우선순위 수정
        if(!resultTodo.getRank().equals(todo.getRank())) {
            List<TodoVO> newListForRemove = searchTodoList(resultTodo.getManager().getUserId(), resultTodo.getDate(), resultTodo.getRank());
            newListForRemove.remove(resultTodo.getOrder());
            newListForRemove.forEach(l -> l.setOrder(newListForRemove.indexOf(l)));

            List<TodoVO> newListForAdd = searchTodoList(resultTodo.getManager().getUserId(), resultTodo.getDate(), todo.getRank());
            resultTodo.setRank(todo.getRank());

            if(todo.getOrder() < newListForAdd.size()) {
                newListForAdd.add(todo.getOrder(), resultTodo);
            } else {
                newListForAdd.add(resultTodo);
            }

            newListForAdd.forEach(l -> l.setOrder(newListForAdd.indexOf(l)));

        } else if(resultTodo.getOrder() != todo.getOrder()) {
            List<TodoVO> newList = searchTodoList(resultTodo.getManager().getUserId(), resultTodo.getDate(), resultTodo.getRank());
            newList.remove(resultTodo.getOrder());

            if (todo.getOrder() < newList.size()) {
                newList.add(todo.getOrder(), resultTodo);
            } else {
                newList.add(resultTodo);
            }

            newList.forEach(l -> l.setOrder(newList.indexOf(l)));
        }

        return resultTodo;
    }

    /**
     * Todo 위임 전 유효성 검사
     *
     * @param todo
     * @return result
     */
    public Map<String, String> validAssignTodo(TodoVO todo){
        Map<String, String> result = new HashMap<>();
        result.put("status", Message.I0002);

        // 필수 정보 체크
        if(todo == null) {
            result.put("errorCode", "E0001");
            result.put("errorMessage", Message.E0001);

        } else if(todo.getId() < 1) {
            result.put("errorCode", "E0007");
            result.put("errorMessage", Message.E0007);

        } else if(todo.getAssignee() == null || todo.getAssignee().getUserId() < 1) {
            result.put("errorCode", "E0008");
            result.put("errorMessage", Message.E0008);

        // 신규 데이터 유효성 체크
        } else if(profileService.getProfile(todo.getAssignee().getUserId()) == null) {
            result.put("errorCode", "E0012");
            result.put("errorMessage", Message.E0012);

        // 기존 데이터 유효성 체크
        } else {
            TodoVO originTodo = getTodo(todo.getId());

            if(originTodo == null) {
                result.put("errorCode", "E0015");
                result.put("errorMessage", Message.E0015);

            } else if(originTodo.getOriginTodoId() > 0) {
                result.put("errorCode", "E0017");
                result.put("errorMessage", Message.E0017);

            } else if(!Message.I0011.equals(originTodo.getStatus())) {
                result.put("errorCode", "E0018");
                result.put("errorMessage", Message.E0018);

            } else {
                result.put("status", Message.I0001);
            }
        }

        return result;
    }

    /**
     * Todo 위임
     *
     * @param todo
     * @return resultTodo
     */
    public TodoVO assignTodo(TodoVO todo) {
        // 기존 Todo 데이터 조회
        TodoVO resultTodo = getTodo(todo.getId());

        // 상태, 양수인 setting
        resultTodo.setStatus(Message.I0014);
        resultTodo.setAssignee(profileService.getProfile(todo.getAssignee().getUserId()));

        // 신규 데이터 생성
        TodoVO assignTodo = new TodoVO();
        assignTodo.setDate(resultTodo.getDate());
        assignTodo.setManager(todo.getAssignee());
        assignTodo.setTask(resultTodo.getTask());
        assignTodo.setDescription(resultTodo.getDescription());
        assignTodo.setAssigner(resultTodo.getManager());
        createTodo(assignTodo, todo.getId());

        return resultTodo;
    }

    /**
     * 위임 취소 전 유효성 검사
     *
     * @param todo
     * @return result
     */
    public Map<String, String> validCancelAssignTodo(TodoVO todo){
        Map<String, String> result = new HashMap<>();
        result.put("status", Message.I0002);

        // 필수 정보 체크
        if(todo == null) {
            result.put("errorCode", "E0001");
            result.put("errorMessage", Message.E0001);

        } else if(todo.getId() < 1) {
            result.put("errorCode", "E0007");
            result.put("errorMessage", Message.E0007);

            // 기존 데이터 유효성 체크
        } else {
            TodoVO originTodo = getTodo(todo.getId());

            if(originTodo == null) {
                result.put("errorCode", "E0015");
                result.put("errorMessage", Message.E0015);

            } else if(originTodo.getOriginTodoId() <= 0) {
                result.put("errorCode", "E0019");
                result.put("errorMessage", Message.E0019);

            } else {
                result.put("status", Message.I0001);
            }
        }

        return result;
    }

    /**
     * 위임 취소
     *
     * @param todo
     * @return todo.getId()
     */
    public int cancelAssignTodo(TodoVO todo) {
        // 기존 Todo 데이터 조회
        TodoVO assignTodo = getTodo(todo.getId());
        TodoVO originTodo = getTodo(assignTodo.getOriginTodoId());

        // 원본 Todo 원상복구
        originTodo.setStatus(Message.I0011);
        originTodo.setAssignee(null);

        // 위임 받은 Todo 삭제 및 우선순위 재정렬
        todoRepository.remove(assignTodo);

        List<TodoVO> newList = searchTodoList(assignTodo.getManager().getUserId(), assignTodo.getDate(), assignTodo.getRank());
        newList.forEach(l -> l.setOrder(newList.indexOf(l)));

        return todo.getId();
    }

    /**
     * Todo 삭제 전 유효성 검사
     *
     * @param todo
     * @return result
     */
    public Map<String, String> validRemoveTodo(TodoVO todo){
        Map<String, String> result = new HashMap<>();
        result.put("status", Message.I0002);

        // 필수 정보 체크
        if(todo == null) {
            result.put("errorCode", "E0001");
            result.put("errorMessage", Message.E0001);

        } else if(todo.getId() < 1) {
            result.put("errorCode", "E0007");
            result.put("errorMessage", Message.E0007);

        // 기존 데이터 유효성 체크
        } else {
            TodoVO originTodo = getTodo(todo.getId());

            if(originTodo == null) {
                result.put("errorCode", "E0015");
                result.put("errorMessage", Message.E0015);

            } else if(Message.I0014.equals(originTodo.getStatus())) {
                result.put("errorCode", "E0016");
                result.put("errorMessage", Message.E0016);

            } else if(originTodo.getOriginTodoId() > 0) {
                result.put("errorCode", "E0020");
                result.put("errorMessage", Message.E0020);

            } else {
                result.put("status", Message.I0001);
            }
        }

        return result;
    }

    /**
     * Todo 삭제
     *
     * @param todo
     * @return todo.getId()
     */
    public int removeTodo(TodoVO todo) {
        // 기존 Todo 데이터 조회
        TodoVO originTdo = getTodo(todo.getId());

        // Todo 삭제 및 우선순위 재정렬
        todoRepository.remove(originTdo);

        List<TodoVO> newList = searchTodoList(originTdo.getManager().getUserId(), originTdo.getDate(), originTdo.getRank());
        newList.forEach(l -> l.setOrder(newList.indexOf(l)));

        return todo.getId();
    }

}
