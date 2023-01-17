package com.amore.project.todo;

import com.amore.project.todo.vo.TodoVO;

import java.util.Comparator;

public class TodoComparator implements Comparator<TodoVO> {

    @Override
    public int compare(TodoVO todo1, TodoVO todo2) {
        String rank1 = todo1.getRank();
        String rank2 = todo2.getRank();

        int order1 = todo1.getOrder();
        int order2 = todo2.getOrder();

        // S 등급 우선 정렬
        if(!"S".equals(rank1) && "S".equals(rank2)) {
            return 1;
        } else if("S".equals(rank1) && !"S".equals(rank2)) {
            return -1;

            // 그 외 등급 정렬
        } else {
            if(!rank1.equals(rank2)) {
                return rank1.compareTo(rank2);
            } else if(order1 > order2) {
                return 1;
            } else if(order1 < order2) {
                return -1;
            }
        }

        return 0;
    }

}
