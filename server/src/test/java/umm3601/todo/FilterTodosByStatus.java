package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FilterTodosByStatus {
  @Test
  public void limitTodos() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] first5 = db.limitTodos(allTodos, 5);

    Todo[] complete = db.filterTodosByStatus(first5, "Complete");
    assertEquals(3,complete.length, "Incorrect number of completed Todos listed.");
    for (Todo todo : complete) {
      assertTrue(todo.status);
    }

    Todo[] incomplete = db.filterTodosByStatus(first5, "Incomplete");
    assertEquals(2,incomplete.length, "Incorrect number of incompleted Todos listed.");
    for (Todo todo : incomplete) {
      assertFalse(todo.status);
    }
  }

  public void listUsersWithStatusFilter() throws IOException {
    Database db = new Database("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("status", Arrays.asList(new String[] { "Complete" }));
    Todo[] completeTodos = db.listTodos(queryParams);
    for (Todo todo: completeTodos) {
        assertTrue(todo.status);
    }

    queryParams.put("status", Arrays.asList(new String[] { "Incomplete" }));
    Todo[] incompleteTodos = db.listTodos(queryParams);
    for (Todo todo: incompleteTodos) {
        assertFalse(todo.status);
    }
  }
}
